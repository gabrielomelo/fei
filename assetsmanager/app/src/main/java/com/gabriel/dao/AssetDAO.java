package com.gabriel.dao;

import android.content.ContentValues;
import android.database.Cursor;

import com.gabriel.dbutils.DBAdapter;
import com.gabriel.entities.Asset;
import java.util.ArrayList;

public class AssetDAO {

    private DBAdapter db;

    public AssetDAO(DBAdapter db) {
        this.db = db;
        this.db.connect();
    }

    public Asset getAssetByID(DBAdapter db) {
        Asset asset = new Asset();

        return asset;
    }

    public ArrayList<Asset> getAll() {
        ArrayList<Asset> assets = new ArrayList<>();
        Cursor cursor = this.db.getInstace().rawQuery("select * from ASSETS", null);
        while(cursor.moveToNext()) {
            Asset asset = new Asset();
            asset.setID(cursor.getInt(0))
                    .setDescription(cursor.getString(1))
                    .setSerialNumber(cursor.getString(2))
                    .setHost(cursor.getString(3));
            assets.add(asset);
        }
        return assets;
    }

    public boolean update(Asset asset) {
        ContentValues values = new ContentValues();
        values.put("DESCRIPTION", asset.getDescription());
        values.put("SERIAL_NUMBER", asset.getSerialNumber());
        values.put("HOST", asset.getHost());
        this.db.getInstace().update("ASSETS", values,
                "ID = ?", new String [] {asset.getID().toString()});
        return true;
    }

    public Asset getAsset(String description, String serialNumber, String host) {
        Asset asset = new Asset();
        return asset.setDescription(description).setSerialNumber(serialNumber).setHost(host);
    }

    public long insert(Asset asset) {
        ContentValues values = new ContentValues();
        values.put("DESCRIPTION", asset.getDescription());
        values.put("SERIAL_NUMBER", asset.getSerialNumber());
        values.put("HOST", asset.getHost());
        return this.db.getInstace().insert("ASSETS", null, values);
    }
}
