package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PatientEditDiagnosis extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    int position;

    EditText edId,edMRN,edName,edDiagnosisDescription,edApprovedStatus, edSurgeryTime;
    AutoCompleteTextView edProcedureName, edOtroom, edSurgeryDuration;

    TextView edViewAnaesthetistID;
    TextView textTimeApproval;

    DatePickerDialog.OnDateSetListener setListener;
    int t1hour,t1Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_diagnosis);

        //set string
        String[] procedureName = getResources().getStringArray(R.array.procedureName);
        String[] BookOT = getResources().getStringArray(R.array.ot);
        String[] surgeryDuration = getResources().getStringArray(R.array.surgeryDuration);

        //assigned variables
        edId = findViewById(R.id.ed_id);
        edMRN = findViewById(R.id.ed_mrn);
        edName = findViewById(R.id.ed_PatientName);
        edSurgeryDuration = findViewById(R.id.ed_SurgeryDuration);
        edDiagnosisDescription = findViewById(R.id.ed_DiagnosisDescription);
        edProcedureName = findViewById(R.id.ed_ProcedureName);
        edApprovedStatus = findViewById(R.id.ed_ApprovedStatus);
        edOtroom = findViewById(R.id.ed_otroom);
        edViewAnaesthetistID = findViewById(R.id.textViewAnaesthetistID);
        textTimeApproval = findViewById(R.id.textTimeApproval);
        edSurgeryTime = findViewById(R.id.ed_surgeryTime);

        //get patient data
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");



        //set anaes
        edViewAnaesthetistID.setText(SharedPrefManager.getInstance(this).getUsername());
        textTimeApproval.setText(getCreatedDateWithMonthName() + "  " + getTimeInWithAmAndPm());

        //for autocomplete Ot room
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,BookOT);
        edOtroom.setAdapter(adapter1);

        //for autocomplete surgery duration
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,surgeryDuration);
        edSurgeryDuration.setAdapter(adapter2);

        //for autocomplete procedure name
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,procedureName);
        edProcedureName.setAdapter(adapter3);


        //pick current time
        edSurgeryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(PatientEditDiagnosis.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        t1hour =hourOfDay;
                        t1Minute=minute;

                        Calendar datetime = Calendar.getInstance();

                        datetime.set(0,0,0,t1hour,t1Minute);

                        //edTime.setText(DateFormat.format("hh:mm aaa", datetime));
                        edSurgeryTime.setText(DateFormat.format("HH:mm:00", datetime));
                    }
                },12,0,false);
                timePickerDialog.updateTime(t1hour,t1Minute);
                timePickerDialog.show();
            }
        });



    }

    //method to get created date
    private String getCreatedDateWithMonthName(){
        return new SimpleDateFormat("dd-LLL-yyyy", Locale.getDefault()).format(new Date());
    }

    //method to get current time created
    private String getTimeInWithAmAndPm(){
        return new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());
    }

    public void btn_updateData(View view) {
        final String id = edId.getText().toString();
        final String mrn = edMRN.getText().toString().toUpperCase();
        final String name = edName.getText().toString().toUpperCase();
        final String otroom = edOtroom.getText().toString();
        final String surgeryTime = edSurgeryTime.getText().toString();
        final String surgery_duration = edSurgeryDuration.getText().toString();
        final String name_diagnosis_desc = edDiagnosisDescription.getText().toString();
        final String procedure_name = edProcedureName.getText().toString();
        final String anaesthetist_id = edViewAnaesthetistID.getText().toString();


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updateDiagnosis.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(PatientEditDiagnosis.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PatientEditDiagnosis.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("mrn", mrn);
                params.put("name", name);
                params.put("otroom", otroom);
                params.put("name_diagnosis_desc", name_diagnosis_desc);
                params.put("procedure_name", procedure_name);
                params.put("surgeryTime",surgeryTime);
                params.put("surgery_duration", surgery_duration);
                params.put("anaesthetist_id",anaesthetist_id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PatientEditDiagnosis.this);
        requestQueue.add(request);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}