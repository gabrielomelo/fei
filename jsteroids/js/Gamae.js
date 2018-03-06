
/**
 * @author gabrielomelo
 *
 * O funcionamento do jogo é simples de um certo ponto de vista, o código é original com excessão de algumas
 * fórmulas matemáticas que achei pela internet. TODAS AS REFERENCIAS SÃO FEITAS NO README.
 * O jogo é muito mais simples que o original, porém, tenho orgulho em apresentar seu funcionamento.
 *
 */

var asteroids;
var key;
var ship;
var canvas;
var ctx;
var fpoints;
var time;
var col, upd, cmd, wtc, anim;

/**
 * Inicia o processo inicial do jogo
 * defino um intervalo de processame;n;to para calcular a fisica do jogo
 * e requisito uma janela de animação para a função draw
 */

function main() {

    //inicio as variveis importantes para o jogo, as chamadas variaveis globais
    asteroids = [];
    key = 0;
    ship = null;
    fpoints = 0;
    time = 59;

    //crio os elementos pertinentes ao jogo
    setUp();

    //inicio o bloco principal do jogo
    col = setInterval(colision, (1000/120));
    upd = setInterval(update, (1000/120));
    cmd = setInterval(commands, (1000/60));
    anim = setInterval(draw, (1000/120));

    //função com timeout de 1 seg, embaixo explico melhor
    wtc = setInterval(watch, 1000);
}

/**
 * inicio as variaveis que compreendem o jogo
 * utilizo de algumas variaveis da minha folha de configuração
 */

function setUp() {

    //crio o canvas e o contexto
    canvas = document.getElementById("canvas");
    ctx = canvas.getContext("2d");

    //crio os asteroids baseado no numero definido na folha de configurações
    for ( var i = 1; behavior.INIT_ASTEROIDS_VALUE > i -1; i++ ) {

        asteroids.push(new Asteroid().create(dots.ASTEROIDS).scale(scales.ASTEROIDS));

    }

    //defino a posição dos asteroids de modo aleatorio baseado no tamanho do canvas
    for ( var c = 0; asteroids.length > c; c++ ) {

        asteroids[c].center.x = Math.round(Math.random() * (canvas.width) + 1);
        asteroids[c].center.y = Math.round(Math.random() * (canvas.height) + 1);
        asteroids[c].render(asteroids[c].center.x, asteroids[c].center.y, ctx);

    }

    //instancio a nave e o centro dela sendo metade do tamanho da tela
    ship = new Ship(dots.SHIP);
    ship.center.x = canvas.width/2;
    ship.center.y = canvas.height/2;

    //defino o tamanho do poligono no objeto e o centro dele
    ship.scale(scales.SHIP).render(ship.center.x, ship.center.y, ctx);

}

/**
 * Movimenta os objetos e atualiza os comandos
 * Na segunda parte do codigo retiro os lasers que sairam da tela, ou seja
 * do limite do meu contexto
 */

function update() {

    //itero dentro de cada asteroid pedindo para que ele se mova
    //mais explicações no metodo dentro do arquivo do asteroid
    for (var i = 0; asteroids.length > i; i++ ) {
        asteroids[i].move();
    }

    //renderizo o laser, do mesmo jeito que faço para cada poligono
    for (var j = 0; ship.lasers.length > j; j++) {
        ship.lasers[j].boost();
    }

    //reduzindo o uso de memoria removendo os lasers que estao fora do canvas
    //uso a função slice para apagar os elementos que sairem do limite do canvas
    for (var i = 0; ship.lasers.length > i; i++) {

        if( ship.lasers[i].position.x > canvas.width || ship.lasers[i].position.x < 0 ){

            ship.lasers = ship.lasers.slice(i);

        }else if( ship.lasers[i].position.y > canvas.height || ship.lasers[i].position.y < 0 ) {

            ship.lasers = ship.lasers.slice(i);

        }

    }

}

/**
 * Adicionei dois listener para o teclado
 * keydown - recebe o codigo de cada tecla pressionada
 * keyup - define o codigo da tecla como zero quando a tecla é solta
 *
 * na segunda parte do codigo ele compara o o codigo e determina uma açao
 * e sim está horrivel. Portanto, não há muito o que comentar, é simples.
 * Espero não perder nota por isso.
 */

function commands() {

    addEventListener("keydown", function (event) {
        key = event.keyCode;
    });

    addEventListener("keyup", function(){
        key = 0;
    });

    if (key === keys.UP) {

        ship.boost();

    } else if (key === keys.RIGHT) {

        ship.rotate(behavior.SHIP_RIGHT_ANGLE);

    } else if (key === keys.LEFT) {

        ship.rotate(behavior.SHIP_LEFT_ANGLE);

    } else if (key === keys.SPACEBAR) {

        ship.shoot();
        key = 0;

    }

}

