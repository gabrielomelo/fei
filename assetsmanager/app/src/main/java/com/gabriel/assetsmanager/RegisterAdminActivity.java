package com.gabriel.assetsmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gabriel.dao.AdminDAO;
import com.gabriel.dbutils.DBAdapter;
import com.gabriel.utils.Mask;

public class RegisterAdminActivity extends AppCompatActivity {

    EditText txtName;
    EditText txtEmail;
    EditText txtCPF;
    EditText txtUsername;
    EditText txtPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);


        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtCPF = findViewById(R.id.txtCPF);
        txtCPF.addTextChangedListener(Mask.mask(txtCPF, Mask.FORMAT_CPF));
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
    }


    public void btCadastrarOnClick(View view) {
        String name = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String cpf = Mask.unmask(txtCPF.getText().toString());
        String username = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        if (name.equals("") || email.equals("") || cpf.length() != 11 ||
                username.equals("") || password.equals("")) {
            Toast.makeText(this, "Failed to create new admin", Toast.LENGTH_SHORT).show();
        }
        else {
            // CREATE GOES HERE - valeu vitex
            AdminDAO dao = new AdminDAO(new DBAdapter(this));
            long ret = dao.insert(dao.createAdmin(name, email, cpf, username, password));
            Toast.makeText(this, "Admin created, ID: " + ret , Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void btCancelarOnClick(View view) {
        finish();
    }
}
