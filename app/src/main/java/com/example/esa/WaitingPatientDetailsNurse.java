package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;

public class WaitingPatientDetailsNurse extends AppCompatActivity {

    int position;

    ImageView buttonBack;

    TextView tvID, tvMRN, tvName, tvCategory, tvPhysiologicalStatus,tvAnaesthetistIncharge, tvTimeArrivalToSurgeon, tvNeeded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_patient_details_nurse);
        getSupportActionBar().hide();

        //declare variables
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        buttonBack =findViewById(R.id.buttonBack);
        tvID = findViewById(R.id.txtID);
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
        tvID.setText(WaitingPatientListNurse.waitingPatientArrayList.get(position).getId());
        tvMRN.setText(WaitingPatientListNurse.waitingPatientArrayList.get(position).getMRN());
        tvName.setText(WaitingPatientListNurse.waitingPatientArrayList.get(position).getPatientName());
        tvCategory.setText(WaitingPatientListNurse.waitingPatientArrayList.get(position).getCategory());
        tvPhysiologicalStatus.setText(WaitingPatientListNurse.waitingPatientArrayList.get(position).getClinicalDescriptor());
        tvAnaesthetistIncharge.setText(WaitingPatientListNurse.waitingPatientArrayList.get(position).getAnesthetistName());
        tvTimeArrivalToSurgeon.setText(WaitingPatientListNurse.waitingPatientArrayList.get(position).getArrivalTimeToSurgeon());
        tvNeeded.setText(WaitingPatientListNurse.waitingPatientArrayList.get(position).getNeeded_by_patient());


        //back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WaitingPatientDetailsNurse.this,WaitingPatientListNurse.class);
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
                        startActivity(new Intent(getApplicationContext(),SettingNurse.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



    }

    //update data into score_tb and intent to schedule register
    public void CreateSchedule(View view) {

        final String id = tvID.getText().toString();
        final String Status = "Done";

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updateScore.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(WaitingPatientDetailsNurse.this, response, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),RegisterSchedule.class);

                        String id = tvID.getText().toString();
                        String mrn = tvMRN.getText().toString();
                        String patientName = tvName.getText().toString();
                        String category = tvCategory.getText().toString();
                        String schedule_status = "Done";

                        i.putExtra("id",id);
                        i.putExtra("mrn",mrn);
                        i.putExtra("patientName",patientName);
                        i.putExtra("category",category);
                        i.putExtra("schedule_status",schedule_status);

                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(WaitingPatientDetailsNurse.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("Status", Status);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(WaitingPatientDetailsNurse.this);
        requestQueue.add(request);


    }
}