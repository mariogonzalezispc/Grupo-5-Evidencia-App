package com.cdp.ecodoctapp;

import android.os.Bundle;

import com.cdp.ecodoctapp.service.UserService;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.cdp.ecodoctapp.databinding.ActivityPerfilBinding;

public class Perfil extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityPerfilBinding binding;

    private UserService userService = new UserService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPerfilBinding.inflate(getLayoutInflater());


        final TextView textView = binding.EmailUsuario;
        textView.setText(userService.getLoggedUser());


        setContentView(binding.getRoot());




    }


}