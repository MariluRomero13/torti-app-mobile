package com.example.torti_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    }

    private void boot() {
        this.inicializarElementos();
        this.iniciarSesion();
    }

    private void inicializarElementos() {
        txtUsuario = findViewById(R.id.txt_usuario);
        txtPassword = findViewById(R.id.txt_password);
        btnIniciaSesion= findViewById(R.id.btn_iniciarSesion);
    }
    
    private void iniciarSesion() {
        btnIniciaSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "Hola", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
