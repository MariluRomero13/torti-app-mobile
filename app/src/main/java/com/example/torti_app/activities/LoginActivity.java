package com.example.torti_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.torti_app.Models.User;
import com.example.torti_app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText txtUsuario, txtPassword;
    private Button btnIniciaSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.boot();
        this.btnIniciaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User.login(getApplicationContext(), LoginActivity.this,
                        txtUsuario.getText().toString(), txtPassword.getText().toString());
            }
        });
    }

    private void boot() {
        this.inicializarElementos();
    }

    private void inicializarElementos() {
        txtUsuario = findViewById(R.id.txt_usuario);
        txtPassword = findViewById(R.id.txt_password);
        this.btnIniciaSesion = findViewById(R.id.btn_iniciarSesion);
    }
}
