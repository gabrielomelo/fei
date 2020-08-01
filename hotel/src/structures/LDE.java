package structures;

/**
 * Lista dinâmica encadeada.
 * Criada por ahmed e aprimorada por gabriel.
 * @author ahmed 
 * @author gabriel
 * @param <T> 
 */

public class LDE<T extends Comparable<T>> {

    public No primeiro;
    public Integer n;

    public LDE() {
        n = 0;
        primeiro = null;
    }

    public boolean insert(T x) {
        No novo = new No();
        novo.setValor(x);
        novo.setProximo(null);
        No anterior = null;
        No proximo = this.primeiro;

        if (proximo != null) {
            while (proximo != null) {
                anterior = proximo;
                proximo = proximo.getProximo();
            }

            if (anterior != null) {
                anterior.setProximo(novo);
            } else {
                primeiro = novo;
            }
        } else {
            primeiro = novo;
        }

        novo.setProximo(proximo);
        n++;
        return true;
    }

    public boolean remove(int idx) {
        No<T> atual = primeiro;
        No<T> anterior = null;
        int i;
        for (i = 0; i < idx - 1 && atual != null; i++) {
            anterior = atual;
            atual = atual.getProximo();
        }

        if (anterior != null) {
            anterior.setProximo(atual.getProximo());
        } else {
            primeiro = atual.getProximo();
        }
        
        this.n--;
        return true;
    }
    
    boolean remove(T value)
    {
        No<T> actual = primeiro;
        No<T> previous = null;

        while((actual != null) && (actual.getValor().compareTo(value) != 0)){
            previous = actual;
            actual = actual.getProximo();
        }

        if(actual == null) return false;

        if(previous != null)
            previous.setProximo(previous.getProximo());
        else
            primeiro = actual.getProximo();
        
        this.n--;
        return true;
    }

    // Retorna o nó pelo indice
    public No<T> search(int index) {
        No<T> atual = primeiro;
        Integer i = 0;
        while (atual != null && !i.equals(index)) {
            atual = atual.getProximo();
            i++;
        }
        if (atual != null)
            return atual;        
        return null;
    }
    
    // Retorna o nó pelo valor armazenado
    public No<T> search(T value) {
        No<T> atual = primeiro;
        while (atual != null && (atual.getValor().compareTo(value) != 1)) {
            atual = atual.getProximo();
        }
        if (atual != null) return atual;        
        return null;
    }

    public void print() {
        No atual = primeiro;
        while (atual != null) {
            System.out.println(atual.getValor());
            atual = atual.getProximo();
        }
    }
    
    public T[] toArray() {
        Object[] temp = new Object [this.n];
        
        for(int i = 0; i < this.n; i++)
            temp[i] = this.search(i).getValor();
        
        return (T[]) temp;
    }

    public int size() {
        return this.n;
    }

}
