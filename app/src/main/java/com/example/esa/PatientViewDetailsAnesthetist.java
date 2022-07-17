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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class PatientViewDetailsAnesthetist extends AppCompatActivity {

    int position;

    TextView tvCategoryStatus, tvID, tvMRN, tvName, tvAge,tvGender, tvNationality, tvRace, tvCountry;
    TextView tvWard, tvSurgeonName, tvDiagnosisDescription, tvProcedure1;
    TextView tvProcedure2, tvProcedure3, tvSubSpecialty, tvLastMeal;
    TextView tvInformAnesthetist, tvAnesthetistName, tvHighDependencyArea ;
    TextView tvArrivalTime, tvApprovedTime, tvStatus;

    TextView tvViewRace, tvViewCountry, tvViewSurgeonName, tvViewDiagnosisDescription, tvViewProcedure1;
    TextView tvViewProcedure2, tvViewProcedure3, tvViewSubSpecialty, tvViewLastMeal, tvViewInformAnesthetist;
    TextView tvViewAnesthetistName, tvViewHighDependencyArea, tvViewArrivalTime, tvViewApprovedTime, tvViewStatus;

    TextView buttonViewMoreDetails, buttonViewLessDetails;

    ImageView buttonBack;

    Button buttonClassifyCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_details_anesthetist);
        getSupportActionBar().hide();

        //Initializing Variables
        tvID = findViewById(R.id.txtID);
        tvMRN = findViewById(R.id.txtMRN);
        tvName = findViewById(R.id.txtName);
        tvAge = findViewById(R.id.txtage);
        tvGender = findViewById(R.id.txtgender);
        tvNationality = findViewById(R.id.txtNationality);
        tvRace = findViewById(R.id.txtRace);
        tvCountry = findViewById(R.id.txtCountry);
        tvWard = findViewById(R.id.txtWard);
        tvSurgeonName = findViewById(R.id.txtSurgicalTeam);
        tvDiagnosisDescription = findViewById(R.id.txtDiagnosisDescription);
        tvProcedure1 = findViewById(R.id.txtProcedureName);
        tvProcedure2 = findViewById(R.id.txtProcedureName2);
        tvProcedure3 = findViewById(R.id.txtProcedureName3);
        tvSubSpecialty = findViewById(R.id.txtSubSpeciality);
        tvLastMeal = findViewById(R.id.txtLastMeal);
        tvInformAnesthetist = findViewById(R.id.txtInformedAnaesthetist);
        tvAnesthetistName = findViewById(R.id.txtAnaesthetistName);
        tvHighDependencyArea = findViewById(R.id.txtHighDependencyArea);
        tvArrivalTime = findViewById(R.id.txtTimeArrival);
        tvApprovedTime = findViewById(R.id.txtApprovalTime);
        tvStatus = findViewById(R.id.textApprovedStatus);
        tvCategoryStatus = findViewById(R.id.txtCategoryStatus);
        buttonClassifyCategory = findViewById(R.id.buttonClassifyCategory);

        //for View More Info
        tvViewRace = findViewById(R.id.textViewRace);
        tvViewCountry = findViewById(R.id.textViewCountry);
        tvViewSurgeonName = findViewById(R.id.textViewSurgicalTeam);
        tvViewDiagnosisDescription = findViewById(R.id.textViewDiagnosisDesc);
        tvViewProcedure1 = findViewById(R.id.textViewProcedureName);
        tvViewProcedure2 = findViewById(R.id.textViewProcedureName2);
        tvViewProcedure3 = findViewById(R.id.textViewProcedureName3);
        tvViewSubSpecialty = findViewById(R.id.textViewSubSpeciality);
        tvViewLastMeal = findViewById(R.id.textViewLastMeal);
        tvViewInformAnesthetist = findViewById(R.id.textViewInformedAnaesthetist);
        tvViewAnesthetistName = findViewById(R.id.textViewAnaesthetistName);
        tvViewHighDependencyArea = findViewById(R.id.textViewHighDependencyArea);
        tvViewArrivalTime = findViewById(R.id.textViewTimeArrival);
        tvViewApprovedTime = findViewById(R.id.textViewApprovedTime);
        tvViewStatus = findViewById(R.id.textViewApprovedStatus);
        buttonViewMoreDetails = findViewById(R.id.buttonViewMoreDetails);
        buttonViewLessDetails = findViewById(R.id.buttonViewLessDetails);
        buttonBack = findViewById(R.id.buttonBack);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //get patient data
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        tvID.setText(PatientListAnes.patientArrayList.get(position).getId());
        tvMRN.setText(PatientListAnes.patientArrayList.get(position).getMRN());
        tvName.setText(PatientListAnes.patientArrayList.get(position).getName());
        tvAge.setText(PatientListAnes.patientArrayList.get(position).getAge());
        tvGender.setText(PatientListAnes.patientArrayList.get(position).getGender());
        tvNationality.setText(PatientListAnes.patientArrayList.get(position).getNationality());
        tvRace.setText(PatientListAnes.patientArrayList.get(position).getRace());
        tvCountry.setText(PatientListAnes.patientArrayList.get(position).getCountry());
        tvWard.setText(PatientListAnes.patientArrayList.get(position).getWard());
        tvSurgeonName.setText(PatientListAnes.patientArrayList.get(position).getSurgeonName());
        tvDiagnosisDescription.setText(PatientListAnes.patientArrayList.get(position).getDiagnosisDescription());
        tvProcedure1.setText(PatientListAnes.patientArrayList.get(position).getProcedureName1());
        tvProcedure2.setText(PatientListAnes.patientArrayList.get(position).getProcedureName2());
        tvProcedure3.setText(PatientListAnes.patientArrayList.get(position).getProcedureName3());
        tvSubSpecialty.setText(PatientListAnes.patientArrayList.get(position).getSubSpecialty());
        tvLastMeal.setText(PatientListAnes.patientArrayList.get(position).getLastMeal());
        tvInformAnesthetist.setText(PatientListAnes.patientArrayList.get(position).getInformAnesthetist());
        tvAnesthetistName.setText(PatientListAnes.patientArrayList.get(position).getAnesthetistName());
        tvHighDependencyArea.setText(PatientListAnes.patientArrayList.get(position).getHighDependencyArea());
        tvArrivalTime.setText(PatientListAnes.patientArrayList.get(position).getArrivalTime());
        tvApprovedTime.setText(PatientListAnes.patientArrayList.get(position).getApprovedTime());
        tvStatus.setText(PatientListAnes.patientArrayList.get(position).getStatus());
        tvCategoryStatus.setText(PatientListAnes.patientArrayList.get(position).getCategory_status());

        //check if nation malaysian or non malaysian
        String nation = tvNationality.getText().toString().trim();

        if (nation.equals("MALAYSIAN")){
            tvCountry.setVisibility(View.GONE);
            tvViewCountry.setVisibility(View.GONE);

        } else if (nation.equals("NON-MALAYSIAN")){
            tvRace.setVisibility(View.GONE);
            tvViewRace.setVisibility(View.GONE);

        }


        //if procedure null
        String procedure2 = tvProcedure2.getText().toString().trim();

        if (procedure2.equals("-")){
            tvViewProcedure2.setVisibility(View.GONE);
            tvProcedure2.setVisibility(View.GONE);

        }

        //if procedure null
        String procedure3 = tvProcedure3.getText().toString().trim();

        if (procedure3.equals("-")){
            tvViewProcedure3.setVisibility(View.GONE);
            tvProcedure3.setVisibility(View.GONE);

        }



        //back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientViewDetailsAnesthetist.this, PatientListAnes.class);
                startActivity(i);
            }
        });


        //classify category button
        buttonClassifyCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = tvID.getText().toString();
                String MRN = tvMRN.getText().toString();
                String PatientName = tvName.getText().toString();

                //get intent
                Intent i = new Intent(getApplicationContext(),Category.class);

                i.putExtra("id",id);
                i.putExtra("MRN",MRN);
                i.putExtra("PatientName",PatientName);

                updateData();

                startActivity(i);
            }
        });




        //set selected item on bottomNavigationView
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        return true;

                    case R.id.register:
                        startActivity(new Intent(getApplicationContext(), RegisterPatientNurse.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(), Setting.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

    }


    //update category status method
    private void updateData() {

        final String id = tvID.getText().toString().toUpperCase();
        final String category_status = "Done";

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updateCategoryStatus.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //startActivity(new Intent(getApplicationContext(), SettingNurse.class));
                        Toast.makeText(PatientViewDetailsAnesthetist.this, response, Toast.LENGTH_SHORT).show();
                        getIntent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        progressDialog.dismiss();
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PatientViewDetailsAnesthetist.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("category_status", category_status);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PatientViewDetailsAnesthetist.this);
        requestQueue.add(request);

}


    //view more button
    public void ViewMoreDetails(View view) {

        tvSurgeonName.setVisibility(View.VISIBLE);
        tvDiagnosisDescription.setVisibility(View.VISIBLE);
        tvProcedure1.setVisibility(View.VISIBLE);
        tvProcedure2.setVisibility(View.VISIBLE);
        tvProcedure3.setVisibility(View.VISIBLE);
        tvSubSpecialty.setVisibility(View.VISIBLE);
        tvLastMeal.setVisibility(View.VISIBLE);
        tvInformAnesthetist.setVisibility(View.VISIBLE);
        tvAnesthetistName.setVisibility(View.VISIBLE);
        tvHighDependencyArea.setVisibility(View.VISIBLE);
        tvArrivalTime.setVisibility(View.VISIBLE);
        tvApprovedTime.setVisibility(View.VISIBLE);
        tvStatus.setVisibility(View.VISIBLE);

        tvViewSurgeonName.setVisibility(View.VISIBLE);
        tvViewDiagnosisDescription.setVisibility(View.VISIBLE);
        tvViewProcedure1.setVisibility(View.VISIBLE);
        tvViewProcedure2.setVisibility(View.VISIBLE);
        tvViewProcedure3.setVisibility(View.VISIBLE);
        tvViewSubSpecialty.setVisibility(View.VISIBLE);
        tvViewLastMeal.setVisibility(View.VISIBLE);
        tvViewInformAnesthetist.setVisibility(View.VISIBLE);
        tvViewAnesthetistName.setVisibility(View.VISIBLE);
        tvViewHighDependencyArea.setVisibility(View.VISIBLE);
        tvViewArrivalTime.setVisibility(View.VISIBLE);
        tvViewApprovedTime.setVisibility(View.VISIBLE);
        tvViewStatus.setVisibility(View.VISIBLE);

        buttonViewMoreDetails.setVisibility(View.GONE);
        buttonViewLessDetails.setVisibility(View.VISIBLE);
    }

    //view less button
    public void ViewLessDetails(View view) {

        tvSurgeonName.setVisibility(View.GONE);
        tvDiagnosisDescription.setVisibility(View.GONE);
        tvProcedure1.setVisibility(View.GONE);
        tvProcedure2.setVisibility(View.GONE);
        tvProcedure3.setVisibility(View.GONE);
        tvSubSpecialty.setVisibility(View.GONE);
        tvLastMeal.setVisibility(View.GONE);
        tvInformAnesthetist.setVisibility(View.GONE);
        tvAnesthetistName.setVisibility(View.GONE);
        tvHighDependencyArea.setVisibility(View.GONE);
        tvArrivalTime.setVisibility(View.GONE);
        tvApprovedTime.setVisibility(View.GONE);
        tvStatus.setVisibility(View.GONE);

        tvViewSurgeonName.setVisibility(View.GONE);
        tvViewDiagnosisDescription.setVisibility(View.GONE);
        tvViewProcedure1.setVisibility(View.GONE);
        tvViewProcedure2.setVisibility(View.GONE);
        tvViewProcedure3.setVisibility(View.GONE);
        tvViewSubSpecialty.setVisibility(View.GONE);
        tvViewLastMeal.setVisibility(View.GONE);
        tvViewInformAnesthetist.setVisibility(View.GONE);
        tvViewAnesthetistName.setVisibility(View.GONE);
        tvViewHighDependencyArea.setVisibility(View.GONE);
        tvViewArrivalTime.setVisibility(View.GONE);
        tvViewApprovedTime.setVisibility(View.GONE);
        tvViewStatus.setVisibility(View.GONE);

        buttonViewLessDetails.setVisibility(View.GONE);
        buttonViewMoreDetails.setVisibility(View.VISIBLE);
    }
}