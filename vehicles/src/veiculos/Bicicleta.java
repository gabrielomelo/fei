package veiculos;

/**
 * Classe da Bicicleta - Aplica polimorfismo.
 * @author Gabriel
 */
public class Bicicleta extends Veiculo {
	
    private int numPassageiros; // Sem aplicação nenhuma?

    /**
     * Indica se a bicicleta pode se mover no frame atual.
     */
    public boolean status;
    
    /**
     * cor do veiculo em questao
     */
    public static final String bgCor = "\u001B[46m" + " " + Veiculo.corReset;

    /**
     * Construtor que define as variaveis como cor do veiculo e velocidade.
     * @param status indicador de movimento.
     */
    public Bicicleta(boolean status) {
        this.velocidade = 1; // A velocidade é 0.5, porém é definida como um por opção na implementação.
        this.status = status;
    }
        
    /**
     * Move a bicicleta caso a flag seja true.
     * Como comportamento da classe, quando esse função é chamada ela nega o status anterior.
     * @param status 
     */
    public void move(boolean status) {
        if(status) super.move();
        this.status = !status;
    }
	
    /**
     * @return numero de passageiros
     */
    public int getNum_passageiros() {
        return numPassageiros;
    }
    
    @Override
    public String show() {
        return Bicicleta.bgCor;
    }

    /**
     * @param num_passageiros 
     */
    public void setNum_passageiros(int num_passageiros) {
        this.numPassageiros = num_passageiros;
    }
}
