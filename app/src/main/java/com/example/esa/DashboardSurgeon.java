package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardSurgeon extends AppCompatActivity {

    private TextView textViewUsername;
    MaterialCardView cardCategory,cardAnesthetist,cardWaitingPatientList, cardPatientList,cardNurse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_surgeon);
        getSupportActionBar().hide();

        //declare variables
        textViewUsername= (TextView) findViewById(R.id.textViewUsername);
        cardCategory = (MaterialCardView) findViewById(R.id.cardCategory);
        cardPatientList = (MaterialCardView)findViewById(R.id.cardPatientList);
        cardWaitingPatientList= (MaterialCardView) findViewById(R.id.cardWaitingPatientList);
        cardAnesthetist = (MaterialCardView)findViewById(R.id.cardAnesthetist);
        cardNurse = (MaterialCardView)findViewById(R.id.cardNurse);
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
                        startActivity(new Intent(getApplicationContext(), RegisterPatientNurse.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(),SettingSurgeon.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        //Intent to category
        cardCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardSurgeon.this,ScheduleListSurgeon.class);
                startActivity(i);
            }
        });

        //Intent to patient list
        cardPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardSurgeon.this,PatientListSurgeon.class);
                startActivity(i);

            }
        });

        //Intent to waiting patient list
        cardWaitingPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardSurgeon.this,WaitingPatientListSurgeon.class);
                startActivity(i);

            }
        });

        //Intent to Anesthetist list
        cardAnesthetist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardSurgeon.this,AnesthetistListForSurgeon.class);
                startActivity(i);

            }
        });

        //Intent to Nurse
        cardNurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardSurgeon.this,NurseListForSurgeon.class);
                startActivity(i);

            }
        });



    }
}