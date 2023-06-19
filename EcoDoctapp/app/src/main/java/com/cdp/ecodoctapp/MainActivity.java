package com.cdp.ecodoctapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

import com.cdp.ecodoctapp.entity.Message;
import com.cdp.ecodoctapp.service.UserService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.cdp.ecodoctapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    private  Button btnLogin;
    private Button btnRegister;

    private activity_login activityLogin;

    private UserService userService = new UserService();

    private  NavigationView navigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        btnLogin = findViewById(R.id.button);
        btnRegister = findViewById(R.id.button2);

        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.activity_reproductor_video)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


   }

   public void onResume() {
       super.onResume();

       if(userService.isLogged()) {
           hideItemLogged();
       }else {
            hideItemLogout();
       }
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void returnRegister(View view) {

        Intent registerIntent = new Intent(this, RegisterActivity.class);
        startActivity(registerIntent);
    }
    public void returnLogin(View view) {

        Intent loginIntent = new Intent(this, activity_login.class);
        startActivity(loginIntent);
    }

    public void logout (){

      Message message =  userService.logout();
        Toast.makeText(this, message.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {// EITHER CALL THE METHOD HERE OR DO THE FUNCTION DIRECTLY
        Log.d("Logout", "Logout");
         logout();
        recreate();
        }
        return super.onOptionsItemSelected(item);
    }

    private void hideItemLogged() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.activity_login).setVisible(false);
        btnLogin.setVisibility(View.GONE);
        btnRegister.setVisibility(View.GONE);
    }
    private void hideItemLogout() {
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.logout).setVisible(false);
        menu.findItem(R.id.Perfil).setVisible(false);
    }


}