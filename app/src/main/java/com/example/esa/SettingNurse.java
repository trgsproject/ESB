package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingNurse extends AppCompatActivity {

    ImageView logout,htcm,about,personalDetails,changePassword;
    float v;
    RelativeLayout Relative1,Relative2,Relative3,Relative4,Relative5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_nurse);
        getSupportActionBar().hide();

        //declare variables
        personalDetails = findViewById(R.id.personalDetails);
        logout = findViewById(R.id.logout);
        htcm = findViewById(R.id.htcm);
        about= findViewById(R.id.about);
        changePassword =findViewById(R.id.changePassword);

        //bottomNavigation
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.setting);

        //animation for relative layout
        Relative1 = findViewById(R.id.Relative1);
        Relative2 = findViewById(R.id.Relative2);
        Relative3 = findViewById(R.id.Relative3);
        Relative4 = findViewById(R.id.Relative4);
        Relative5 = findViewById(R.id.Relative5);

        Relative1.setTranslationX(800);
        Relative2.setTranslationX(800);
        Relative3.setTranslationX(800);
        Relative4.setTranslationX(800);
        Relative5.setTranslationX(800);

        Relative1.setAlpha(v);
        Relative2.setAlpha(v);
        Relative3.setAlpha(v);
        Relative4.setAlpha(v);
        Relative5.setAlpha(v);

        Relative1.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        Relative2.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        Relative3.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        Relative4.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        Relative5.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();

        //intent bottom
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),DashboardNurse.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.register:
                        startActivity(new Intent(getApplicationContext(),RegisterPatientNurse.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.setting:
                        return true;
                }
                return false;
            }
        });


        //intent personal details
        personalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingNurse.this,PersonalDetailsNurse.class);
                startActivity(i);
            }
        });

        //intent to change password
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingNurse.this,ChangePasswordForNurse.class);
                startActivity(i);
            }
        });

        //intent to HTCM web
        htcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUrl("https://hctm.ukm.my/");
            }
        });

        //intent about
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SettingNurse.this,AboutForNurse.class);
                startActivity(i);
            }
        });

        //intent logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(SettingNurse.this).logout();
                Intent i = new Intent(SettingNurse.this,Login.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

    //url method
    private void getUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));

    }
}