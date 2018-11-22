package veiculos;

/**
 * Classe Caminhao.
 * @author Gabriel
 */
public class Caminhao extends Veiculo {
	
    private int capacidadeCarga;

    /**
     * cor do veiculo em questao
     */
    public static final String bgCor = "\u001B[43m" + " " + Veiculo.corReset;
    
    /**
     * Construtor que define as variaveis como cor do veiculo e velocidade.
     */
    public Caminhao() {
        this.velocidade = 1;
    }

    /**
     * @return capacidade de carga
     */
    public int getCapacidade_carga() {
        return capacidadeCarga;
    }

    @Override
    public String show() {
        return Caminhao.bgCor;
    }
    
    /**
     * @param capacidadeCarga recebe a capacidade de carga
     */
    public void setCapacidade_carga(int capacidadeCarga) {
        this.capacidadeCarga = capacidadeCarga;
    }
}
