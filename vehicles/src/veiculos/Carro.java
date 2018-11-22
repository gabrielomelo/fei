package veiculos;

/**
 *
 * @author Gabriel
 */
public class Carro extends Veiculo {

    /**
     * Numero de passageiros
     */
    private int numPassageiros;
    
    /**
     * cor do veiculo em questao
     */
    public static final String bgCor = "\u001B[41m" + " " + Veiculo.corReset;
    
    /**
     * Construtor que define as variaveis como cor do veiculo e velocidade.
     */
    public Carro() {
        this.velocidade = 2;
    }
	
    /**
     * @return numero de passageiros
     */
    public int getNum_passageiros() {
        return numPassageiros;
    }
    
    @Override
    public String show() {
        return Carro.bgCor;
    }

    /**
     * @param num_passageiros numero de passageiros
     */
    public void setNum_passageiros(int num_passageiros) {
	this.numPassageiros = num_passageiros;
    }
}
