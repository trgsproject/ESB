package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SchedulePatientDetailsSurgeon extends AppCompatActivity {

    int position;

    ImageView buttonBack;

    TextView tvMRN, tvPatientName, tvOTRoom, tvSurgeryDate,tvCategory, tvSurgeryEndTime, tvSurgeryDuration;
    TextView tvPHU_ID_Bed, tvPHU_Start_Time, tvSurgeryStartTime, tvPACU_ID_Bed, tvPACU_Start_Time ,tvCallTime, tvArrivalTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_patient_details_surgeon);
        getSupportActionBar().hide();

        //declare variables
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        buttonBack =findViewById(R.id.buttonBack);
        tvMRN = findViewById(R.id.textMRN);
        tvPatientName = findViewById(R.id.textPatientName);
        tvOTRoom = findViewById(R.id.textOTRoom);
        tvCategory = findViewById(R.id.textCategory);
        tvSurgeryDate = findViewById(R.id.textSurgeryDate);
        tvSurgeryStartTime = findViewById(R.id.textTimeSurgeryStartTime);
        tvSurgeryEndTime = findViewById(R.id.textSurgeryEndTime);
        tvSurgeryDuration = findViewById(R.id.textSurgeryDuration);
        tvPHU_ID_Bed = findViewById(R.id.textPHUIDBed);
        tvPHU_Start_Time = findViewById(R.id.textPHUStartTime);
        tvPACU_ID_Bed = findViewById(R.id.textPACUIDBed);
        tvPACU_Start_Time = findViewById(R.id.textPACUStartTime);
        tvCallTime = findViewById(R.id.textCallTime);
        tvArrivalTime = findViewById(R.id.textArrivalTime);


        //set patient data
        Intent intent =getIntent();
        position = intent.getExtras().getInt("position");
        tvMRN.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getMRN());
        tvPatientName.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getPatientName());
        tvOTRoom.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getOT());
        tvCategory.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getCategory());
        tvSurgeryDate.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getSurgeryDate());
        tvSurgeryStartTime.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getSurgeryStartTime());
        tvSurgeryEndTime.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getSurgeryEndTime());
        tvPHU_ID_Bed.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getPHUidBed());
        tvPHU_Start_Time.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getPHUStartTime());
        tvPACU_ID_Bed.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getPACUidBed());
        tvPACU_Start_Time.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getPACUStartTime());
        tvCallTime.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getCallTime());
        tvArrivalTime.setText(ScheduleListSurgeon.scheduleArrayList.get(position).getArrivalTimeToOT());


        //get surgery time
        String Time1 = tvSurgeryStartTime.getText().toString().trim();
        String Time2 = tvSurgeryEndTime.getText().toString().trim();

        // date format
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(Time1);
            d2 = format.parse(Time2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        double diff= d2.getTime() - d1.getTime();

        long diffMinutes = (int) (diff / (60 * 1000) % 60);
        long diffHours = (int) (diff / (60 * 60 * 1000) % 24);


         if (diffHours<10 && diffMinutes<10){
            tvSurgeryDuration.setText("0"+ diffHours + "0" + diffMinutes);
        }
        else if (diffMinutes<10){
            tvSurgeryDuration.setText("0"+ diffHours + "0" + diffMinutes);
        }
        else {
            tvSurgeryDuration.setText("0" + diffHours + "" + diffMinutes);
        }


        //back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SchedulePatientDetailsSurgeon.this,ScheduleListSurgeon.class);
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


                    case R.id.register:
                        startActivity(new Intent(getApplicationContext(),RegisterPatientNurse.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(),SettingSurgeon.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



    }
}