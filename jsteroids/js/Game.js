/**
 * @author gabrielomelo
 *
 **/

//setting the construct
function Game(ctx, cvs, state)
{

    // Environment variables
    this.asteroids = [];
    this.bullets = [];
    this.canvas = cvs;
    this.ctx = ctx;
    this.state = state;
    this.time = 60;
    this.countdown = setInterval(function(){
        this.time -= 1;
      },
      (1000 / 1)
    );

    //load the game elements
    this.init = function() {
        for(var i = 0; i < 10; i++) {
          this.asteroids.push(new Asteroid().init());
        }

        this.ship = new Ship([this.canvas.width/2, this.canvas.height/2]).init();
        this.loop();

        return this;
    }

    this.loop = function() {
      while(this.state) {
        this.clock();
        this.update();
        this.render();
      }
    }

    this.render = function() {
      for(var i = 0; this.asteroids.lenght > i; i++) {
        this.asteroids[i].render(this.ctx);
      }
      for(var i = 0; this.bullets.lenght > i; i++) {
        this.bullets[i].render(this.ctx);
      }
      this.ship.render(this.ctx);

      return this;
    }

    this.update = function() {
      for(var i = 0; this.asteroids.lenght > i; i++) {
        this.bullets[i].update();
      }

      for(var i = 0; this.asteroids.lenght > i; i++) {
        this.asteroids[i].update();
      }
      this.ship.update();

      return this;
    }

    this.watch = function() {
      if (this.time > 60){
        this.state = !this.state;
      }
      return this;
    }

    this.garbageCollector = function() {
      //range verification here..
    }

    return this;
}


function main() {
    //getting the context and setting the game
    var canvas = document.getElementById("canvas");
    var game = new Game(canvas.getContext("2d"), canvas, true);
    game.init();
}
