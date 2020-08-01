package com.gabriel.entities;

/*
 * @author gabrielomelo
 * This remain in the Model Realm
 */

import java.io.Serializable;

public class User implements Serializable {

    public User () {}

    public User(String name, String email, String CPF, String login, String password) {
        this.setName(name).setEmail(email).setCPF(CPF).setLogin(login).setPassword(password);
    }

    public Integer getID() {
        return ID;
    }

    public User setID(Integer ID) {
        this.ID = ID;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCPF() {
        return CPF;
    }

    public User setCPF(String CPF) {
        this.CPF = CPF;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }


    private Integer ID;
    private String name;
    private String email;
    private String CPF;
    private String login;
    private String password;

}
