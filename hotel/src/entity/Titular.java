package entity;

/**
 * Entidade Titular implementa Comparable.
 * @author Gabriel Melo
 */
public class Titular implements Comparable<Titular> {
    private int id;
    private String name;
    private String CPF;
    private String dataNascimento;

    public Titular(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Titular setName(String name) {
        this.name = name;
        return this;
    }

    public String getCPF() {
        return CPF;
    }

    public Titular setCPF(String CPF) {
        this.CPF = CPF;
        return this;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public Titular setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    @Override
    public int compareTo(Titular o) {
        return (this.CPF.equals(o.getCPF())) ? 1 : 0;
    }

    public int getId() {
        return this.id;
    }
}
