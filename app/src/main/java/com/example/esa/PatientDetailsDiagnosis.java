package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class PatientDetailsDiagnosis extends AppCompatActivity {

    int position;

    TextView tvID, tvMRN, tvname, tvDiagnosisDesc, tvProcedureName, tvSurgeryTime, tvArrivalTimeToSurgeon;
    TextView tvSurgeryDuration,tvSurgicalTeam, tvSubSpeciality,tvLastMeal,tvHighDependencyArea , txtInformedAnaesthetist, txtAnaesthetistOnCall;
    TextView textViewUsernameUser, tvTimeApproval;

    ImageView buttonBack;
    Button buttonClassifyCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_diagnosis);
        getSupportActionBar().hide();

        //declare variables
        tvID = findViewById(R.id.txtid);
        tvMRN = findViewById(R.id.txtMRN);
        tvname = findViewById(R.id.txtname);
        tvDiagnosisDesc = findViewById(R.id.txtDiagnosisDescription);
        tvProcedureName = findViewById(R.id.txtProcedureName);
        buttonBack = findViewById(R.id.buttonBack);
        buttonClassifyCategory = findViewById(R.id.buttonClassifyCategory);
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        textViewUsernameUser = findViewById(R.id.txtUsernameUser);
        textViewUsernameUser.setText(SharedPrefManager.getInstance(this).getUsername());
        tvSurgicalTeam = findViewById(R.id.txtSurgicalTeam);
        tvSubSpeciality = findViewById(R.id.txtSubSpeciality);
        tvLastMeal = findViewById(R.id.txtLastMeal);
        tvArrivalTimeToSurgeon = findViewById(R.id.txtArrivalTimeToSurgeon);
        tvTimeApproval = findViewById(R.id.txtTimeApproval);
        tvHighDependencyArea = findViewById(R.id.txtHighDependencyArea);
        txtInformedAnaesthetist = findViewById(R.id.txtInformedAnaesthetist);
        txtAnaesthetistOnCall = findViewById(R.id.txtAnaesthetistOnCall);

        //get patient data
        Intent intent =getIntent();
        position = intent.getExtras().getInt("position");


        //back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientDetailsDiagnosis.this,Dashboard.class);
                startActivity(i);
            }
        });


        //classify category button
        buttonClassifyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String patientID = tvMRN.getText().toString();
                String patientName = tvname.getText().toString();
                String id = tvID.getText().toString();
                 String surgeryTime = "";
                String arrival_time_to_surgeon = tvArrivalTimeToSurgeon.getText().toString();

                //get intent
                Intent i = new Intent(getApplicationContext(),Category.class);

                i.putExtra("id",id);
                i.putExtra("patientID",patientID);
                i.putExtra("patientName",patientName);
                i.putExtra("surgeryTime",surgeryTime);
                i.putExtra("arrival_time_to_surgeon",arrival_time_to_surgeon);

                insertData();

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

    private void insertData() {
        final String id = tvID.getText().toString();
        final String category_status = "Done";

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updateApprovePatient.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(PatientDetailsDiagnosis.this, response, Toast.LENGTH_SHORT).show();
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PatientDetailsDiagnosis.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("category_status",category_status);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PatientDetailsDiagnosis.this);
        requestQueue.add(request);

    }


}