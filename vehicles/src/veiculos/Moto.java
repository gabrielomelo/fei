package veiculos;

/**
 * Classe Moto.
 * @author Gabriel
 */
public class Moto extends Veiculo {

    /**
     * Tipo de Carro
     */
    private String tipo;
    
    /**
     * cor do veiculo em questao
     */
    public static final String bgCor = "\u001B[44m" + " " + Veiculo.corReset;
	
    /**
     * Construtor que define as variaveis como cor do veiculo e velocidade.
     */
    public Moto() {
        this.velocidade = 3;
    }

    /**
     * @return tipo de carro
     */
    public String getTipo() {
        return tipo;
    }

    @Override
    public String show() {
        return Moto.bgCor;
    }
    
    /**
     * @param tipo tipo de carro
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
