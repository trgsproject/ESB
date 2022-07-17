package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PatientEditDiagnosisNurse extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    int position;
    EditText edId, edMRN, edName, edDiagnosisDescription, edApprovedStatus, edSurgeryTime, edLastMealTime, edSurgeryDate, edLastMealDate, edSurgicalTeam,edArrivalTime;

    AutoCompleteTextView edProcedureName, edOtroom, edSurgeryDuration, edSubSpecialty, edProcedureName2,edProcedureName3;
    TextView lastMeal,bookOT, buttonAddProcedure;

    DatePickerDialog.OnDateSetListener setListener;
    int t1hour,t1Minute;

    ImageView image_remove1, image_remove2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_edit_diagnosis_nurse);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

       //set string
        String[] procedureName = getResources().getStringArray(R.array.procedureName);
        String[] BookOT = getResources().getStringArray(R.array.ot);
        String[] surgeryDuration = getResources().getStringArray(R.array.surgeryDuration);
        String[] subSpecialty = getResources().getStringArray(R.array.SubSpeciality);

        //assigned variables
        edId = findViewById(R.id.ed_id);
        edMRN = findViewById(R.id.ed_mrn);
        edName = findViewById(R.id.ed_PatientName);
        //edSurgeryDuration = findViewById(R.id.ed_SurgeryDuration);
        edDiagnosisDescription = findViewById(R.id.ed_DiagnosisDescription);
        edProcedureName = findViewById(R.id.ed_ProcedureName);
        edApprovedStatus = findViewById(R.id.ed_ApprovedStatus);
        //edOtroom = findViewById(R.id.ed_otroom);
        //edSurgeryTime = findViewById(R.id.ed_surgeryTime);
        //edSurgeryDate =findViewById(R.id.ed_surgeryDate);
        edLastMealTime = findViewById(R.id.ed_LastMeal);
        edLastMealDate = findViewById(R.id.ed_LastMealDate);
        edSurgicalTeam = findViewById(R.id.ed_SurgicalTeam);
        edSubSpecialty = findViewById(R.id.ed_SubSpecialty);
        edArrivalTime = findViewById(R.id.ed_ArrivalTime);

        edProcedureName2 = findViewById(R.id.ed_ProcedureName2);
        edProcedureName3 = findViewById(R.id.ed_ProcedureName3);
        buttonAddProcedure = findViewById(R.id.buttonAddProcedure);
        image_remove1 = findViewById(R.id.image_remove1);
        image_remove2 = findViewById(R.id.image_remove2);

        //separately data and time
        lastMeal = findViewById(R.id.lastMeal);
        //bookOT = findViewById(R.id.bookOT);

        //get patient data
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        edId.setText(DiagnosisList.diagnosisArrayList.get(position).getId());
        edMRN.setText(DiagnosisList.diagnosisArrayList.get(position).getMrn());
        edName.setText(DiagnosisList.diagnosisArrayList.get(position).getName());
        edDiagnosisDescription.setText(DiagnosisList.diagnosisArrayList.get(position).getName_diagnosis_desc());
        edProcedureName.setText(DiagnosisList.diagnosisArrayList.get(position).getProcedure_name());
        edApprovedStatus.setText(DiagnosisList.diagnosisArrayList.get(position).getApproved_status());
        edSurgicalTeam.setText(DiagnosisList.diagnosisArrayList.get(position).getSurgical_team());
        edSubSpecialty.setText(DiagnosisList.diagnosisArrayList.get(position).getSub_specialty());
        edArrivalTime.setText(DiagnosisList.diagnosisArrayList.get(position).getArrival_time_to_surgeon());
        lastMeal.setText(DiagnosisList.diagnosisArrayList.get(position).getLast_meal());
        edProcedureName2.setText(DiagnosisList.diagnosisArrayList.get(position).getProcedure_name_2());
        edProcedureName3.setText(DiagnosisList.diagnosisArrayList.get(position).getProcedure_name_3());

        //set array and separately the date and time of last meal
        String l = lastMeal.getText().toString().trim();
        String [] split = l.split(" ");
        String l1 = split[0]; //date last meal
        String l2 = split[1]; //time last meal
        edLastMealDate.setText(l1);
        edLastMealTime.setText(l2);


        //set array and separately the date and time of last meal
        //String o = bookOT.getText().toString().trim();
        //String [] split1 = o.split(" ");
        //String o1 = split1[0]; //date last meal
        //String o2 = split1[1]; //time last meal
        //edSurgeryDate.setText(o1);
        //edSurgeryTime.setText(o2);


        //if procedure name 2 exist
        String ProcedureCheck2 = edProcedureName2.getText().toString().trim();

        if (ProcedureCheck2.equals("-")){
            edProcedureName2.setVisibility(View.GONE);
            image_remove1.setVisibility(View.GONE);
            buttonAddProcedure.setVisibility(View.VISIBLE);
        }


        //if procedure name 3 exist
        String ProcedureCheck3 = edProcedureName3.getText().toString().trim();

        if (ProcedureCheck3.equals("-")){
            edProcedureName3.setVisibility(View.GONE);
            image_remove2.setVisibility(View.GONE);
            buttonAddProcedure.setVisibility(View.VISIBLE);
        }


        //for autocomplete Ot room
        //ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,BookOT);
        //edOtroom.setAdapter(adapter1);

        //for autocomplete surgery duration
        //ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,surgeryDuration);
        //edSurgeryDuration.setAdapter(adapter2);

        //for autocomplete procedure name
        ArrayAdapter<String> adapter3a = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,procedureName);
        edProcedureName.setAdapter(adapter3a);

        //for autocomplete procedure name
        ArrayAdapter<String> adapter3b = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,procedureName);
        edProcedureName2.setAdapter(adapter3b);

        //for autocomplete procedure name
        ArrayAdapter<String> adapter3c = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,procedureName);
        edProcedureName3.setAdapter(adapter3c);

        //for autocomplete sub specialty
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,subSpecialty);
        edSubSpecialty.setAdapter(adapter4);


        /**
        //pick surgery time
        edSurgeryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(PatientEditDiagnosisNurse.this, new TimePickerDialog.OnTimeSetListener() {
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
**/

        //pick last meal time
        edLastMealTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(PatientEditDiagnosisNurse.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        t1hour =hourOfDay;
                        t1Minute=minute;

                        Calendar datetime = Calendar.getInstance();

                        datetime.set(0,0,0,t1hour,t1Minute);

                        //edTime.setText(DateFormat.format("hh:mm aaa", datetime));
                        edLastMealTime.setText(DateFormat.format("HH:mm:00", datetime));
                    }
                },12,0,false);
                timePickerDialog.updateTime(t1hour,t1Minute);
                timePickerDialog.show();
            }
        });


        //method pick date of last meal
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(PatientEditDiagnosisNurse.this
                , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar updateDate = Calendar.getInstance();
                updateDate.set(year, month, dayOfMonth);
                edLastMealDate.setText(DateFormat.format("yyyy-MM-dd", updateDate));
            }
        },calendar.get(Calendar.DAY_OF_MONTH ),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());


        //get date last meal
        edLastMealDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });



        /**

        //method pick date of book ot
        final Calendar calendar1 = Calendar.getInstance();
        final DatePickerDialog datePickerDialog1 = new DatePickerDialog(PatientEditDiagnosisNurse.this
                , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar updateDate = Calendar.getInstance();
                updateDate.set(year, month, dayOfMonth);
                edSurgeryDate.setText(DateFormat.format("yyyy-MM-dd", updateDate));
            }
        },calendar1.get(Calendar.DAY_OF_MONTH ),calendar1.get(Calendar.MONTH),calendar1.get(Calendar.YEAR));
        datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis());


        //get date operation
        edSurgeryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog1.show();
            }
        });

**/

    }

    public void btn_updateData(View view) {
        final String id = edId.getText().toString();
        final String mrn = edMRN.getText().toString().toUpperCase();
        final String name = edName.getText().toString().toUpperCase();
        //final String otroom = edOtroom.getText().toString();
        final String name_diagnosis_desc = edDiagnosisDescription.getText().toString();
        final String procedure_name = edProcedureName.getText().toString();
        final String procedure_name_2 = edProcedureName2.getText().toString();
        final String procedure_name_3 = edProcedureName3.getText().toString();
        //final String surgeryTime = edSurgeryDate.getText().toString() + " " +  edSurgeryTime.getText().toString();
        //final String surgery_duration = edSurgeryDuration.getText().toString();
        final String approved_status = edApprovedStatus.getText().toString();
        final String sub_specialty =edSubSpecialty.getText().toString();
        final String last_meal = edLastMealDate.getText().toString() + " " + edLastMealTime.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updateDiagnosisForNurse.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(PatientEditDiagnosisNurse.this, response, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DiagnosisList.class));
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PatientEditDiagnosisNurse.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("mrn", mrn);
                params.put("name", name);
                //params.put("otroom", otroom);
                params.put("name_diagnosis_desc", name_diagnosis_desc);
                params.put("procedure_name", procedure_name);
                params.put("procedure_name_2",procedure_name_2);
                params.put("procedure_name_3", procedure_name_3);
                //params.put("surgeryTime", surgeryTime);
                //params.put("surgery_duration", surgery_duration);
                params.put("approved_status", approved_status);
                params.put("sub_specialty",sub_specialty);
                params.put("last_meal",last_meal);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(PatientEditDiagnosisNurse.this);
        requestQueue.add(request);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void RemoveProcedureName2(View view) {
        edProcedureName2.setVisibility(View.GONE);
        image_remove1.setVisibility(View.GONE);
        buttonAddProcedure.setVisibility(View.VISIBLE);
        edProcedureName2.setText("-");
    }

    public void RemoveProcedureName3(View view) {
        edProcedureName3.setVisibility(View.GONE);
        image_remove2.setVisibility(View.GONE);
        buttonAddProcedure.setVisibility(View.VISIBLE);
        edProcedureName3.setText("-");
    }

    public void AddProcedure(View view) {
        edProcedureName2.setVisibility(View.VISIBLE);
        image_remove1.setVisibility(View.VISIBLE);
        edProcedureName3.setVisibility(View.VISIBLE);
        image_remove2.setVisibility(View.VISIBLE);
        buttonAddProcedure.setVisibility(View.GONE);

    }
}