package simulacao;

import java.util.ArrayList;
import veiculos.Bicicleta;
import veiculos.Caminhao;
import veiculos.Carro;
import veiculos.Moto;

/**
 * Classe mundo, que controla o comportamento de tudo.
 * @author Gabriel
 */
public final class Mundo {
    
    // Mapa do mundo 60x30
    private String mapa[][] = {
        {"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","2","2","2","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},            
        {"1","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","0","1"},
        {"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"},         
    };
    
    private final ArrayList<Caminhao> caminhoes;
    private final ArrayList<Carro> carros;
    private final ArrayList<Moto> motos;
    private final ArrayList<Bicicleta> bikes;
    
    private int tamanho_x = 60;
    private int tamanho_y = 30;
    
    /**
     * Construtor que recebe os veiculos que habitarao o mundo.
     * @param carros
     * @param caminhoes
     * @param motos
     * @param bikes 
     */
    Mundo(ArrayList<Carro> carros, 
            ArrayList<Caminhao> caminhoes, 
            ArrayList<Moto> motos,
            ArrayList<Bicicleta> bikes) 
    {
        this.motos = motos;
        this.bikes = bikes;
        this.carros = carros;
        this.caminhoes = caminhoes; 
    }
    
    /**
     * Desenha o mapa da cidade na linha de comando
     * Itera dentro da matriz e imprime cada character no CMD
     */
    public void desenhaMundo() {
        this.compoeMundo();
        
        for(int i = 0; i <= this.tamanho_y; i++) {
            for(int j = 0; j < this.tamanho_x; j++) {
                if(mapa[i][j].equals("0"))
                    mapa[i][j] = "\u001B[40m" + " " + "\u001B[0m"; //Preto
                else if(mapa[i][j].equals("1"))
                    mapa[i][j] = "\u001B[47m" + " " + "\u001B[0m"; //Branco
                else if(mapa[i][j].equals("2"))
                    mapa[i][j] = "\u001B[42m" + " " + "\u001B[0m"; //Verde
            }
        }
        
        System.out.println("Número de Carros: " + this.carros.size() 
                + " Número de Caminhoes: " + this.caminhoes.size() 
                + " Número de Motos: " + this.motos.size()
                + " Número de Bicicletas: " + this.bikes.size()
        );
        
        System.out.print(Carro.bgCor + " Carro ");
        System.out.print(Caminhao.bgCor + " Caminhao ");
        System.out.print(Moto.bgCor + " Moto ");
        System.out.println(Bicicleta.bgCor + " Bicicleta");
        
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                System.out.print(mapa[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Compoe o mundo usando os veiculos armazenados na memoria e regras do mundo
     * Deve haver uma solução mais elegante para este problema.
     */
    public void compoeMundo() {
        
        this.detectaColisao();
        this.detectaFabrica();
        
        // Move os carros
        for (int i = 0; i < carros.size(); i++) {
            this.carros.get(i).move();
        }
        
        // Move os caminhoes
        for (int i = 0; i < caminhoes.size(); i++) {
            this.caminhoes.get(i).move();
        }
        
        // Move as motos
        for (int i = 0; i < motos.size(); i++) {
            this.motos.get(i).move();
        }
        
        // Move as bikes 
        for (int i = 0; i < bikes.size(); i++) {
            this.bikes.get(i).move(this.bikes.get(i).status);
        }
        
        for(int i = 0; i < this.carros.size(); i++) {
            mapa[this.carros.get(i).getY()][this.carros.get(i).getX()] = this.carros.get(i).show();
        }
        
        for(int i = 0; i < this.caminhoes.size(); i++) {
            mapa[this.caminhoes.get(i).getY()][this.caminhoes.get(i).getX()] = this.caminhoes.get(i).show();
        }
        
        for(int i = 0; i < this.motos.size(); i++) {
            mapa[this.motos.get(i).getY()][this.motos.get(i).getX()] = this.motos.get(i).show();
        }
        
        for(int i = 0; i < this.bikes.size(); i++) {
            mapa[this.bikes.get(i).getY()][this.bikes.get(i).getX()] = this.bikes.get(i).show();
        }
    }

    /**
     * Detecta a Colisao entre dois veiculos dentro do mapa e lida com eles a partir da matriz de confusoes
     */
    public void detectaColisao() {
        /// Colisão carro com carro
        for (int i = 0; i < carros.size(); i++) {
            for (int j = 0; j < carros.size(); j++) {
                if (carros.get(j).getX() == carros.get(i).getX() && carros.get(j).getY() == carros.get(i).getY()) {
                    if (i != j) carros.remove(carros.get(i));
                }
            }
        }
        // Colisão caminhão com caminhão

        for (int i = 0; i < caminhoes.size(); i++) {
            for (int j = 0; j < caminhoes.size(); j++) {
                if (caminhoes.get(j).getX() == caminhoes.get(i).getX() && caminhoes.get(j).getY() == caminhoes.get(i).getY()) {
                    if (i != j) caminhoes.remove(caminhoes.get(i));
                }
            }
        }

        // Colisão moto com moto
        for (int i = 0; i < motos.size(); i++) {
            for (int j = 0; j < motos.size(); j++) {
                if (motos.get(j).getX() == motos.get(i).getX() && motos.get(j).getY() == motos.get(i).getY()) {
                    if (i != j) motos.remove(motos.get(i));
                }
            }
        }
        
        // Colisão bike com bike
        for (int i = 0; i < bikes.size(); i++) {
            for (int j = 0; j < bikes.size(); j++) {
                if (bikes.get(i).getX() == bikes.get(j).getX() && bikes.get(i).getY() == bikes.get(j).getY()) {
                        if (i != j) bikes.remove(bikes.get(i));
                }
            }
        }
        
        // Colisão Carro com bike
        for (int i = 0; i < carros.size(); i++) {
            for (int j = 0; j < bikes.size(); j++) {
                if (carros.get(i).getX() == bikes.get(j).getX() && carros.get(i).getY() == bikes.get(j).getY()) {
                    if (!(carros.size() > bikes.size()))
                        bikes.remove(bikes.get(j));
                }
            }
        }
        
        // Colisao entre Caminhao com bike
        for (int i = 0; i < caminhoes.size(); i++) {
            for (int j = 0; j < bikes.size(); j++) {
                if (caminhoes.get(i).getX() == bikes.get(j).getX() && caminhoes.get(i).getY() == bikes.get(j).getY()) {
                    if (!(caminhoes.size() > bikes.size()))
                        bikes.remove(bikes.get(j));
                }
            }
        }
        
        // Colisao entre moto com bike
        for (int i = 0; i < motos.size(); i++) {
            for (int j = 0; j < bikes.size(); j++) {
                if (motos.get(i).getX() == bikes.get(j).getX() && motos.get(i).getY() == bikes.get(j).getY()) {
                    if (!(motos.size() > bikes.size()))
                        bikes.remove(bikes.get(j));
                }
            }
        }

        // Colisão entre caminhão e carro
        for (int i = 0; i < caminhoes.size(); i++) {
            for (int j = 0; j < carros.size(); j++) {
                if (carros.get(j).getX() == caminhoes.get(i).getX() && carros.get(j).getY() == caminhoes.get(i).getY()) {
                    if (!(caminhoes.size() > carros.size()))
                        carros.remove(carros.get(j));
                }
            }
        }

        // Colisão entre caminhão e moto
        for (int i = 0; i < caminhoes.size(); i++) {
            for (int j = 0; j < motos.size(); j++) {
                if (motos.get(j).getX() == caminhoes.get(i).getX() && motos.get(j).getY() == caminhoes.get(i).getY()) {
                    if (!(caminhoes.size() > motos.size()))
                        motos.remove(motos.get(j));
                }
            }
        }

        // Colisão entre moto e carro
        for (int i = 0; i < carros.size(); i++) {
            for (int j = 0; j < motos.size(); j++) {
                if (motos.get(j).getX() == carros.get(i).getX() && motos.get(j).getY() == carros.get(i).getY()) {
                    if (!(carros.size() > motos.size()))
                        motos.remove(motos.get(i));
                }
            }
        }
    }
    
    /**
     * Detecta fabricas dentro do mundo que estão na mesma posição do carro e depois cria novos.
     */
    public void detectaFabrica() {
        // Itera nos carros
        for (int i = 0; i < this.carros.size(); i++) {
            int x = this.carros.get(i).getX();
            int y = this.carros.get(i).getY();
            if(this.mapa[y][x].equals("2") && !(this.carros.get(i).getFabrica())) {
                this.carros.add(new Carro());
                this.carros.get(i).setFabrica(true);
            }
        }
        
         // Itera nos Caminhoes
        for (int i = 0; i < this.caminhoes.size(); i++) {
            int x = this.caminhoes.get(i).getX();
            int y = this.caminhoes.get(i).getY();
            if(this.mapa[y][x].equals("2") && !(this.caminhoes.get(i).getFabrica())) {
                this.caminhoes.add(new Caminhao());
                this.caminhoes.get(i).setFabrica(true);
            }
        }
        
        // Itera nas Motos
        for (int i = 0; i < this.motos.size(); i++) {
            int x = this.motos.get(i).getX();
            int y = this.motos.get(i).getY();
            if(this.mapa[y][x].equals("2") && !(this.motos.get(i).getFabrica())) {
                this.motos.add(new Moto());
                this.motos.get(i).setFabrica(true);
            }
        }
        
        // Itera nas Bicicletas
        for (int i = 0; i < this.bikes.size(); i++) {
            int x = this.bikes.get(i).getX();
            int y = this.bikes.get(i).getY();
            if(this.mapa[y][x].equals("2") && !(this.bikes.get(i).getFabrica())) {
                this.bikes.add(new Bicicleta(false));
                this.bikes.get(i).setFabrica(true);
            }
        }
    }

    /**
     * @return tamanho do eixo X em inteiro 
     */
    public int getTamanho_x() {
        return tamanho_x;
    }

    /**
     * tamanho do eixo X
     * @param tamanho_x
     */
    public void setTamanho_x(int tamanho_x) {
        this.tamanho_x = tamanho_x;
    }

    /**
     * @return tamanho do eixo Y em inteiro
     */
    public int getTamanho_y() {
        return tamanho_y;
    }

    /**
     * tamanho do eixo y
     * @param tamanho_y
     */
    public void setTamanho_y(int tamanho_y) {
        this.tamanho_y = tamanho_y;
    }
}
