package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PatientEditSurgeon extends AppCompatActivity {

    private int position;

    TextView tvID, buttonAddProcedure;

    EditText tvMRN, tvName, tvAge, tvSurgeonName, tvDiagnosisDescription, tvSubSpecialty, tvLastMealDateTime, tvArrivalTime;

    AutoCompleteTextView tvGender, tvWard, tvProcedureName1, tvProcedureName2, tvProcedureName3;

    //Nationality
    TextView tvNation, textViewRaceList, textViewCountry ;

    RadioGroup radioGroup;
    RadioButton checkMalaysian,checkNonMalaysian;

    AutoCompleteTextView tvCountry, tvRaceList;

    ImageView image_remove1, image_remove2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_surgeon);

        //set String
        String[] procedure = getResources().getStringArray(R.array.procedureName);
        String[] nation = getResources().getStringArray(R.array.nation);
        String[] gender = getResources().getStringArray(R.array.gender);
        String[] ward = getResources().getStringArray(R.array.WardName);
        String[] race = getResources().getStringArray(R.array.racelist);
        String[] country = getResources().getStringArray(R.array.Country);

        //set variables
        tvID = findViewById(R.id.ed_id);
        tvMRN = findViewById(R.id.ed_MRN);
        tvName = findViewById(R.id.ed_name);
        tvAge = findViewById(R.id.ed_age);
        tvGender = findViewById(R.id.ed_gender);
        tvWard = findViewById(R.id.ed_ward);
        tvSurgeonName = findViewById(R.id.ed_surgeonName);
        tvDiagnosisDescription = findViewById(R.id.ed_DiagnosisDescription);
        tvProcedureName1 = findViewById(R.id.ed_ProcedureName1);
        tvProcedureName2 = findViewById(R.id.ed_ProcedureName2);
        tvProcedureName3 = findViewById(R.id.ed_ProcedureName3);
        tvSubSpecialty = findViewById(R.id.ed_SubSpecialty);
        tvLastMealDateTime = findViewById(R.id.ed_LastMealDateTime);
        tvArrivalTime = findViewById(R.id.ed_arrivalTime);
        buttonAddProcedure = findViewById(R.id.buttonAddProcedure);


        //For nationality
        tvNation = findViewById(R.id.ed_nation);
        radioGroup = findViewById(R.id.radio_group);
        checkMalaysian = findViewById(R.id.check_Malaysian);
        checkNonMalaysian = findViewById(R.id.check_NonMalaysian);
        textViewRaceList = findViewById(R.id.textViewRaceList);
        textViewCountry = findViewById(R.id.textViewCountry);
        tvCountry = findViewById(R.id.ed_Country);
        tvRaceList = findViewById(R.id.ed_RaceList);
        image_remove1 = findViewById(R.id.image_remove1);
        image_remove2 = findViewById(R.id.image_remove2);


        //get patient data
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        tvID.setText(PatientListSurgeon.patientArrayList.get(position).getId());
        tvMRN.setText(PatientListSurgeon.patientArrayList.get(position).getMRN());
        tvName.setText(PatientListSurgeon.patientArrayList.get(position).getName());
        tvAge.setText(PatientListSurgeon.patientArrayList.get(position).getAge());
        tvGender.setText(PatientListSurgeon.patientArrayList.get(position).getGender());
        tvWard.setText(PatientListSurgeon.patientArrayList.get(position).getWard());
        tvSurgeonName.setText(PatientListSurgeon.patientArrayList.get(position).getSurgeonName());
        tvDiagnosisDescription.setText(PatientListSurgeon.patientArrayList.get(position).getDiagnosisDescription());
        tvProcedureName1.setText(PatientListSurgeon.patientArrayList.get(position).getProcedureName1());
        tvProcedureName2.setText(PatientListSurgeon.patientArrayList.get(position).getProcedureName2());
        tvProcedureName3.setText(PatientListSurgeon.patientArrayList.get(position).getProcedureName3());
        tvSubSpecialty.setText(PatientListSurgeon.patientArrayList.get(position).getSubSpecialty());
        tvLastMealDateTime.setText(PatientListSurgeon.patientArrayList.get(position).getLastMeal());
        tvArrivalTime.setText(PatientListSurgeon.patientArrayList.get(position).getArrivalTime());
        tvNation.setText(PatientListSurgeon.patientArrayList.get(position).getNationality());
        tvRaceList.setText(PatientListSurgeon.patientArrayList.get(position).getRace());
        tvCountry.setText(PatientListSurgeon.patientArrayList.get(position).getCountry());

        //set ward
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,ward);
        tvWard.setAdapter(adapter1);

        //set race
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,race);
        tvRaceList.setAdapter(adapter2);

        //set country
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,country);
        tvCountry.setAdapter(adapter3);


        //set procedure name 1
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,procedure);
        tvProcedureName1.setAdapter(adapter4);

        //set procedure name 2
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,procedure);
        tvProcedureName2.setAdapter(adapter5);

        //set procedure name 3
        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,procedure);
        tvProcedureName3.setAdapter(adapter6);


        //for set a checkbox automatically when user save a data
        String nationality = tvNation.getText().toString().trim();

        if (nationality.equals("MALAYSIAN")){
            checkMalaysian.setChecked(true);

            tvRaceList.setVisibility(View.VISIBLE);
            textViewRaceList.setVisibility(View.VISIBLE);
            tvCountry.setVisibility(View.GONE);
            textViewCountry.setVisibility(View.GONE);

            String result = checkMalaysian.getText().toString();
            tvNation.setText(result);
        }

        else if (nationality.equals("NON-MALAYSIAN")){
            checkNonMalaysian.setChecked(true);

            tvCountry.setVisibility(View.VISIBLE);
            textViewCountry.setVisibility(View.VISIBLE);
            tvRaceList.setVisibility(View.GONE);
            textViewRaceList.setVisibility(View.GONE);

            String result = checkNonMalaysian.getText().toString();
            tvNation.setText(result);
        }



    }


    //button update data
    public void btn_updateData(View view) {
        String id = tvID.getText().toString().trim();
        String MRN = tvMRN.getText().toString().trim();
        String Name = tvName.getText().toString().trim();
        String Age = tvAge.getText().toString().trim();
        String Gender = tvGender.getText().toString().trim();
        String Nationality = tvNation.getText().toString().trim().toUpperCase();
        String Race = tvRaceList.getText().toString().trim().toUpperCase();
        String Country = tvCountry.getText().toString().trim().toUpperCase();
        String Ward = tvWard.getText().toString().trim();
        String SurgeonName = tvSurgeonName.getText().toString().trim();
        String DiagnosisDescription = tvDiagnosisDescription.getText().toString().trim();
        String ProcedureName1 = tvProcedureName1.getText().toString().trim();
        String ProcedureName2 = tvProcedureName2.getText().toString().trim();
        String ProcedureName3 =  tvProcedureName3.getText().toString().trim();
        String SubSpecialty = tvSubSpecialty.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updateDiagnosisForNurse.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(PatientEditSurgeon.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DashboardSurgeon.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PatientEditSurgeon.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("MRN", MRN);
                params.put("Name", Name);
                params.put("Age", Age);
                params.put("Gender", Gender);
                params.put("Nationality", Nationality);
                params.put("Race",Race);
                params.put("Country",Country);
                params.put("Ward", Ward);
                params.put("SurgeonName",SurgeonName);
                params.put("DiagnosisDescription",DiagnosisDescription);
                params.put("ProcedureName1", ProcedureName1);
                params.put("ProcedureName2", ProcedureName2);
                params.put("ProcedureName3", ProcedureName3);
                params.put("SubSpecialty",SubSpecialty);


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PatientEditSurgeon.this);
        requestQueue.add(request);

    }



    //method for radio button
    public void onRadioButtonClicked(View view) {

        String Nationality;

        if (checkMalaysian.isChecked()) {
            tvRaceList.setVisibility(View.VISIBLE);
            textViewRaceList.setVisibility(View.VISIBLE);

            tvCountry.setVisibility(View.GONE);
            textViewCountry.setVisibility(View.GONE);

            Nationality = checkMalaysian.getText().toString();
            tvNation.setText(Nationality);


        }
        else if (checkNonMalaysian.isChecked()) {
            tvCountry.setVisibility(View.VISIBLE);
            textViewCountry.setVisibility(View.VISIBLE);
            tvRaceList.setVisibility(View.GONE);
            textViewRaceList.setVisibility(View.GONE);

            Nationality = checkNonMalaysian.getText().toString();
            tvNation.setText(Nationality);


        }
        else {
            Toast.makeText(this, "Please select the nationality", Toast.LENGTH_SHORT).show();
        }
    }


    //remove procedure name 2
    public void RemoveProcedureName2(View view) {
        tvProcedureName2.setVisibility(View.GONE);
        image_remove1.setVisibility(View.GONE);
        buttonAddProcedure.setVisibility(View.VISIBLE);
    }


    //remove procedure name 3
    public void RemoveProcedureName3(View view) {
        tvProcedureName3.setVisibility(View.GONE);
        image_remove2.setVisibility(View.GONE);
        buttonAddProcedure.setVisibility(View.VISIBLE);
    }


    //add button
    public void AddProcedure(View view) {

        tvProcedureName2.setVisibility(View.VISIBLE);
        image_remove1.setVisibility(View.VISIBLE);
        tvProcedureName3.setVisibility(View.VISIBLE);
        image_remove2.setVisibility(View.VISIBLE);
        buttonAddProcedure.setVisibility(View.GONE);
    }

}