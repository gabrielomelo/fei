package Service;
import structures.Queue;

/**
 * Essa classe roda numa thread separada usando a interface runnable.
 * Ela agenda a execução de serviços ao invés de executar após o clique, ela recebe
 * uma lista que é alimentada de acordo com a demanda dos usuários. Caso rodassem
 * várias instancias dessa aplicação, implementaria uma execução em lotes, onde
 * a pilha atual seria clonada e a referencia esvaziada, evitando duplicatas e
 * sincronizando a a "base de dados" da aplicação.
 * @author Gabriel Melo
 */
public class ServiceScheduler implements Runnable {

    private final int DEFAULT_INTERVAL = 200;
    private final int DEFAULT_INTERVAL_MULTIPLIER = 1000;
    private int interval;
    
    public ServiceScheduler(Queue<Service> events) {
        this.events = events;
        this.interval = DEFAULT_INTERVAL;
    }
    
    public ServiceScheduler(Queue<Service> events, int secInterval) {
        this.events = events;
        this.interval = secInterval * DEFAULT_INTERVAL_MULTIPLIER;        
    }
    
    @Override
    public void run() {
        try {
            while(true) {
                if(this.events.size() != 0)
                    this.events.unqueue().release();
                else
                    System.out.println("Fila de servicos vazia");
                Thread.sleep(this.interval);
            }
        } catch (InterruptedException e) {
            System.out.println("Erro na thread");
            Thread.currentThread().interrupt();
        }
    }
    
    private Queue<Service> events;

}
