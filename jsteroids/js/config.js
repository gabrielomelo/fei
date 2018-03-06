/**
 * @author gabrielomelo
 * @link github.com/gabrielomelo
 */

 //Arrays com o posicionamento de cada vértice dos poligonos

var dots = {

    ASTEROIDS: [
        [-1,-1,1,-2,1,1,-2,1,-1,0,-1,-1],
        [-1,-2,1,-1,2,2,0,1,-1,2,-2,-1,-1,-2],
        [-1,-1,0,-2,1,-1,2,1,-1,2,-1,-1],
        [-1,-1,0,-2,2,0,1,1,-1,1,-1,-1],
        [-2,0,0,1,1,0,1,-1,-2,-1,-2,0],
        [-2,-1,0,-2,2,-1,1,2,-1,2,-2,-1]
    ],

    SHIP: [0,-3,-3,5,0,7,3,5,0,-3],

    LASER_PATTERN: [-1,-3,0,-6,1,-3,-1,-3]

};

//A fins de leitura e analise do codigo, separei os codigos de cada tecla aqui.

var UP_ARROW = 38;
var RIGHT_ARROW = 39;
var LEFT_ARROW = 37;
var SPACEBAR = 32;

//Variavies de configuração do comportamento

var behavior = {

    SHIP_ACELERATION: 4,
    LASER_VELOCITY: 6,
    ASTEROIDS_MAX_VELOCITY: 1.8,
    ASTEROIDS_MIN_VELOCITY: 0.7,
    SHIP_LEFT_ANGLE: -0.15,
    SHIP_RIGHT_ANGLE: 0.15,
    SHIP_DEFAULT_ANGLE: ( Math.PI*3 )/2,
    ASTEROIDS_MIN_SIZE: 2,
    INIT_ASTEROIDS_VALUE: 6

};
