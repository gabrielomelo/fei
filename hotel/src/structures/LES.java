package structures;

/**
 * Lista Estatica Sequencial.
 * @author Gabriel Melo
 * @param <T>
 */
public class LES <T extends Comparable> {
    
    private T[] v;
    private int size = 0;
    private int max;
    
    public LES(int max) {
        this.max = max;
        this.v = (T[]) new Comparable[this.max];
    }
    
    public T search(T value)
    {
        for (int i = 0; i < this.size; i++) {
            if(this.v[i].compareTo((T) value) == 1)
                return this.v[i];
        }

        return null;
    }
    
    public T search(int index) {
        return this.v[index];
    }
    
    public boolean insert(T value) {
        
        if(this.size >= this.max) return false;
        
        this.v[this.size++] = value;
        
        return true;
    }
    
    public boolean remove(int index) {
        if(index < 0 || index > this.max || this.size == 0) return false;

        // troca tudo uma posição atras
        for (int j = index; j <= this.size; j++)
            this.v[j] = this.v[j+1];

        this.size--;
        return true;
    }
    
    public boolean remove(T value) {
        if(this.size == 0 || value == null) return false;
        
        for (int j = 0; j < this.size; j++) {
            if(this.v[j].compareTo(value) == 1) {
                if(this.remove(j))
                    return true;
            }
        }
        
        return false;
    }
    
    public int size() {
        return this.size;
    }
    
    public void print() {
        for(int i = 0; i < this.size; i++) {
            System.out.println(this.v[i]);
        }
    }    
    
    // Retorna exatamente um array com tamanho igual o da LES e não MAX
    public Comparable[] toArray() {
        Comparable[] temp = new Comparable[this.size];
        for(int i = 0; i < this.size; i++) {
            temp[i] = (T) this.v[i];
        }
        return temp;
    }
}
