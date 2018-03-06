/**
 * @author gabrielomelo
 * @link github.com/gabrielomelo
 *
 * Orientei a objeto, mas durante meus estudos, percebi que o unico modo de criar classes é com o
 * construtor, ou seja, a função propriamente dita. Os objetos são similares as structs, podem estruturar dados
 * já que são objetos, mas não classes.
 *
 * Como trabalho não tive tempo de estudar sobre, porém gostaria de ter usado herança, polimorfismo e etc, visto que TUDO NESTE JOGO
 * é um poligono.
 *
 * @returns {Asteroid}
 * @constructor
 */

function Asteroid() {

    this.position;

    // Este OBJETO é usado para armazenar as coordenadas do centro da figura.

    this.center = {
        x: undefined,
        y: undefined
    };

    this.s;
    this.vector;
    this.radius;

    /**
     * Com essa função eu defino em ordem:
     * 1° qual dos shapes de asteroid esse objeto terá, será um número aleatório
     * entre 0 e 1 multiplicado por 4.
     * 2° um arco aleatório para utilizar nas funções de seno e cosseno para definir a direção.
     * 3° a velocidade (grandeza) do vetor.
     *
     * @returns {Asteroid}
     */

    this.create = function(dots) {

        this.position = dots[randomValue(0, 5)];
        this.radius = 2*Math.PI*Math.random()*6+1;
        this.vector = randomValue(behavior.ASTEROIDS_MIN_VELOCITY, behavior.ASTEROIDS_MAX_VELOCITY);

        return this;

    };

    /**
     * A primeira sequencia de proposiçoes verifica se o x e o y passaram da tela
     * se isso acontecer, entao ela mudará a posição do poligono para o lado oposto.
     *
     * A segunda parte calcula a posição do centro usando a velocidade "vezes" o seno
     * e cosseno do arco (graus) inicial.
     *
     * @returns {Asteroid}
     */

    this.move = function() {

        //verifico se ele passou o  limite da tela no X e Y e jogo ela para o outro lado
        if(this.center.x > ctxValues.WIDTH) {
            this.center.x = 0;
        }else if (this.center.x < 0) {
            this.center.x = ctxValues.WIDTH;
        }

        if (this.center.y > ctxValues.HEIGHT) {
            this.center.y = 0;
        }else if (this.center.y < 0) {
            this.center.y = ctxValues.HEIGHT;
        }

        //adicionando movimento multiplicando o vetor pela velocidade
        this.center.x += this.vector * Math.cos(this.radius);
        this.center.y += this.vector * Math.sin(this.radius);

        return this;

    };

   /**
    * Recebe um x  e y aleatórios e o contexto de renderização.
    * Itera dentro das coordenadas de 2 em 2, alcançando o x a abcissa
    * de cada ponto.
    *
    * Primeiramente ela move o curso de desenho para a primeira coordenada de depois
    * itera de 2 em 2 e move para o próximo ponto do poligono.
    *
    * Depois do loop eu defino uma cor e "risco".
    *
    * @returns {Asteroid}
    * */

    this.render = function(x, y, ctx) {

            //digo ao canvas que vou a partir daqui, risca-lo
            ctx.beginPath();

            //posiciono o cursor na primeira coordenada
            ctx.moveTo( this.position[0] + this.center.x, this.position[1] + this.center.y );

            //tamanho do "for each"
            var len = this.position.length;

            //itero de 2 em 2 para desenhar o poligono por linhas
            for (var i = 2; len > i; i += 2) {

                ctx.lineTo(this.position[i] + this.center.x, this.position[i + 1] + this.center.y );

            }

            //digo que quero um linha e nao o preenchimento
            ctx.strokeStyle = ctxValues.STROKE_COLOR;
            ctx.stroke();

            //retorno o objeto para concatenar
            return this;

    };

   /**
    * Função que recebe um valor real (float) e itera dentro das coordenadas
    * multiplicando e escalando os pontos.
    * Retorna o próprio objeto para que eu possa concatenar as operações.
    *
    * @args size
    * @returns {Asteroid}
    * */

    this.scale = function(size) {

        this.s = size;

        //itero em cada ponto do objeto e multiplico ele pelo mesmo valor, mantendo a proporcionalidade
        for( var i = 0; this.position.length > i; i++ ) {

            this.position[i] *= this.s;

        }

        return this;

    };

    /**
     * Recebe o ultimo o tiro disparado e itera dentro dos vertices do poligono, se caso o delta
     * entre o centro do tiro e do asteroid for negativo, como na geometria, eles se intersectam.
     * Se houver o impacto ele retorna true, se caso nao houver ele retorna false
     *
     * @returns true or false
     */

    this.haveAPoint = function(x, y) {

        //definindo as variaveis as coordenadas e o tamanho do indice
        //a primeira é apenas o valor logico que no fim do processo indicara se houve ou nao uma  colisao

        var c = false;
        var p = this.position;
        var len = p.length;

        for (var i = 0, j = len-2; i < len; i += 2) {

            //definindo a abcissa do ponto

            var px1 = p[i] + this.center.x;
            var px2 = p[j] + this.center.x;

            // definindo a posição da ordenada do ponto

            var py1 = p[i+1] + this.center.y;
            var py2 = p[j+1] + this.center.y;

            //comparando as coordenadas dos dois objetos e definido elas colidiram
            //a segunda proposição é a questão da reta semi infinita descrita no artigo
            //é calculado o coeficiente angular "(px2-px1) * (y-py1) / (py2-py1) + px1)"
            //em seguida há a comparação com abcissa do objeto. Ou seja, se o coeficiente angular
            //der um numero menor que o x do ponto a reta que o laser forma corta o objeto

            if ( ( py1 > y !== py2 > y )
                && ( x < (px2-px1) * (y-py1) / (py2-py1) + px1) ) {

                //negando o boleano definido no começo da função
                c = !c;

            }

            //j é o lado oposto de i ou seja, sao angulos convexos dentro do poligono, por isso demanda muito processamento
            j = i;

        }

        //retornando a proposição
        return c;

    };

    return this;

}
