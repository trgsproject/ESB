package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class About extends AppCompatActivity {

    ImageView buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();

        //declare variables
        buttonBack =  findViewById(R.id.buttonBack);
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);

        //bottomnavigation
        bottomNavigationView.setSelectedItemId(R.id.setting);

        //back button to previous intent
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(About.this,Setting.class);
                startActivity(i);
            }
        });


        //intent bottom
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.register:
                        startActivity(new Intent(getApplicationContext(),RegisterPatient.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.setting:
                        return true;
                }
                return false;
            }
        });

    }
}