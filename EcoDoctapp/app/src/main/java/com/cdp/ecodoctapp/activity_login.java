package com.cdp.ecodoctapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cdp.ecodoctapp.entity.Message;
import com.cdp.ecodoctapp.service.UserService;

public class activity_login extends AppCompatActivity {
    Button btn_register;
    EditText email, password;

    UserService userService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userService = new UserService();

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        btn_register = findViewById(R.id.btn_login);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = email.getText().toString().trim();
                String passUser = password.getText().toString().trim();

                Message message = userService.login(emailUser,passUser);
                Toast.makeText(activity_login.this, message.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}