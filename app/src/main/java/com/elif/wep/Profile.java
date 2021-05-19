package com.elif.wep;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Profile extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public NavigationView navigation;

    FirebaseAuth fAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fAuth = FirebaseAuth.getInstance();



        // change menu if user logged-in

        if (fAuth.getCurrentUser() != null) {
            setContentView(R.layout.activity_profile);
            drawerNav();
            navigationMenu();
            navigation.getMenu().clear();
            navigation.inflateMenu(R.menu.menu_user); //inflate new items.

            Button updateBtn = findViewById(R.id.updateBtn);
            updateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getUserAuth();
                }
            });
        }

        if(fAuth.getCurrentUser() == null) {
            setContentView(R.layout.activity_profile_logout);
            drawerNav();
            navigationMenu();
            navigation.getMenu().clear();
            navigation.inflateMenu(R.menu.menu); //inflate new items.

        }


        navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.nav_login:
                        startActivity(new Intent(getApplicationContext()
                                , Login.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_register:
                            startActivity(new Intent(getApplicationContext()
                                    , Register.class));
                            overridePendingTransition(0, 0);
                            return true;
                    case R.id.nav_account:
                        startActivity(new Intent(getApplicationContext()
                                , Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_logout:
                        logout();
                        return true;
                    default:
                        return true;
                }

            }
        });




    }

    public void logout() {

        AlertDialog alertDialog = new AlertDialog.Builder(Profile.this).create();
        alertDialog.setTitle("WEP");
        alertDialog.setMessage("Do you want to sign out?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        fAuth.signOut();
                        Toast.makeText(Profile.this, "You logged out.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);

                    }
                });
        alertDialog.show();

    }


    // toggle button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void drawerNav() {

        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigation = (NavigationView)  findViewById(R.id.navigation_menu);
        navigation.bringToFront();



        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void navigationMenu() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setSelectedItemId(R.id.profile_item);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private MenuItem item;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                this.item = item;
                switch (item.getItemId()) {
                    case R.id.home_item:
                        startActivity(new Intent(getApplicationContext()
                                , MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.task_item:
                        startActivity(new Intent(getApplicationContext()
                                , TaskList.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.plan_item:
                        startActivity(new Intent(getApplicationContext()
                                , Schedule.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.stat_item:
                        startActivity(new Intent(getApplicationContext()
                                , Statistics.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile_item:
                        return true;

                }
                return false;
            }
        });
    }

    private void getUserAuth() {

        // --- dialog -- //

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Profile.this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        View view = layoutInflater.inflate(R.layout.edit_profile_user_login, null);
        alertDialog.setView(view);

        AlertDialog userDialog = alertDialog.create();

        final EditText email = view.findViewById(R.id.userEmail);
        final EditText password = view.findViewById(R.id.userPassword);

        // --- dialog -- //


        EditText editUserEmail = findViewById(R.id.editUserMail);
        String email_change = editUserEmail.getText().toString();


        Button login = view.findViewById(R.id.loginAgain);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_m = email.getText().toString().trim();
                String password_m = password.getText().toString().trim();

                if (TextUtils.isEmpty(email_change)) {
                    Toast.makeText(Profile.this, "Please, enter password.", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(email_m) || TextUtils.isEmpty(password_m)) {
                    Toast.makeText(Profile.this, "Enter every details", Toast.LENGTH_SHORT).show();
                } else {
                    fAuth.signInWithEmailAndPassword(email_m, password_m)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = fAuth.getCurrentUser();
                                        user.updateEmail(email_change)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(Profile.this, "Successful.", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });                                    } else {
                                        Toast.makeText(Profile.this, "Please enter correct details", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

                userDialog.dismiss();
            }

        });

        userDialog.show();


    }


}