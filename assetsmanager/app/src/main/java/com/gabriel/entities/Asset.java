package com.gabriel.entities;

import java.io.Serializable;

public class Asset implements Serializable {
    public Integer getID() {
        return ID;
    }

    public Asset setID(Integer ID) {
        this.ID = ID;
        return this;
    }

    public String getDescription() {
        return this.description;
    }

    public Asset setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public Asset setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String getHost() {
        return host;
    }

    public Asset setHost(String host) {
        this.host = host;
        return this;
    }

    @Override

    public String toString() {
        String string = this.ID + ". Descrição: " + this.description + ". S/N: " + this.serialNumber + ". Host: " + this.host;
        return string;
    }
    private Integer ID;
    private String description;
    private String serialNumber;
    private String host; // String porque preciso de resolver em alguma classe.
}
