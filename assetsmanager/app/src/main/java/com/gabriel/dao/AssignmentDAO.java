package com.gabriel.dao;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;

import com.gabriel.dbutils.DBAdapter;
import com.gabriel.entities.Assignment;

import java.util.ArrayList;

public class AssignmentDAO {

    private DBAdapter db;

    public AssignmentDAO(DBAdapter db) {
        this.db = db;
        this.db.connect();
    }

    public long insert(Assignment collab) {
        ContentValues values = new ContentValues();
        values.put("ASSET_ID", collab.getAssetID());
        values.put("COLLABORATOR_ID", collab.getAssetID());
        return this.db.getInstace().insert("ASSIGNMENTS", null, values);
    }

    public ArrayList<Assignment> getAll() {
        ArrayList<Assignment> assigns = new ArrayList<>();
        Cursor c = this.db.getInstace().rawQuery("SELECT * FROM ASSIGNMENTS", null);
        while (c.moveToNext()) {
            Assignment assign = new Assignment();
            assigns.add(
                    assign.setID(c.getInt(c.getColumnIndex("ID")))
                    .setAssetID(c.getInt(c.getColumnIndex("ASSET_ID")))
                    .setCollaboratorID(c.getInt(c.getColumnIndex("COLLABORATOR_ID")))
            );
        }
        return assigns;
    }

    public Integer delete(Integer id) {
        return this.db.getInstace().delete("ASSIGNMENTS", "ID = ?", new String[]{id.toString()});
    }

}
