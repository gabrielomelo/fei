package Service;

/**
 * Interface de serviço, possui método que aciona a execução do serviço.
 * @author Gabriel Melo
 */

public abstract class Service implements Comparable<Service> {
    public abstract void release();
}
