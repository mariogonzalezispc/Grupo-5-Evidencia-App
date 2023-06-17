package com.cdp.ecodoctapp.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.cdp.ecodoctapp.activity_login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationService {

    private FirebaseAuth mAuth;
    private Handler handler;
    private Runnable runnable;

    public AuthenticationService() {
        mAuth = FirebaseAuth.getInstance();
        handler = new Handler();
    }

    public void startAutoLoginCheck(final Context context) {
        // Crea un nuevo Runnable para la verificación
        runnable = new Runnable() {
            @Override
            public void run() {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {
                    // El usuario no está logueado, redirigir a LoginActivity
                    Intent intent = new Intent(context, activity_login.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                } else {
                    Log.d("USER_CONTEXT", user.getEmail());
                }

                // Ejecuta el Runnable nuevamente después de 3 segundos
                handler.postDelayed(this, 3000);
            }
        };

        // Inicia la verificación inicialmente
        handler.post(runnable);
    }

    public void stopAutoLoginCheck() {
        // Detiene la verificación al detener el Runnable
        handler.removeCallbacks(runnable);
    }
}