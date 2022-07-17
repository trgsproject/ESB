package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PatientViewDetails extends AppCompatActivity {

    int position;

    TextView tvuserid, tvname, tvusername, tvage, tvgender, tvnation, tvic;
    TextView tvID, tvNurseID, tvTimeArrival;

    FloatingActionButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_details);
        getSupportActionBar().hide();

        //Initializing Varaiables
        tvID = findViewById(R.id.txtid);
        tvNurseID = findViewById(R.id.txtNurseid);
        tvuserid = findViewById(R.id.txtuserid);
        tvname = findViewById(R.id.txtname);
        tvusername = findViewById(R.id.txtusername);
        tvage = findViewById(R.id.txtage);
        tvgender = findViewById(R.id.txtgender);
        tvic = findViewById(R.id.txtic);
        tvnation = findViewById(R.id.txtnation);
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        buttonBack = findViewById(R.id.buttonBack);
        tvTimeArrival = findViewById(R.id.txtTimeArrival);

        //get patient data
        Intent intent =getIntent();
        position = intent.getExtras().getInt("position");
        tvuserid.setText(PatientListAnes.patientArrayList.get(position).getMRN());
        tvname.setText(PatientListAnes.patientArrayList.get(position).getName());
        //tvusername.setText(PatientListAnes.patientArrayList.get(position).getShortname());
        tvage.setText(PatientListAnes.patientArrayList.get(position).getAge());
        tvgender.setText(PatientListAnes.patientArrayList.get(position).getGender());
        tvnation.setText(PatientListAnes.patientArrayList.get(position).getNationality());
        //tvic.setText(PatientListAnes.patientArrayList.get(position).getIc());
        //tvNurseID.setText(PatientListAnes.patientArrayList.get(position).getUser_id());
        //tvTimeArrival.setText(PatientListAnes.patientArrayList.get(position).getArrival_time());

        //back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientViewDetails.this,PatientListAnes.class);
                startActivity(i);
            }
        });



        //set selected item on bottomNavigationView
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        return true;

                    case R.id.register:
                        startActivity(new Intent(getApplicationContext(),RegisterPatient.class));
                        overridePendingTransition(0,0);
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