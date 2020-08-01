package com.gabriel.dbutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBAdapter {

    private SQLiteDatabase db = null;
    private DBDescription helper;

    public DBAdapter(Context context) {
        this.helper = new DBDescription(context);
    }

    public void connect() {
        if(this.db != null) return;
        this.db = this.helper.getWritableDatabase();
    }

    public SQLiteDatabase getInstace() {
        return this.db;
    }

}
