package structures;

/**
 * 
 * @author ahmed
 * @param <T> 
 */

public class No<T> {
    
    private T valor;
    private No proximo;

    public T getValor() {
        return valor;
    }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public No getProximo() {
        return proximo;
    }

    public void setProximo(No proximo) {
        this.proximo = proximo;
    }    
}
