package com.gabriel.assetsmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.gabriel.dao.AssetDAO;
import com.gabriel.dao.AssignmentDAO;
import com.gabriel.dao.CollaboratorDAO;
import com.gabriel.dbutils.DBAdapter;
import com.gabriel.entities.Asset;
import com.gabriel.entities.Assignment;
import com.gabriel.entities.Collaborator;

import java.util.ArrayList;

public class RegisterAssignmentActivity extends AppCompatActivity {

    ArrayList<Collaborator> collaborators;
    ArrayList<Asset> assets;
    AutoCompleteTextView CPFeditText;
    AutoCompleteTextView AssetEditText;
    Asset asset;
    Collaborator collab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_assignment);
        loadData();
        loadAutoComplete();

    }

    public void loadData() {
        AssetDAO assetDAO = new AssetDAO(new DBAdapter(this));
        CollaboratorDAO collabDAO = new CollaboratorDAO(new DBAdapter(this));
        this.assets = assetDAO.getAll();
        this.collaborators = collabDAO.getAll();
    }

    public void loadAutoComplete() {
        CPFeditText = findViewById(R.id.cpfAutoComplete);
        AssetEditText = findViewById(R.id.serialAutoComplete);
        CPFeditText.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.collaborators));
        CPFeditText.setThreshold(1);
        CPFeditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                collab = (Collaborator) parent.getAdapter().getItem(position);
            }
        });
        AssetEditText.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, this.assets));
        AssetEditText.setThreshold(1);
        AssetEditText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                asset = (Asset) parent.getAdapter().getItem(position);
            }
        });
    }

    public void onClickSubmitButton(View v) {
        if(asset == null || collab == null) return;
        AssignmentDAO dao = new AssignmentDAO(new DBAdapter(this));
        Assignment assignment = new Assignment().setAssetID(this.asset.getID()).setCollaboratorID(this.collab.getID());
        dao.insert(assignment);
        Toast.makeText(this, "Assignment SN: " + this.asset.getSerialNumber() + "CPF: " + this.collab.getCPF(), Toast.LENGTH_LONG).show();
        finish();
    }

    public void onClickCancelButton(View v) {
        finish();
    }
}
