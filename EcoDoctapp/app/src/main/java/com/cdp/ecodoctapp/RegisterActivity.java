package com.cdp.ecodoctapp;

import androidx.annotation.NonNull;
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

public class RegisterActivity extends AppCompatActivity {
    private EditText nombre;
    private EditText apellido;
    private EditText correoRegister;
    private EditText contrasenaRegister;
    private EditText contrasenaRegisterConfirmacion;

    private UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        correoRegister = findViewById(R.id.correoRegister);
        contrasenaRegister = findViewById(R.id.contrasenaRegister);
        contrasenaRegisterConfirmacion = findViewById(R.id.contrasenaRegisterConfirmacion);

    }

    public void onResume() {
        super.onResume();
        if (userService.isLogged()){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }

    public void registrarUsuario (View view) throws InterruptedException {


        String name = nombre.getText().toString();
        String lastname = apellido.getText().toString();
        String email = correoRegister.getText().toString();
        String password = contrasenaRegister.getText().toString();
        String password2 = contrasenaRegisterConfirmacion.getText().toString();
        Message message = userService.register(name,lastname,email,password,password2);

        if (message.isOK()){
            Toast.makeText(this, message.getMessage(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, message.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


}


