package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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
import com.example.esa.api.ApiUtilities;
import com.example.esa.model.NotificationData;
import com.example.esa.model.PushNotification;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.esa.Constants.TOPIC;

public class PatientDetailsDiagnosisSurgeon extends AppCompatActivity {

    int position;

    TextView tvID, tvMRN, tvname, tvDiagnosisDesc, tvProcedureName, tvSurgeryTime, tvArrivalTime, txtAnaesthetistOnCall;
    TextView tvSurgeryDuration,tvSurgicalTeam, tvSubSpeciality,tvLastMeal, tvHighDependencyArea, txtInformedAnaesthetist;
    TextView textViewUsernameUser, tvTimeApproval,  textViewProcedureName2, textViewProcedureName3, tvProcedureName2, tvProcedureName3;

    ImageView buttonBack;
    Button buttonApproved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_diagnosis_surgeon);
        getSupportActionBar().hide();

        //get topic for notification
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        //declare variables
        tvID = findViewById(R.id.txtid);
        tvMRN = findViewById(R.id.txtMRN);
        tvname = findViewById(R.id.txtname);
        tvDiagnosisDesc = findViewById(R.id.txtDiagnosisDescription);
        tvProcedureName = findViewById(R.id.txtProcedureName);
        //tvSurgeryDuration = findViewById(R.id. txtSurgeryDuration);
        tvTimeApproval = findViewById(R.id.txtTimeApproval);
        buttonBack = findViewById(R.id.buttonBack);
        //tvSurgeryTime = findViewById(R.id.txtSurgeryTime);
        buttonApproved = findViewById(R.id.buttonApproved);
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        textViewUsernameUser = findViewById(R.id.txtUsernameUser);
        textViewUsernameUser.setText(SharedPrefManager.getInstance(this).getUsername());
        tvSurgicalTeam = findViewById(R.id.txtSurgicalTeam);
        tvSubSpeciality = findViewById(R.id.txtSubSpeciality);
        tvLastMeal = findViewById(R.id.txtLastMeal);
        tvArrivalTime = findViewById(R.id.txtArrivalTimeToSurgeon);
        tvHighDependencyArea = findViewById(R.id.txtHighDependencyArea);
        textViewProcedureName2 = findViewById(R.id.textViewProcedureName2);
        textViewProcedureName3 = findViewById(R.id.textViewProcedureName3);
        tvProcedureName2 = findViewById(R.id.txtProcedureName2);
        tvProcedureName3 = findViewById(R.id.txtProcedureName3);
        txtInformedAnaesthetist = findViewById(R.id.txtInformedAnaesthetist);
        txtAnaesthetistOnCall = findViewById(R.id.txtAnaesthetistOnCall);

        //get patient data
        Intent intent =getIntent();
        position = intent.getExtras().getInt("position");



        //check if procedure 2 and 3 exist
        String ProcedureCheck2 = tvProcedureName2.getText().toString().trim();

        if (ProcedureCheck2.equals("-")){
            tvProcedureName2.setVisibility(View.GONE);
            textViewProcedureName2.setVisibility(View.GONE);
        }



        String ProcedureCheck3 = tvProcedureName3.getText().toString().trim();

        if(ProcedureCheck3.equals("-")){
            textViewProcedureName3.setVisibility(View.GONE);
            tvProcedureName3.setVisibility(View.GONE);

        }


        //back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientDetailsDiagnosisSurgeon.this,DashboardSurgeon.class);
                startActivity(i);
            }
        });


        //classify category button
        buttonApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String check_highDependencyArea = tvHighDependencyArea.getText().toString().trim();

                if(check_highDependencyArea.equals("Available") | check_highDependencyArea.equals("Non-Available")){

                    updateData();
                }else{

                    Toast.makeText(PatientDetailsDiagnosisSurgeon.this, "Please re-check the patient details!", Toast.LENGTH_SHORT).show();
                }




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
                        startActivity(new Intent(getApplicationContext(),SettingSurgeon.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });




    }

    //update data into diagnosis_tb
    private void updateData() {
        final String id = tvID.getText().toString();
        final String approved_status = "Approved";
        final String time_approval = "";

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updateApprovePatientSurgeon.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //for notification
                        final String titletxt = "Patient have been approved!";
                        final String msgtxt = "Patient: " + tvname.getText().toString().trim();

                        PushNotification notification = new PushNotification(new NotificationData(titletxt, msgtxt), TOPIC);
                        Toast.makeText(PatientDetailsDiagnosisSurgeon.this, response, Toast.LENGTH_SHORT).show();
                        sendNotification(notification);

                        // InsertData();
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PatientDetailsDiagnosisSurgeon.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("approved_status", approved_status);
                params.put("time_approval",time_approval);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PatientDetailsDiagnosisSurgeon.this);
        requestQueue.add(request);

    }


    //insert data into score_tb
    private void InsertData() {

        final String mrn = tvMRN.getText().toString().trim().toUpperCase();
        final String patient_name = tvname.getText().toString().trim().toUpperCase();
        final String bookSurgeryTime = tvSurgeryTime.getText().toString().trim().toUpperCase();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "insertCategory.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equalsIgnoreCase("Data Inserted")) {

                            //for notification
                            final String titletxt = "Patient have been approved!";
                            final String msgtxt = "Patient: " + tvname.getText().toString().trim();

                            // Intent i = new Intent(PatientDetailsDiagnosisSurgeon.this, DashboardSurgeon.class);

                            //Toast.makeText(PatientDetailsDiagnosisSurgeon.this, "Patient have been approved!", Toast.LENGTH_SHORT).show();
                            PushNotification notification = new PushNotification(new NotificationData(titletxt, msgtxt), TOPIC);

                            sendNotification(notification);

                            // startActivity(i);
                            progressDialog.dismiss();


                        } else {
                            Toast.makeText(PatientDetailsDiagnosisSurgeon.this, response, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PatientDetailsDiagnosisSurgeon.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }

        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("mrn", mrn);
                params.put("patient_name", patient_name);
                params.put("bookSurgeryTime",bookSurgeryTime);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PatientDetailsDiagnosisSurgeon.this);
        requestQueue.add(request);
    }


    //send notification
    private void sendNotification(PushNotification notification) {
        ApiUtilities.getClient().sendNotification(notification).enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, retrofit2.Response<PushNotification> response) {
                if (response.isSuccessful()){
                    //Toast.makeText(RegisterPatientDetails.this,"success", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(PatientDetailsDiagnosisSurgeon.this,"error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(PatientDetailsDiagnosisSurgeon.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}