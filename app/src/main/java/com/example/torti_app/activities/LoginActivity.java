package com.example.torti_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.torti_app.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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
        this.agregarListeners();
    }

    private void inicializarElementos() {
        txtUsuario = findViewById(R.id.txt_usuario);
        txtPassword = findViewById(R.id.txt_password);
    }

    private void agregarListeners() {
        btnIniciaSesion.setOnClickListener(this);
    }

    private void iniciarSesion(String usuario, String password) {
        try {
            JSONObject params = new JSONObject();
            params.put("username", usuario);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        this.iniciarSesion(txtUsuario.getText().toString(), txtPassword.getText().toString());
    }
}
