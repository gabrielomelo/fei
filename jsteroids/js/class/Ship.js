/**
 * Classe da nave espacial.
 * como atributos ela tem a posição do centro e de cada vertice. Além disso
 * possui o mais vital para o funcionamento do jogo que é o angulo que ela se encontra.
 * JÁ QUE O CANVAS É PROJETADO DE PONTA CABEÇA, TIVE QUE DEFINIR O ANGULO INICIAL como
 * TRES PI SOBRE 2.
 *
 * @param position
 * @returns {Ship}
 * @constructor
 */


//o nome nao ficou claro, porém sao os pontos que formam as coordenadas do objeto
function Ship(position){

    //defino as propriedades do objeto
    this.angle = behavior.SHIP_DEFAULT_ANGLE;
    this.position = position;
    this.lasers = [];

    //objeto que para mim é um struct com o centro do objeto
    this.center = {

      x: undefined,
      y: undefined

    };

    /**
     * A função de rotação do poligono, utiliza de conceitos de trigonometria.
     * Ela cria um triangulo imaginario dentro do poligono em questão, e calcula
     * qual sera é a DIFERENCA E SOMA entre os SENOS E COSSENOS, compensando, MEDIANTE
     * O PRODUTO DE CADA ABCISSA E ORDENADA, o status de cada coordenada do poligono
     * para o  angulo atual.
     *
     * @param angle
     * @returns {Ship}
     */

    this.rotate = function(angle) {

        //soma o angulo passado como argumento e atualiza a posição atual
        this.angle += angle;

        //itera em cada coordenada do poligono
        for (var i = 0; this.position.length > i; i += 2) {

            //isolo o x e y de cada vertice
            var x = this.position[i];
            var y = this.position[i+1];

            //rotaciono a posição dos pontos de acordo com a soma do argumento com o status anterior
            this.position[i] =  Math.cos(angle) * x - Math.sin(angle) * y;
            this.position[i+1] = Math.sin(angle) * x +  Math.cos(angle) * y;

        }

        return this;

    };

    /**
     * essa função usa de conceitos de trigonometria para funcionar. Ela depende do angulo da própia nave
     * para poder criar um vetor naquela direção. as proposições anteriores a este código mencionado, verificam
     * a altura e largura do contexto, apagando todo objeto que exceder esse limite.
     *
     * @returns {Ship}
     */

    this.boost = function() {

        //se a nave ultrapassar o limite da tela eu jogo ela para o outro lado
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

        //multiplico o vetor pela velocidade, está como aceleração :(
        this.center.x += behavior.SHIP_ACELERATION * Math.cos(this.angle);
        this.center.y += behavior.SHIP_ACELERATION * Math.sin(this.angle);

        return this;

    };

    /**
     * cria uma nova instancia do laser e adiciona na lista de elementos do mesmo tipo
     * retorna o proprio objeto para podermos concatenar operações
     *
     * @returns {Ship}
     */

    this.shoot = function() {

        //crio um novo laser com o mesmo angulo da nave e a origem sendo o centro da nave
        this.lasers.push( new Laser( this.center.x, this.center.y, this.angle ) );

        return this;

    };

    /**
     * percorre o array com as coordenadas em pares, e risca o intervalo entre as vertices
     * usando o método stroke.
     *
     * @param x
     * @param y
     * @param ctx
     * @returns {Ship}
     */

    this.render = function(x, y, ctx) {

        //aviso que aqui começa um desenho
        ctx.beginPath();

        //posiciono a caneta no primeiro vertice
        ctx.moveTo( this.position[0] + this.center.x, this.position[1] + this.center.y );

        //pego o tamanho o array com as posiçoes
        var len = this.position.length;

        //itero em pares para poder  riscar uma reta entre eles
        for (var i = 2; len > i; i += 2) {

            ctx.lineTo(this.position[i] + this.center.x, this.position[i + 1] + this.center.y );

        }

        //riso com a cor da configuração
        ctx.strokeStyle = ctxValues.STROKE_COLOR;
        ctx.stroke();

        return this;

    };

   /**
    * Função que recebe um valor númerico e itera dentro das coordenadas
    * multiplicando e escalando (aumentando ou diminuindo) os pontos.
    * Retorna o próprio objeto para que eu possa concatenar as operações.
    * */

    this.scale = function(size) {

        //pego o tamanho do array com as coordenadas
        var len = this.position.length;

        //itero e escalo de acordo com o argumento
        for( var i = 0; len > i; i++ ) {
            this.position[i] *= size;
        }

        return this;

    };

    return this;

}
