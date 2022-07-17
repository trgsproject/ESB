package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

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

import com.android.volley.AuthFailureError;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Dashboard extends AppCompatActivity {

    private TextView textViewUsername;
    MaterialCardView cardCategory,cardSurgeon,cardPatientList, cardWaitingPatientList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide();

        //declare variables
        textViewUsername= (TextView) findViewById(R.id.textViewUsername);
        cardCategory = (MaterialCardView) findViewById(R.id.cardCategory);
        cardPatientList = (MaterialCardView) findViewById(R.id.cardPatientList);
        cardWaitingPatientList = (MaterialCardView) findViewById(R.id.cardWaitingPatientList);
        cardSurgeon = (MaterialCardView) findViewById(R.id.cardSurgeon);
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

                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(),Setting.class));
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
                Intent i = new Intent(Dashboard.this,ScheduleList.class);
                startActivity(i);
            }
        });

        //Intent to Patient List
        cardPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this,PatientListAnes.class);
                startActivity(i);

            }
        });


        //Intent to waiting patient list
        cardWaitingPatientList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this,WaitingPatientList.class);
                startActivity(i);

            }
        });

        //Intent to category
        cardSurgeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Dashboard.this,SurgeonListForAnaesthetist.class);
                startActivity(i);

            }
        });

    }
}