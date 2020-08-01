package com.gabriel.entities;

import java.io.Serializable;

public class Assignment implements Serializable {

    public Integer getID() {
        return ID;
    }

    public Assignment setID(Integer ID) {
        this.ID = ID;
        return this;
    }

    public Integer getCollaboratorID() {
        return collaboratorID;
    }

    public Assignment setCollaboratorID(Integer collaboratorID) {
        this.collaboratorID = collaboratorID;
        return this;
    }

    public Integer getAssetID() {
        return assetID;
    }

    public Assignment setAssetID(Integer assetID) {
        this.assetID = assetID;
        return this;
    }

    @Override
    public String toString() {
        return "Ass. ID: " + ID + ". Colaborador ID: " + collaboratorID
                + ". Asset ID: " + assetID;
    }

    private Integer ID;
    private Integer collaboratorID;
    private Integer assetID;
}
