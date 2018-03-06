/**
 * Classe do laser, ele é um poligono, por isso deveria ser um classe com heranças...
 * Ela basicamente se baseia na nave. Tem como argumentos o centro da nave e o angulo,
 * me dando o poder de acelerar ela de acordo com a posição da nave.
 *
 * @param x
 * @param y
 * @param direction
 * @returns {Laser}
 * @constructor
 */

function Laser(x, y, direction) {

    //definindo as propriedades do futuro objeto
    this.coordinates = dots.LASER_PATTERN;
    this.position = {
        x : null,
        y : null
    };

    this.angle = direction;

    //passando para o objeto os args do construtor
    this.position.x = x;
    this.position.y = y;

    /**
     * acelera o vetor de acordo com o definido no arquivo de config
     *
     * @returns {Laser}
     */

    this.boost = function() {

        //aqui, aceleramos o vetor para criar MOVIMENTO DIRECIONADO
        this.position.x += behavior.LASER_VELOCITY * Math.cos(this.angle);
        this.position.y += behavior.LASER_VELOCITY * Math.sin(this.angle);

        return this;

    };

    /**
     * renderiza igual as outras !EXATAMENTE IGUAL!, por isso nao irei comentar.
     *
     * Mas saiba que ela itera de dois em doi e risca formando arestras
     *
     * @returns {Laser}
     */

    this.render = function() {

        ctx.beginPath();

        ctx.moveTo( this.coordinates[0] + this.position.x, this.coordinates[1] + this.position.y );

        var len = this.coordinates.length;

        for (var i = 2; len > i; i += 2) {

            ctx.lineTo(this.coordinates[i] + this.position.x, this.coordinates[i + 1] + this.position.y );

        }

        ctx.fillStyle = ctxValues.STROKE_COLOR;
        ctx.fill();

        return this;

    };

    return this;

}
