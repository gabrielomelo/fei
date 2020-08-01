package com.gabriel.assetsmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.gabriel.dao.CollaboratorDAO;
import com.gabriel.dbutils.DBAdapter;
import com.gabriel.entities.Collaborator;


public class EditCollaboratorActivity extends AppCompatActivity {

    String TARGET_TAG = "TARGET_COLLAB";
    Collaborator collab;
    EditText txtName, txtCPF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_collaborator);
        txtName = findViewById(R.id.collab_name_text);
        txtCPF = findViewById(R.id.collab_cpf_text);
        loadTarget();
    }

    private void loadTarget() {
        this.collab = (Collaborator) getIntent().getSerializableExtra(TARGET_TAG);
        if(this.collab != null) {
            txtName.setText(this.collab.getName());
            txtCPF.setText(this.collab.getCPF());
            return;
        }
        Toast.makeText(this, "Failed to load collaborator", Toast.LENGTH_SHORT).show();
    }

    public void btAlterarOnClick(View view) {
        String name = txtName.getText().toString();
        String cpf = txtCPF.getText().toString();
        if (name.equals("") || cpf.equals("")) {
            Toast.makeText(this, "Failed to edit collaborator", Toast.LENGTH_SHORT).show();
        }
        else {
            // EDIT GOES HERE
            this.collab.setCPF(cpf).setName(name);
            CollaboratorDAO dao = new CollaboratorDAO(new DBAdapter(this));
            dao.update(collab);
            Toast.makeText(this, "Collaborator edited", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void btCancelarOnClick(View view) {
        finish();
    }

}
