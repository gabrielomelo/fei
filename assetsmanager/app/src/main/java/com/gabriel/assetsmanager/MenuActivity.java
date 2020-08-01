package com.gabriel.assetsmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void btCadastroDispositivoOnClick(View view) {
        startActivity(new Intent(this, RegisterAssetActivity.class));
    }

    public void btCadastroColaboradorOnClick(View view) {
        startActivity(new Intent(this, RegisterCollabActivity.class));
    }

    public void btAlteraDispositivoOnClick(View view) {
        startActivity(new Intent(this, EditAssetActivity.class));
    }

}
