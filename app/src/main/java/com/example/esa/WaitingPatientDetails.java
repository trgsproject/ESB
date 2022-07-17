package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WaitingPatientDetails extends AppCompatActivity {

    int position;

    ImageView buttonBack;

    TextView tvMRN, tvName, tvCategory, tvPhysiologicalStatus,tvAnaesthetistIncharge, tvTimeArrivalToSurgeon, tvNeeded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_patient_details);
        getSupportActionBar().hide();

        //declare variables
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        buttonBack =findViewById(R.id.buttonBack);
        tvMRN = findViewById(R.id.txtMRN);
        tvName = findViewById(R.id.txtName);
        tvCategory = findViewById(R.id.txtCategory);
        tvPhysiologicalStatus = findViewById(R.id.txtPhysiologicalStatus);
        tvAnaesthetistIncharge = findViewById(R.id.txtAnaesthetistIncharge);
        tvTimeArrivalToSurgeon = findViewById(R.id.txtTimeArrivalToSurgeon);
        tvNeeded = findViewById(R.id.txtNeeded);

        //get patient data
        Intent intent =getIntent();
        position = intent.getExtras().getInt("position");
        tvMRN.setText(WaitingPatientList.waitingPatientArrayList.get(position).getMRN());
        tvName.setText(WaitingPatientList.waitingPatientArrayList.get(position).getPatientName());
        tvCategory.setText(WaitingPatientList.waitingPatientArrayList.get(position).getCategory());
        tvPhysiologicalStatus.setText(WaitingPatientList.waitingPatientArrayList.get(position).getClinicalDescriptor());
        tvAnaesthetistIncharge.setText(WaitingPatientList.waitingPatientArrayList.get(position).getAnesthetistName());
        tvTimeArrivalToSurgeon.setText(WaitingPatientList.waitingPatientArrayList.get(position).getArrivalTimeToSurgeon());
        tvNeeded.setText(WaitingPatientList.waitingPatientArrayList.get(position).getNeeded_by_patient());


        //back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WaitingPatientDetails.this,WaitingPatientList.class);
                startActivity(i);
            }
        });

        //bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        return true;

                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(),Setting.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



    }

}