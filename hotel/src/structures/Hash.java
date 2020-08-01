package structures;

import entity.Reserve;

/**
 * Hash, usa LES para crescer horizontalmente.
 * Recebe o tamanho da tabela e tem um limite horizonal.
 * @author gabriel
 * @param <T> 
 */

public class Hash<T extends Comparable> {

    private LES<T>[] v;
    private Integer max;
    private Integer size;
    
    public Hash (int max, int width){
        this.size = 0;
        this.max = max*width;
        this.v = new LES[this.max];
        
        for (int i = 0; i < this.max; i++) {
            this.v[i] = new LES<>(width);
        }
    }

    public boolean insert(T value) {
        if(this.size >= this.max) return false;

        boolean r = this.v[this.hash(value)].insert(value);

        if(!r) return false;

        this.size++;
        return true;
    }

    public boolean remove(T value) {
        if(this.size.equals(0)) return false;

        boolean r = this.v[this.hash(value)].remove(value);

        if(!r) return false;

        this.size--;
        return true;
    }

    public T search (T value) {
        return this.v[this.hash(value)].search(value);
    }

    public int size() {
        return this.size;
    }
    
    public LES<T> getLine(int value) {
        return this.v[this.hash(value)];
    }
    
    public void print() {
        for (int i = 0; i < this.max; i++) {
            this.v[i].print();
        }
    }

    public int getSize() {
        return this.size;
    }
    
    public int getMax() {
        return this.max;
    }

    private int hash(T value) {
        return Integer.parseInt(value.toString()) % this.max;
    }
    
    private int hash(int value) {
        return value % this.max;
    }
    
};
