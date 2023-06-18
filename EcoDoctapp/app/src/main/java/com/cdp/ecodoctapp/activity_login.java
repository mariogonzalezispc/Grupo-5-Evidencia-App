package com.cdp.ecodoctapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cdp.ecodoctapp.entity.Message;
import com.cdp.ecodoctapp.service.UserService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.checkerframework.checker.nullness.qual.NonNull;

public class activity_login extends AppCompatActivity {

    private EditText correo;
    private EditText contrasena;

    private UserService userService = new UserService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);

    }


    public void onResume() {
        super.onResume();
        if (userService.isLogged()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }


    public void iniciarSesion (View view) throws InterruptedException {
        String email = correo.getText().toString();
        String password = contrasena.getText().toString();
        Message message = userService.login(email,password);
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_SHORT).show();
        Thread.sleep(1*1000);
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}