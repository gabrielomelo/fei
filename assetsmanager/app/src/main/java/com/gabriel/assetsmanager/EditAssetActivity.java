package com.gabriel.assetsmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.gabriel.dao.AssetDAO;
import com.gabriel.dbutils.DBAdapter;
import com.gabriel.entities.Asset;

public class EditAssetActivity extends AppCompatActivity {

    EditText txtDescription;
    EditText txtSerialNumber;
    EditText txtHost;
    Asset asset;
    String TARGET_TAG = "TARGET_ASSET";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_asset);

        txtDescription = findViewById(R.id.txtDescription);
        txtSerialNumber = findViewById(R.id.txtSerialNumber);
        txtHost = findViewById(R.id.txtHost);
        loadTarget();
    }

    private void loadTarget() {
        this.asset = (Asset) getIntent().getSerializableExtra(TARGET_TAG);
        if(this.asset != null) {
            txtDescription.setText(this.asset.getDescription());
            txtSerialNumber.setText(this.asset.getSerialNumber());
            txtHost.setText(this.asset.getHost());
            return;
        }
        Toast.makeText(this, "Failed to load asset", Toast.LENGTH_SHORT).show();
    }

    public void btAlterarOnClick(View view) {
        String description = txtDescription.getText().toString();
        String serialNumber = txtSerialNumber.getText().toString();
        String host = txtHost.getText().toString();
        if (description.equals("") || serialNumber.equals("") || host.equals("")) {
            Toast.makeText(this, "Failed to edit asset", Toast.LENGTH_SHORT).show();
        }
        else {
            // EDIT GOES HERE
            this.asset.setDescription(description).setSerialNumber(serialNumber).setHost(host);
            AssetDAO dao = new AssetDAO(new DBAdapter(this));
            dao.update(asset);
            Toast.makeText(this, "Asset edited", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void btCancelarOnClick(View view) {
        finish();
    }
}
