package structures;

/**
 * Lista implementada com a LDE, o que a torna dinâmica, já que é 
 * necessário que ela cresca de acordo com a demanda.
 * @author Gabriel Melo
 * @param <T>
 */

public class Queue<T extends Comparable<T>> {
    
    private LDE<T> v;
    
    public Queue() {
        this.v = new LDE<>();
    }
    
    public boolean queue(T value) {
        return this.v.insert(value);
    }
    
    public T unqueue() {
        T r = this.v.search(0).getValor();
        this.v.remove(0);
        return r;
    }
    
    public void print() {
        this.v.print();
    }
    
    public int size() {
        return this.v.size();
    }
}
