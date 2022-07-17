package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class DashboardNurse extends AppCompatActivity {

    private TextView textViewUsername;
    MaterialCardView cardPatientList, cardAnesthetist, cardSurgeon, cardWaitingPatientList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_nurse);
        getSupportActionBar().hide();

        //declare variables
        textViewUsername= (TextView) findViewById(R.id.textViewUsername);
        cardPatientList = (MaterialCardView) findViewById(R.id.cardPatientList);
        cardAnesthetist =(MaterialCardView)findViewById(R.id.cardAnesthetist);
        cardSurgeon =(MaterialCardView)findViewById(R.id.cardSurgeon);
        cardWaitingPatientList = (MaterialCardView)findViewById(R.id.cardWaitingPatientList);
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);

        //getUsername
        textViewUsername.setText( SharedPrefManager.getInstance(this).getUserRole() + ": " + SharedPrefManager.getInstance(this).getUsername());

        //bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        return true;

                    case R.id.register:
                        startActivity(new Intent(getApplicationContext(),RegisterPatientNurse.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(),SettingNurse.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        //Intent to Patient List
        cardPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardNurse.this, PatientList.class);
                startActivity(i);
            }
        });

        //intent to Anesthetist List
        cardAnesthetist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardNurse.this,AnesthetistListForNuse.class);
                startActivity(i);
            }
        });



        //Intent to surgeon list
        cardSurgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardNurse.this, SurgeonListForNurse.class);
                startActivity(i);
            }
        });

        //Intent to Waiting Patient List
        cardWaitingPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardNurse.this, WaitingPatientListNurse.class);
                startActivity(i);
            }
        });


    }
}