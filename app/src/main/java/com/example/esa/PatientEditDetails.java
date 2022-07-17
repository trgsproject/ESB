package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
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

public class PatientEditDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText edId,edUserId,edName,edUsername,edAge,edGender,edIc,edNation;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_details);

        String[] ProcedureName = getResources().getStringArray(R.array.procedureName);
        String[] BookOT = getResources().getStringArray(R.array.ot);

        //assigned variables
        edId = findViewById(R.id.ed_id);
        edUserId= findViewById(R.id.ed_userid);
        edName = findViewById(R.id.ed_name);
        edUsername = findViewById(R.id.ed_username);
        edAge= findViewById(R.id.ed_age);
        edGender = findViewById(R.id.ed_gender);
        edIc = findViewById(R.id.ed_ic);
        edNation = findViewById(R.id.ed_nation);

        //get patient data
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        edId.setText(PatientListAnes.patientArrayList.get(position).getId());
        edUserId.setText(PatientListAnes.patientArrayList.get(position).getMRN());
        edName.setText(PatientListAnes.patientArrayList.get(position).getName());
        //edUsername.setText(PatientListAnes.patientArrayList.get(position).getShortname());
        edAge.setText(PatientListAnes.patientArrayList.get(position).getAge());
        edGender.setText(PatientListAnes.patientArrayList.get(position).getGender());
        //edIc.setText(PatientListAnes.patientArrayList.get(position).getIc());
        edNation.setText(PatientListAnes.patientArrayList.get(position).getNationality());


    }

    public void btn_updateData(View view) {


        final String id = edId.getText().toString();
        final String mrn = edUserId.getText().toString().toUpperCase();
        final String name = edName.getText().toString().toUpperCase();
        final String username = edUsername.getText().toString().toUpperCase();
        final String age = edAge.getText().toString();
        final String gender = edGender.getText().toString().toUpperCase();
        final String ic = edIc.getText().toString();
        final String nation = edNation.getText().toString().toUpperCase();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updatePatient.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(PatientEditDetails.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), PatientListAnes.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PatientEditDetails.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("mrn", mrn);
                params.put("username", username);
                params.put("name", name);
                params.put("age", age);
                params.put("gender", gender);
                params.put("ic", ic);
                params.put("nation", nation);
                params.put("gender", gender);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PatientEditDetails.this);
        requestQueue.add(request);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}