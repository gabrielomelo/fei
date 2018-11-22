package simulacao;

import java.util.ArrayList;
import veiculos.Bicicleta;
import veiculos.Caminhao;
import veiculos.Carro;
import veiculos.Moto;

/**
 * @author Gabriel Melo RA: 22217015-1
 * Classe Simulação executa o loop principal e inicia o mundo.
 */
public class Simulacao {

    // Método principal

    /**
     * Ponto de entrada do programa, executa o começo da simulação
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        
        // Inicializando os vetores com os veiculos usando durante a simulação
        ArrayList<Carro> carros = new ArrayList<>();
        ArrayList<Caminhao> caminhoes = new ArrayList<>();
        ArrayList<Moto> motos = new ArrayList<>();
        ArrayList<Bicicleta> bicicletas = new ArrayList<>();
        
        // Instancia 10 veiculos de cada tipo de maneira aleatória
        for (int i = 0; i < 10; i++) {
            carros.add(new Carro());
            caminhoes.add(new Caminhao());
            motos.add(new Moto());
            bicicletas.add(new Bicicleta(false));
        }
        
        // Loop da simulação - Cria um novo "estado" de mundo para cada iteração.
        while(true) {
            
            // Inicio um novo mundo para cada Iteração
            Mundo mundo = new Mundo(carros, caminhoes, motos, bicicletas);
            mundo.desenhaMundo();
            // Sleep na thread, caso não seja feito, o acompanhamento pelo terminal fica díficil
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println("Idontknow");
            }
        }
    }
}
