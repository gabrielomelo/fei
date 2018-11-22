package veiculos;
import java.util.Random;

/**
 * Super Classe Veiculo, contém os metódos base para um objeto tipo veiculo.
 * @author Gabriel
 */
public abstract class Veiculo {
	
    /**
     * Posição atual do veiculo no eixo X
     */
    protected Integer x;

    /**
     * Posição atual do veiculo no eico Y
     */
    protected Integer y;

    /**
     * Velocidade do veiculo
     */
    protected Integer velocidade;

    /**
     * Flag que permite a fabricação de veiculos novos quando este
     * passa por cima de uma fabrica
     */
    protected boolean fabrica = false;
    
    /**
     * ANSI para a coloração dos veiculos
     */
    public static final String corReset = "\u001B[0m";
    
    /**
     * ANSI que permite a colorização do fundo de character com a cor do veiculo
     */
    public String bgCor;
	
    Veiculo() {
        Random gen = new Random();
        this.x = gen.nextInt(59 - 1) + 1;
        this.y = gen.nextInt(29 - 1) + 1;
    }
	
    /**
     * Funnção move o veiculo de acordo com a velocidade definida na classe herdeira.
     * Caso este esteja fora do "escopo" ele é redirecionado para o proximo valor do intervalo
     * este é o valor inicial.
     */
    public void move() {
        Random gen = new Random();
        int dir = gen.nextInt(4);

        switch (dir) {
            case 0: //direita
                this.x += this.velocidade;
                break;
            case 1: //esquerda
                this.x -= this.velocidade;
                break;
            case 2: //cima
                this.y += this.velocidade;
                break;
            case 3: //baixo
                this.y -= this.velocidade;
                break;
        }

        if(this.getX() < 1)
           this.setX(58);
        if(this.getX() > 58)
           this.setX(1); 
        // Y
        if(this.getY() < 1)
           this.setY(28); 
        if(this.getY() > 28)
           this.setY(1);
    }

    /**
     * Mostra a representação padrao do veiculo no terminal.
     * @return a string contendo o fundo com a cor da instancia atual do veiculo
     */
    abstract public String show();
        
    /**
     * @return X
     */
    public Integer getX() {
        return this.x;
    }
        
    /**
     * @param i inteiro que é o X do veiculo
     */
    public void setX(int i) {
        this.x = i;
    }
        
    /**
     * @return Y
     */
    public Integer getY() {
        return this.y;
    }
        
    /**
     * @param i inteiro que é o Y do veiculo
     */
    public void setY(int i) {
        this.y = i;
    }
        
    /**
     * recebe um booleano que define se o veiculo já gerou um novo.
     * @param f status de geração
     */
    public void setFabrica(boolean f) {
        this.fabrica = f;
    }
        
    /**
     * retorna um booleano que diz se o veiculo ja gerou um novo
     * @return status de geração
     */
    public boolean getFabrica () {
        return this.fabrica;
    }
        
    /**
     * define a velocidade
     * @param i velocidade
     */
    public void setVelocidade(int i) {
        this.velocidade = i;
    }
        
    /**
     * define a velocidade
     * @return velocidade
     */
    public Integer getVelocidade() {
        return this.velocidade;
    }
}
