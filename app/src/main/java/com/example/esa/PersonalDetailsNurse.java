package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PersonalDetailsNurse extends AppCompatActivity {

    private EditText textUsername, textName, textEmail, textPhone ;
    float v;
    TextView textUpdatedAt;
    ImageView buttonBack;

    AutoCompleteTextView textTeam, textSubs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_details_nurse);
        getSupportActionBar().hide();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,Setting.class));
        }

        //get String
        String[] SubSpeciality = getResources().getStringArray(R.array.SubSpeciality);
        String[] Occupation = getResources().getStringArray(R.array.Level);

        //declare variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        textUsername = (EditText) findViewById(R.id.textUsername);
        textName = (EditText) findViewById(R.id.textName);
        textTeam = findViewById(R.id.textTeam);
        textSubs =  findViewById(R.id.textSubspeciality);
        textEmail = (EditText) findViewById(R.id.textEmail);
        textPhone = (EditText) findViewById(R.id.textPhone);
        textUpdatedAt = findViewById(R.id.editTextUpdatedAt);
        buttonBack =  findViewById(R.id.buttonBack);

        //get user data
        textUsername.setText(SharedPrefManager.getInstance(this).getUsername());
        textName.setText(SharedPrefManager.getInstance(this).getUserFullname());
        textTeam.setText(SharedPrefManager.getInstance(this).getUserRole());
        textSubs.setText(SharedPrefManager.getInstance(this).getUserSubspeciality());
        textEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textPhone.setText(SharedPrefManager.getInstance(this).getUserPhone());
        textUpdatedAt.setText(getUpdatedDateWithMonthName() + "  " + getTimeInWithAmAndPm() );


        //set subSpecialty
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,SubSpeciality);
        textSubs.setAdapter(adapter1);

        //set subSpecialty
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Occupation);
        textTeam.setAdapter(adapter2);


        //back button to previous intent
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PersonalDetailsNurse.this,SettingNurse.class);
                startActivity(i);
            }
        });


        //set item selected by bottom navgitaion
        bottomNavigationView.setSelectedItemId(R.id.setting);

        //intent bottom navigation
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), DashboardNurse.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.register:
                        startActivity(new Intent(getApplicationContext(), RegisterPatientNurse.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.setting:
                        return true;
                }
                return false;
            }
        });
    }

    //method to get created date
    private String getUpdatedDateWithMonthName(){
        return new SimpleDateFormat("dd-LLL-yyyy", Locale.getDefault()).format(new Date());
    }

    //method to get current time created
    private String getTimeInWithAmAndPm(){
        return new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());
    }

    public void btn_updateData (View view){

        final String name = textName.getText().toString().toUpperCase();
        final String username = textUsername.getText().toString();
        final String role = textTeam.getText().toString();
        final String subspecialty = textSubs.getText().toString();
        final String email = textEmail.getText().toString();
        final String phone = textPhone.getText().toString();
        final String updatedAt = "";

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updateUser.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        startActivity(new Intent(getApplicationContext(), SettingNurse.class));
                        Toast.makeText(PersonalDetailsNurse.this, response, Toast.LENGTH_SHORT).show();
                        //getIntent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        progressDialog.dismiss();
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PersonalDetailsNurse.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("username", username);
                params.put("name", name);
                params.put("role", role);
                params.put("subspecialty", subspecialty);
                params.put("email", email);
                params.put("phone", phone);
                params.put("updatedAt",updatedAt);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PersonalDetailsNurse.this);
        requestQueue.add(request);

    }



}