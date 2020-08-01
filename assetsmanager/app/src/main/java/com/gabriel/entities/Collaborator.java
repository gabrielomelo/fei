package com.gabriel.entities;

import java.io.Serializable;
import java.util.ArrayList;

public class Collaborator implements Serializable {

    public Integer getID() {
        return this.ID;
    }

    public Collaborator setID(Integer ID) {
        this.ID = ID;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Collaborator setName(String name) {
        this.name = name;
        return this;
    }

    public String getCPF() {
        return CPF;
    }

    public Collaborator setCPF(String CPF) {
        this.CPF = CPF;
        return this;
    }

    @Override
    public String toString() {
        return "Nome: " + name + ". CPF: " + CPF;
    }

    private Integer ID;
    private String name;
    private String CPF;
}
