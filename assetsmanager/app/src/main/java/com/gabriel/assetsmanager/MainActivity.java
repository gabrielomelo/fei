package com.gabriel.assetsmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gabriel.dao.AdminDAO;
import com.gabriel.dbutils.DBAdapter;
import com.gabriel.entities.Admin;

public class MainActivity extends AppCompatActivity {

    private Admin currentUser;
    EditText txtUsername;
    EditText txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
    }

    public void login(View v) {
        String username, password;
        username = txtUsername.getText().toString();
        password = txtPassword.getText().toString();

        AdminDAO dao = new AdminDAO(new DBAdapter(this));
        Admin admin = dao.getByUsername(username);

        if (admin == null) {
            Toast.makeText(MainActivity.this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
            return;
        }

        if(admin.getLogin().equals(username) && admin.getPassword().equals(password)) {
            startActivity(new Intent(this, AssetsListActivity.class));
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.login_error), Toast.LENGTH_SHORT).show();
        }
    }

    public void openRegisterAdminActivity(View v) {
        startActivity(new Intent(this, RegisterAdminActivity.class));
    }

}
