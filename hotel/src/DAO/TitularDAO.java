package DAO;
import entity.Titular;
import structures.LDE;
import structures.No;

/**
 * Classe de acesso de dados da entidade Titular
 * @author Gabriel Melo
 */

public class TitularDAO {
    
    private LDE<Titular> origin;
    
    public TitularDAO(LDE<Titular> origin) {
        this.origin = origin; // Caso isto fosse um sistema com bando de dados, colocaria aqui um caminho de um arquivo com um nome da base 
    }
    
    public Titular getTitularByID(int id) {
        return this.origin.search(id).getValor();
    }
    
    public Titular getSimilarNamed(String searchedName) {
        if(this.origin.size() == 0) return null; // caso ele não possua nenhum elemento é retornado null
        
        int i = 0;
        Titular temp = this.origin.search(i).getValor();
        
        // Itera até achar o nome desejado
        while((this.origin.size() != i) && !temp.getName().equals(searchedName)) {
            temp = this.origin.search(i).getValor();
            i++;
        }
        
        return temp;
    }
    
    public Titular getSimilarCPF(String searchedCPF) {
        if(this.origin.size() == 0) return null;
        
        No<Titular> n = this.origin.search(this.createTitular(searchedCPF, "", ""));
        if(n != null)
            return n.getValor();
        return null;
    }
    
    public Titular createTitular(String CPF, String name, String dtNasc) {
        Titular temp = new Titular(this.origin.size());
        temp.setCPF(CPF).setName(name).setDataNascimento(dtNasc);
        return temp;
    }
    
    public boolean insert(Titular titular) {
        return this.origin.insert(titular);
    }
    
    public boolean insert(String CPF, String name, String dtNasc) {
        return this.origin.insert(this.createTitular(CPF, name, dtNasc));
    }
    
}
