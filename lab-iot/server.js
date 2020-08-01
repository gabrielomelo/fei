/** 
 * Simple Backend for FEI IOT laboratory
 * @author gabrielomelo
 */


// Loading dependencies - Middlewares
var http = require("http");
var express = require("express");
var mongoose = require("mongoose");
var bodyParser = require("body-parser");
var multer = require("multer");
var fs = require("fs");
var app = express();
var stdPort = process.env.PORT || 3030;

// Connecting to mongod
mongoose.connect("mongodb://localhost/lab");

// Schemas --
var Provider = mongoose.model("Provider", mongoose.Schema({
    id: String,
    img_path: String,
    name: String,
    email: String,
    web_site: String,
    telephone: String,
    description: String,
    address: String
}));

var Product = mongoose.model("Product", mongoose.Schema({
    id: String,
    img_path: String,
    description: String,
    qty: String,
    last_order_dt: String,
    last_delivery_dt: String,
    average_cost: String,
    localization: String
}));

// CONFIG
app.set("view engine", "ejs");
app.set("views", "./views");
app.use(express.static("./public/images"));
app.use(express.static("./product_img"));
app.use(express.static("./public/css"));
app.use(express.static("./public/js"));
app.use(bodyParser({urlencoded : false}));

// Image parser configuration
var storage = multer.diskStorage({
  destination: function (req, file, cb) {
    cb(null, "./product_img");
  },
  filename: function (req, file, cb) {
    var extension = file.originalname.substr(file.originalname.lastIndexOf(".") + 1);
    cb(null, file.fieldname + '-' + Date.now() + "." + extension);
  }
})
var upload = multer({ storage: storage });

// ROUTES FOR MAIN PAGES
app.get(["/", "/index"], function(request, response){ // Here the query getting the storage conditions
  Product.find(function(err, result){
      response.render("index", {  
        products : result
      });
  });
});

app.get(["/prod_manager"], function(request, response){ // Here the query getting the storage conditions
  Product.find(function(err, result){
      response.render("product_manager", {  
        products : result
      });
  });
});

app.get(["/product_registration"], function(request, response) { // Render the product registration view
  response.render("product_registration");
});

app.get(["/provider_registration"], function(request, response) { // Render the provider registration view
  response.render("provider_registration");
});

app.get(["/product_delete"], function(request, response) { // Render the provider registration view
  response.redirect("/");
});

app.get(["/product_mod"], function(request, response) { // Render the provider registration view
  Product.find({name: request.param("name")},
  function(err, result){
    response.render("product_modfication", {
      product : result
    });
  });
});


//---------------------------------------------------------------------------------


// INSERT THE PRODUCT
app.post(["/product_reg"], upload.single("image"), function(request, response, next) {

  var requestProduct = new Product({
      id: request.body.id,
      img_path: request.file.filename,
      description: request.body.description,
      qty: request.body.qty,
      last_order_dt: request.body.last_order,
      last_delivery_dt: request.body.last_delivery,
      average_cost: request.body.average_cost,
      localization: request.body.localization
    });

    requestProduct.save();
    response.redirect("/");
});


// UPDATE THE PRODUCT
app.post(["/product_mod"], function(request, response){
  var requestProduct = new Product({
      id: request.body.id,
      name: request.body.name,
      description: request.body.description,
      qty: request.body.qty,
      last_order_dt: request.body.last_order,
      last_delivery_dt: request.body.last_delivery,
      average_cost: request.body.average_cost,
      localization: request.body.localization
    });
	Product.udpate({id: request.body.id}, {set: {name: request.body.name}});	
  response.redirect("/");
});


// DELETE THE PRODUCT
app.get(["/product_del"], function(request, response){
  Product.remove({name: request.param("name")});
  response.redirect("/");

});

// SELECT THE PRODUCT
app.get(["/product_search"], function(request, response){ // Here the query getting the storage conditions
  Product.find(
    {name: request.param("name")},
    function(err, result){
      response.render("product_search", {
        products : result
    });
  });
});

//--------------------------------------------------------------------------

// INSERT THE PROVIDER
app.post(["/provider_reg"], function(request, response){ // "INSERT" call
  
  var requestProvider = new Provider({
      id: request.body.id,
      name: request.body.name,
      email: request.body.email,
      web_site: request.body.web_site,
      telephone: request.body.telephone,
      description: request.body.description,
      address: request.body.address
    });
    requestProvider.save()
    response.redirect("/");
});

// SELECT THE PROVIDER
app.get(["/provider_search"], function(request, response){ // Here the query getting the storage conditions
  Provider.find(
    {name: request.param("name")},
    function(err, result){
      response.render("provider_search", {
        providers : result
    });
  });
});


//-------------------------------------------------------------


// INIT
var server = http.createServer(app);
server.listen(stdPort);
console.log("Listening...");
console.log("Server initialized on port: " + stdPort);
