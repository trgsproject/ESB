package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
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
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.util.HashMap;
import java.util.Map;

public class PatientEditDetailsNurse extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText edId, edUserId, edName, edUsername, edAge, edIc, edArrivalTime ;
    private int position;

    AutoCompleteTextView  edGender , edWard;
    TextView edNation, textViewRaceList, textViewCountry ;

    RadioGroup radioGroup;
    RadioButton checkMalaysian,checkNonMalaysian;

    AutoCompleteTextView textCountry, textRaceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_details_nurse);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

        //set String
        String[] Nation = getResources().getStringArray(R.array.nation);
        String[] Gender = getResources().getStringArray(R.array.gender);
        String[] Ward = getResources().getStringArray(R.array.WardName);
        String[] Race = getResources().getStringArray(R.array.racelist);
        String[] Country = getResources().getStringArray(R.array.Country);

        //assigned variables
        edId = findViewById(R.id.ed_id);
        edUserId= findViewById(R.id.ed_userid);
        edName = findViewById(R.id.ed_name);
        edUsername = findViewById(R.id.ed_username);
        edAge= findViewById(R.id.ed_age);
        edGender = findViewById(R.id.ed_gender);
        edIc = findViewById(R.id.ed_ic);
        edArrivalTime = findViewById(R.id.ed_arrivalTime);
        edWard = findViewById(R.id.ed_ward);

        //For nationality
        edNation = findViewById(R.id.ed_nation);
        radioGroup = findViewById(R.id.radio_group);
        checkMalaysian = findViewById(R.id.check_Malaysian);
        checkNonMalaysian = findViewById(R.id.check_NonMalaysian);
        textViewRaceList = findViewById(R.id.textViewRaceList);
        textViewCountry = findViewById(R.id.textViewCountry);
        textCountry = findViewById(R.id.textCountry);
        textRaceList = findViewById(R.id.textRaceList);

        //get patient data
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        edId.setText(PatientList.patientArrayList.get(position).getId());
        edUserId.setText(PatientList.patientArrayList.get(position).getMRN());
        edName.setText(PatientList.patientArrayList.get(position).getName());
        edAge.setText(PatientList.patientArrayList.get(position).getAge());
        edGender.setText(PatientList.patientArrayList.get(position).getGender());
        edWard.setText(PatientList.patientArrayList.get(position).getWard());
        edArrivalTime.setText(PatientList.patientArrayList.get(position).getArrivalTime());
        edNation.setText(PatientList.patientArrayList.get(position).getNationality());
        textRaceList.setText(PatientList.patientArrayList.get(position).getRace());
        textCountry.setText(PatientList.patientArrayList.get(position).getCountry());

        //set gender
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Gender);
        edGender.setAdapter(adapter1);

        //set ward
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Ward);
        edWard.setAdapter(adapter2);

        //set race
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Race);
        textRaceList.setAdapter(adapter3);

        //set country
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Country);
        textCountry.setAdapter(adapter4);


        //for set a checkbox automatically when user save a data
        String nationality = edNation.getText().toString().trim();

        if (nationality.equals("MALAYSIAN")){
            checkMalaysian.setChecked(true);

            textRaceList.setVisibility(View.VISIBLE);
            textViewRaceList.setVisibility(View.VISIBLE);
            textCountry.setVisibility(View.GONE);
            textViewCountry.setVisibility(View.GONE);

            String result = checkMalaysian.getText().toString();
            edNation.setText(result);
        }

        else if (nationality.equals("NON-MALAYSIAN")){
            checkNonMalaysian.setChecked(true);

            textCountry.setVisibility(View.VISIBLE);
            textViewCountry.setVisibility(View.VISIBLE);
            textRaceList.setVisibility(View.GONE);
            textViewRaceList.setVisibility(View.GONE);

            String result = checkNonMalaysian.getText().toString();
            edNation.setText(result);
        }


    }


    // Update data into database
    public void btn_updateData(View view) {

        final String nationality = edNation.getText().toString().toUpperCase();

        if (nationality.equals("MALAYSIAN")) {

            final String id = edId.getText().toString();
            final String mrn = edUserId.getText().toString().toUpperCase();
            final String name = edName.getText().toString().toUpperCase();
            final String shortname = edUsername.getText().toString().toUpperCase();
            final String age = edAge.getText().toString();
            final String gender = edGender.getText().toString().toUpperCase();
            final String ic = edIc.getText().toString();
            final String race = textRaceList.getText().toString().toUpperCase();
            final String country = "-";
            final String ward = edWard.getText().toString().toUpperCase();
            final String arrival_time = edArrivalTime.getText().toString();

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Updating....");

            if (mrn.isEmpty()) {
                Toast.makeText(this, "Please enter a MRN!", Toast.LENGTH_SHORT).show();
                edUserId.setError("Please enter a MRN!");
                return;

            } else if (name.isEmpty()) {
                Toast.makeText(this, "Please enter a name!", Toast.LENGTH_SHORT).show();
                edName.setError("Please enter a name!");
                return;

            } else if (shortname.isEmpty()) {
                Toast.makeText(this, "Please enter a username!", Toast.LENGTH_SHORT).show();
                edUsername.setError("Please enter a username!");
                return;

            } else if (age.isEmpty()) {
                Toast.makeText(this, "Please enter an age!", Toast.LENGTH_SHORT).show();
                edAge.setError("Please enter an age!");
                return;

            } else if (gender.isEmpty()) {
                Toast.makeText(this, "Please enter a gender!", Toast.LENGTH_SHORT).show();
                edAge.setError("Please enter a gender!");
                return;

            } else if (ic.isEmpty()) {
                Toast.makeText(this, "Please enter IC/Passport No!", Toast.LENGTH_SHORT).show();
                edIc.setError("Please enter IC/Passport No!");
                return;

            } else if (nationality.isEmpty()) {
                Toast.makeText(this, "Please enter a nation!", Toast.LENGTH_SHORT).show();
                edNation.setError("Please enter a nation!");
                return;

            } else if (ward.isEmpty()) {
                Toast.makeText(this, "Please enter a ward!", Toast.LENGTH_SHORT).show();
                edWard.setError("Please enter a nation!");
                return;

            } else if (arrival_time.isEmpty()) {
                Toast.makeText(this, "Please enter the arrival time!", Toast.LENGTH_SHORT).show();
                edArrivalTime.setError("Please enter the arrival time!");
                return;

            } else {
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updatePatient.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(PatientEditDetailsNurse.this, response, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), PatientList.class));
                                finish();
                                progressDialog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(PatientEditDetailsNurse.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("id", id);
                        params.put("mrn", mrn);
                        params.put("name", name);
                        params.put("shortname", shortname);
                        params.put("age", age);
                        params.put("gender", gender);
                        params.put("ic", ic);
                        params.put("nationality", nationality);
                        params.put("race", race);
                        params.put("country", country);
                        params.put("ward", ward);
                        params.put("arrival_time", arrival_time);

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(PatientEditDetailsNurse.this);
                requestQueue.add(request);

            }
        }
            else if (nationality.equals("NON-MALAYSIAN")){

                final String id = edId.getText().toString();
                final String mrn = edUserId.getText().toString().toUpperCase();
                final String name = edName.getText().toString().toUpperCase();
                final String shortname = edUsername.getText().toString().toUpperCase();
                final String age = edAge.getText().toString();
                final String gender = edGender.getText().toString().toUpperCase();
                final String ic = edIc.getText().toString();
                final String race = "-";
                final String country = textCountry.getText().toString().trim().toUpperCase();
                final String ward = edWard.getText().toString().toUpperCase();
                final String arrival_time = edArrivalTime.getText().toString();

                final ProgressDialog progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Updating....");

                if (mrn.isEmpty()) {
                    Toast.makeText(this, "Please enter a MRN!", Toast.LENGTH_SHORT).show();
                    edUserId.setError("Please enter a MRN!");
                    return;

                } else if (name.isEmpty()) {
                    Toast.makeText(this, "Please enter a name!", Toast.LENGTH_SHORT).show();
                    edName.setError("Please enter a name!");
                    return;

                } else if (shortname.isEmpty()) {
                    Toast.makeText(this, "Please enter a username!", Toast.LENGTH_SHORT).show();
                    edUsername.setError("Please enter a username!");
                    return;

                } else if (age.isEmpty()) {
                    Toast.makeText(this, "Please enter an age!", Toast.LENGTH_SHORT).show();
                    edAge.setError("Please enter an age!");
                    return;

                } else if (gender.isEmpty()) {
                    Toast.makeText(this, "Please enter a gender!", Toast.LENGTH_SHORT).show();
                    edAge.setError("Please enter a gender!");
                    return;

                } else if (ic.isEmpty()) {
                    Toast.makeText(this, "Please enter IC/Passport No!", Toast.LENGTH_SHORT).show();
                    edIc.setError("Please enter IC/Passport No!");
                    return;

                } else if (nationality.isEmpty()) {
                    Toast.makeText(this, "Please enter a nation!", Toast.LENGTH_SHORT).show();
                    edNation.setError("Please enter a nation!");
                    return;

                } else if (ward.isEmpty()) {
                    Toast.makeText(this, "Please enter a ward!", Toast.LENGTH_SHORT).show();
                    edWard.setError("Please enter a nation!");
                    return;

                }else if (arrival_time.isEmpty()) {
                    Toast.makeText(this, "Please enter the arrival time!", Toast.LENGTH_SHORT).show();
                    edArrivalTime.setError("Please enter the arrival time!");
                    return;

                } else {
                    progressDialog.show();
                    StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updatePatient.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    Toast.makeText(PatientEditDetailsNurse.this, response, Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), PatientList.class));
                                    finish();
                                    progressDialog.dismiss();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(PatientEditDetailsNurse.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();

                            params.put("id", id);
                            params.put("mrn", mrn);
                            params.put("name", name);
                            params.put("shortname", shortname);
                            params.put("age", age);
                            params.put("gender", gender);
                            params.put("ic", ic);
                            params.put("nationality", nationality);
                            params.put("race", race);
                            params.put("country", country);
                            params.put("ward", ward);
                            params.put("arrival_time", arrival_time);

                            return params;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(PatientEditDetailsNurse.this);
                    requestQueue.add(request);
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    //method for radio button
    public void onRadioButtonClicked(View view) {


        if (checkMalaysian.isChecked()) {
            textRaceList.setVisibility(View.VISIBLE);
            textViewRaceList.setVisibility(View.VISIBLE);

            textCountry.setVisibility(View.GONE);
            textViewCountry.setVisibility(View.GONE);

            edNation.setText("MALAYSIAN");

        }
        else if (checkNonMalaysian.isChecked()) {
            textCountry.setVisibility(View.VISIBLE);
            textViewCountry.setVisibility(View.VISIBLE);
            textRaceList.setVisibility(View.GONE);
            textViewRaceList.setVisibility(View.GONE);

            edNation.setText("NON-MALAYSIAN");

        }
        else {
            Toast.makeText(this, "Please select the nationality", Toast.LENGTH_SHORT).show();
        }
    }


}