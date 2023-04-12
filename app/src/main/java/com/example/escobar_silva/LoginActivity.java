package com.example.escobar_silva;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {


    private EditText etUsuario, etPassword;
    private SharedPreferences misPreferencias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        referenciar();

        misPreferencias = getSharedPreferences("tienda_app", MODE_PRIVATE);

        //VERIFICAR SI ESTÁ LOGUEADO
        if(misPreferencias.getBoolean("logueado", false)){
            Intent miIntent = new Intent(this, MainActivity.class);
            startActivity(miIntent);
            finish();
        }
    }
    private void referenciar(){
        etUsuario = findViewById(R.id.et_correo_login);
        etPassword = findViewById(R.id.et_contra_login);
    }
    public void clickIniciarSesion(View view){
        String PASS = "123456";
        String USER = "Carlos";

        String passUser = etPassword.getText().toString();
        String userUser = etUsuario.getText().toString();

        if(PASS.equals(passUser)&& USER.equals(userUser)){
            SharedPreferences.Editor miEditor = misPreferencias.edit();
            miEditor.putBoolean("logueado", true);
            miEditor.apply();

            Intent miIntent = new Intent(this, MainActivity.class);
            startActivity(miIntent);
            finish();
        }else {
            Toast.makeText(this, "CREDENCIALES INCORRECTAS", Toast.LENGTH_SHORT).show();
        }
    }
}