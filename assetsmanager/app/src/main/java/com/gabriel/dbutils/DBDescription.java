package com.gabriel.dbutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * @author gabrielomelo
 * SQLite Database description, dont need JDBC or JPA
 */

public class DBDescription extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "AssetManager1.2.db"; // Rename this every new release
    static final int DATABASE_VERSION = 3; // The version returns to 0 when the major is incremented

    /*
     * Receive the context, or the activity class
     */
    public DBDescription(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
     * Create the necessary tables to run the application
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE ADMINS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME VARCHAR(40)," +
                "EMAIL VARCHAR(40)," +
                "CPF VARCHAR(11) NOT NULL," +
                "USERNAME VARCHAR(15)," +
                "PASSWORD TEXT NOT NULL" +
                ")"
        );

        db.execSQL("CREATE TABLE COLLABORATORS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NAME VARCHAR(40) NOT NULL," +
                "CPF VARCHAR(11) NOT NULL" +
                ")"
        );

        db.execSQL("CREATE TABLE ASSETS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "DESCRIPTION VARCHAR(30)," +
                "SERIAL_NUMBER VARCHAR(30) NOT NULL," +
                "HOST VARCHAR(30) NOT NULL" +
                ")"
        );

        db.execSQL("CREATE TABLE ASSIGNMENTS (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "COLLABORATOR_ID INTEGER NOT NULL," +
                "ASSET_ID INTEGER NOT NULL" +
                ")"
        );
    }

    /*
     * Drop the old tables and create newer ones
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.onCreate(db);
    }

}
