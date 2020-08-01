package com.gabriel.assetsmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gabriel.dao.AssetDAO;
import com.gabriel.dbutils.DBAdapter;

public class RegisterAssetActivity extends AppCompatActivity {

    EditText txtDescription;
    EditText txtSerialNumber;
    EditText txtHost;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_asset);

        txtDescription = findViewById(R.id.txtDescription);
        txtSerialNumber = findViewById(R.id.txtSerialNumber);
        txtHost = findViewById(R.id.txtHost);
    }

    public void btCadastrarOnClick(View view) {
        String description = txtDescription.getText().toString();
        String serialNumber = txtSerialNumber.getText().toString();
        String host = txtHost.getText().toString();
        if (description.equals("") || serialNumber.equals("") || host.equals("")) {
            Toast.makeText(this, "Failed to create new asset", Toast.LENGTH_SHORT).show();
        }
        else {
            // CREATE GOES HERE valeu again vitex
            AssetDAO dao = new AssetDAO(new DBAdapter(this));
            long ret = dao.insert(dao.getAsset(description, serialNumber, host));
            Toast.makeText(this, "Asset created, ID: " + ret, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void btCancelarOnClick(View view) {
        finish();
    }
}
