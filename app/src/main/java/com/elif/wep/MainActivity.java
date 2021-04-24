package com.elif.wep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton statPage;
    private ImageButton planPage;
    private ImageButton homePage;
    private ImageButton taskPage;
    private ImageButton profilePage;

    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerBtn = (Button) findViewById(R.id.mainRegister);
        registerBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openRegisterPage();

            }
        });
        statPage = (ImageButton) findViewById(R.id.statPage);
        statPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openStatsPage();

            }
        });
        planPage = (ImageButton) findViewById(R.id.planPage);
        planPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openPlanPage();

            }
        });
        homePage = (ImageButton) findViewById(R.id.homePage);
        homePage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openHomePage();

            }
        });
        taskPage = (ImageButton) findViewById(R.id.taskPage);
        taskPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openTaskPage();

            }
        });
        profilePage = (ImageButton) findViewById(R.id.profilePage);
        profilePage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                openProfilePage();

            }
        });
    }

    public void openStatsPage() {
        Intent intent = new Intent(this, Statistics.class);
        startActivity(intent);
    }

    private void openPlanPage() {
        Intent intent = new Intent(this, Schedule.class);
        startActivity(intent);
    }

    private void openHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void openTaskPage() {
        Intent intent = new Intent(this, TaskList.class);
        startActivity(intent);
    }

    private void openProfilePage() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }

    private void openRegisterPage() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}