/**
 * pede para que cada objeto renderizar-se
 * ela executa ela mesmo.
 */

function draw() {

    //limpo a tela antes de escrever o proximo frame
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    //verifico se algum tiro já foi disparado
    if(ship.lasers !== []) {

        //itero dentro dos lasers e renderizo cada um deles
        for (var i = 0; ship.lasers.length > i; i++) {

            ship.lasers[i].render();

        }

    }

    //itero dentro do array dos asteroids e renderizo cada um deles
    for (var i = 0; asteroids.length > i; i++) {

        asteroids[i].render(asteroids[i].center.x, asteroids[i].center.y, ctx);

    }

    //renderizo a nave
    ship.render(ship.center.x, ship.center.y, ctx);

}


/**
 * a função itera dentro de cada asteroid, depois itera dentro de cada laser
 * para cada  laser é feito uma verificação se existe ou nao um ponto dentro
 * tendo um ponto dentro
 *
 * se houver algum ponto, ela deletara aquele asteroid do array com os outros
 * se o tamanho deles permitir que sejam adicionadas novas frações, será adicionado
 */

function colision() {

    //itero dentro de cada array na tela
    for (var c = 0; asteroids.length > c; c++) {

        //pego uma referencia desse array
        var asteroid = asteroids[c];

        //itero dentro de cada laser disparado
        for (var g = 0, lenght2 = ship.lasers.length; lenght2 > g; g++) {

            //faço uma referencia desse laser para processos futuros
            var laser = ship.lasers[g];

            //uso o algoritmo PNPOLY para determinar se o centro do laser mais as arrestras
            //se encontram dentro do asteroid
            if(asteroid.haveAPoint(laser.position.x, laser.position.x)){

                //se estiverem, retiro o laser a partir da referencia do array, apangando ele da tela
                ship.lasers.splice(g, 1);
                g--;

                //diminuo o tamanho do loop para se adequar ao numero de lasers disparado que se encontra dentro da tela
                lenght2--;

                //se o tamanho da asteroid for maior que 1/4 do tamanho original
                if (asteroid.s > asteroid.s/4) {

                    //são criados dois novos asteroids que são aleatorios e possuem o mesmo centro
                    var nAst = new Asteroid().create(dots.ASTEROIDS).scale((asteroid.s/2));
                    nAst.center.x = asteroid.center.x;
                    nAst.center.y = asteroid.center.y;

                    var nAst2 = new Asteroid().create(dots.ASTEROIDS).scale((asteroid.s/2));
                    nAst2.center.x = asteroid.center.x;
                    nAst2.center.y = asteroid.center.y;

                    //concateno eles na lista de arrays
                    var nA = [nAst, nAst2];
                    var a = asteroids.slice(c);
                    asteroids = a.concat(nA);

                    //acrescento 100 pontos no placar
                    score(100);

                }

                asteroids.slice(c);

            }

        }

    }

}

/**
 * É uma implementação mais facil da função random nativa do JS
 * já que utilizarei muitas vezes números aleatorios fiz assim.
 */

function randomValue(min, max) {

    //arrendondo um numero aleatorio float entre 0 e 1 e multiplico pelo delta entre o maximo e minimo
    //já que um zero não é o que quero acrescento o minimo
    return Math.round(Math.random()*(max - min) + min);

}

/**
 * limpa toda a execução anterior proporcionando um novo jogo
 *
 */

function reset() {

    //limpo a tela antes de apagar o progresso atual
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    //salvo a pontuação do usuário em um variavel para o alert
    var ap = fpoints;

    alert("O jogo acabou, sua pontuação foi de: " + ap);

    //limpo os processos
    clearInterval(col);
    clearInterval(upd);
    clearInterval(cmd);
    clearInterval(wtc);
    clearInterval(anim);

    //limpo o HUD para a próxima partida
    var pts, displayTime;

    pts = document.getElementById("score");
    pts.innerHTML = "000";

    displayTime = document.getElementById("watch");
    displayTime.innerHTML = "1:00";

    //limpo as variaveis
    asteroids = [];
    key = 0;
    ship = null;
    fpoints = 0;
    time = 59;

    //saio da função
    return;

}

/**
 * acrescento os pontos dentro da variavel na interface
 */

function score(points) {

    var score = document.getElementById("score");
    fpoints += points;
    score.innerHTML = fpoints;

}

/**
 *  escrevo o tempo restante na interface
 */

function watch() {

    var leftTime = document.getElementById("watch");
    leftTime.innerHTML = time;

    //faço o decremento de 1 inteiro no tempo restante a cada vez que a função é executada
    time -= 1;

    //como nao existe segundo negativo, caso o contador seja menos 1 eu o torno 0
    if (time === -1) {
        time = 0;
        reset();
    }

}
