package com.gabriel.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.gabriel.dbutils.DBAdapter;
import com.gabriel.entities.Collaborator;

import java.util.ArrayList;
import java.util.List;

public class CollaboratorDAO {

    private DBAdapter db;

    public CollaboratorDAO(DBAdapter db) {
        this.db = db;
        this.db.connect();
    }

    public ArrayList<Collaborator> getAll() {
        ArrayList<Collaborator> collabs = new ArrayList<>();
        Cursor c = this.db.getInstace().rawQuery("SELECT * FROM COLLABORATORS", null);
        while (c.moveToNext()) {
            Collaborator collab = new Collaborator();
            collabs.add(
                    collab.setID(c.getInt(c.getColumnIndex("ID")))
                            .setName(c.getString(c.getColumnIndex("NAME")))
                            .setCPF(c.getString(c.getColumnIndex("CPF")))
            );
        }
        return collabs;
    }

    public Collaborator getCollab(String name, String CPF) {
        Collaborator collab = new Collaborator();
        return collab.setName(name).setCPF(CPF);
    }

    public boolean update(Collaborator collab) {
        ContentValues values = new ContentValues();
        values.put("NAME", collab.getName());
        values.put("CPF", collab.getCPF());
        this.db.getInstace().update("COLLABORATORS", values,
                "ID = ?", new String [] {collab.getID().toString()});
        return true;
    }

    public long insert(Collaborator collab) {
        ContentValues values = new ContentValues();
        values.put("CPF", collab.getCPF());
        values.put("NAME", collab.getName());
        return this.db.getInstace().insert("COLLABORATORS", null, values);
    }

    public boolean delete(Collaborator collab) {
        return true;
    }
}
