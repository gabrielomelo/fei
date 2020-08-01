package com.gabriel.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.gabriel.dbutils.DBAdapter;
import com.gabriel.entities.Admin;

public class AdminDAO {

    private DBAdapter db;

    public AdminDAO(DBAdapter db) {
        this.db = db;
        this.db.connect();
    }

    public void getAdminByName(String name) {
    }

    // Usado para criar usuários em branco, ou seja, que não possuem ID.
    public Admin createAdmin(String name, String email, String CPF, String login, String password) {
        return new Admin(name, email, CPF, login, password);
    }

    // Metodo deve apenas ser usado para usuários que já foram criados em algum momento
    public Admin createAdmin(Integer ID, String name, String email, String CPF, String login, String password) {
        Admin admin = new Admin(name, email, CPF, login, password);
        return (Admin) admin.setID(ID);
    }

    public Admin getByID(String ID) {
        Cursor c = this.db.getInstace().rawQuery("SELECT * FROM ADMINS WHERE ID = ?", new String[] {ID});
        Log.i("TAMANHO CURSOR", String.format("%d", c.getCount()));
        if(c.getCount() <= 0) return null;

        c.moveToFirst();
        return this.createAdmin(c.getInt(c.getColumnIndex("ID")), c.getString(c.getColumnIndex("NAME")),
                c.getString(c.getColumnIndex("EMAIL")), c.getString(c.getColumnIndex("CPF")),
                c.getString(c.getColumnIndex("USERNAME")), c.getString(c.getColumnIndex("PASSWORD")));
    }

    public Admin getByUsername(String username) {
        Cursor c = this.db.getInstace().rawQuery("SELECT * FROM ADMINS WHERE USERNAME = ?", new String[] {username});

        Log.i("TAMANHO CURSOR", String.format("%d", c.getCount()));

        if(c.getCount() <= 0) return null;

        c.moveToFirst();
        return this.createAdmin(c.getInt(c.getColumnIndex("ID")), c.getString(c.getColumnIndex("NAME")),
                c.getString(c.getColumnIndex("EMAIL")), c.getString(c.getColumnIndex("CPF")),
                c.getString(c.getColumnIndex("USERNAME")), c.getString(c.getColumnIndex("PASSWORD")));
    }

    public long update (Admin admin) {
        ContentValues values = new ContentValues();
        values.put("NAME", admin.getName());
        values.put("EMAIL", admin.getEmail());
        values.put("CPF", admin.getCPF());
        values.put("USERNAME", admin.getLogin());
        values.put("PASSWORD", admin.getPassword());
        return this.db.getInstace().insert("ADMIN", null, values);
    }

    public long insert(Admin admin) {
        ContentValues values = new ContentValues();
        values.put("NAME", admin.getName());
        values.put("EMAIL", admin.getEmail());
        values.put("CPF", admin.getCPF());
        values.put("USERNAME", admin.getLogin());
        values.put("PASSWORD", admin.getPassword());
        return this.db.getInstace().insert("ADMINS", null, values);
    }
}
