package com.gabriel.assetsmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gabriel.dao.CollaboratorDAO;
import com.gabriel.dbutils.DBAdapter;
import com.gabriel.utils.Mask;

public class RegisterCollabActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtCPF;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_collab);

        txtName = findViewById(R.id.txtName);
        txtCPF = findViewById(R.id.txtCPF);
        txtCPF.addTextChangedListener(Mask.mask(txtCPF, Mask.FORMAT_CPF));;
    }

    public void btCadastrarOnClick(View view) {
        String name = txtName.getText().toString();
        String cpf = Mask.unmask(txtCPF.getText().toString());
        if (name.equals("") || cpf.length() != 11) {
            Toast.makeText(this, "Failed to register a new collaborator", Toast.LENGTH_SHORT).show();
        }
        else {
            // CREATE GOES HERE
            CollaboratorDAO dao = new CollaboratorDAO(new DBAdapter(this));
            long ret = dao.insert(dao.getCollab(name, cpf));
            Toast.makeText(this, "Collaborator created. ID: " + ret, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void btCancelarOnClick(View view) {
        finish();
    }
}
