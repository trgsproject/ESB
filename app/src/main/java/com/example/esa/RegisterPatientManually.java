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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.esa.api.ApiUtilities;
import com.example.esa.model.NotificationData;
import com.example.esa.model.PushNotification;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.esa.Constants.TOPIC;

public class RegisterPatientManually extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    EditText textMRN, textName, textAge, textDesc, textLastMealTime, textLastMealDate;

    //Nationality
    TextView textNation;
    RadioGroup radioGroup;
    RadioButton checkMalaysian,checkNonMalaysian;
    ImageView image_remove1, image_remove2;

    Spinner textGender, textCountry, textRaceList;

    TextView textUserRole, textSurgicalTeamName, textSubSpecialty, buttonAddProcedure;

    AutoCompleteTextView textWard,textProcedure, edProcedureName2,edProcedureName3;

    Button btnCancel,btnSave;
    TextView textViewRaceList, textViewCountry;

    DatePickerDialog.OnDateSetListener setListener;
    int t1hour, t1Minute;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient_manually);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

        //get topic for notification
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        //set string
        String[] WardName = getResources().getStringArray(R.array.WardName);
        String[] Country = getResources().getStringArray(R.array.Country);
        String[] procedureName = getResources().getStringArray(R.array.procedureName);
        String[] BookOT = getResources().getStringArray(R.array.ot);
        String[] SubSpecialty = getResources().getStringArray(R.array.SubSpeciality);
        //String[] SurgeonName = getResources().getStringArray(R.array.SurgeonName);

        //assign variables
        textMRN = findViewById(R.id.textMRN);
        textName = findViewById(R.id.textName);
        textAge = findViewById(R.id.textAge);
        textGender = findViewById(R.id.textGender);
        textNation = findViewById(R.id.textNation);
        textViewRaceList = findViewById(R.id.textViewRaceList);
        textRaceList = findViewById(R.id.textRaceList);
        textViewCountry = findViewById(R.id.textViewCountry);
        textCountry = findViewById(R.id.textCountry);
        textWard = findViewById(R.id.textWard);
        textUserRole = findViewById(R.id.textUserRole);
        textSurgicalTeamName = findViewById(R.id.textSurgicalTeam);
        textDesc = findViewById(R.id.textDesc);
        textProcedure = findViewById(R.id.textProcedureName);
        edProcedureName2 = findViewById(R.id.ed_ProcedureName2);
        edProcedureName3 = findViewById(R.id.ed_ProcedureName3);
        textSubSpecialty = findViewById(R.id.textSubSpecialty);
        textLastMealDate = findViewById(R.id.textLastMealDate);
        textLastMealTime = findViewById(R.id.textLastMealTime);
        btnCancel = findViewById(R.id.buttonCancel);
        btnSave= findViewById(R.id.buttonSave);
        radioGroup = findViewById(R.id.radio_group);
        checkMalaysian = findViewById(R.id.check_Malaysian);
        checkNonMalaysian = findViewById(R.id.check_NonMalaysian);
        buttonAddProcedure = findViewById(R.id.buttonAddProcedure);
        image_remove1 = findViewById(R.id.image_remove1);
        image_remove2 = findViewById(R.id.image_remove2);


        //get Name , Role and Sub specialty
        textSurgicalTeamName.setText(SharedPrefManager.getInstance(this).getUserFullname());
        textUserRole.setText(SharedPrefManager.getInstance(this).getUserRole());
        textSubSpecialty.setText(SharedPrefManager.getInstance(this).getUserSubspeciality());

        //set autoComplete for ward
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,WardName);
        textWard.setAdapter(adapter1);

        //set gender
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.gender, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textGender.setAdapter(adapter2);

        //set race list
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this,R.array.racelist, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textRaceList.setAdapter(adapter4);

        //set Country
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(this,R.array.Country, android.R.layout.simple_spinner_item);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textCountry.setAdapter(adapter5);

        //for autocomplete Procedure Name
        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, procedureName);
        textProcedure.setAdapter(adapter6);

        //for autocomplete Procedure Name
        ArrayAdapter<String> adapter7= new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, procedureName);
        edProcedureName2.setAdapter(adapter7);

        //for autocomplete Procedure Name
        ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, procedureName);
        edProcedureName3.setAdapter(adapter8);


        //pick last meal time
        textLastMealTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterPatientManually.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        t1hour =hourOfDay;
                        t1Minute=minute;

                        Calendar datetime = Calendar.getInstance();

                        datetime.set(0,0,0,t1hour,t1Minute);

                        //edTime.setText(DateFormat.format("hh:mm aaa", datetime));
                        textLastMealTime.setText(DateFormat.format("HH:mm:00", datetime));
                    }
                },12,0,false);
                timePickerDialog.updateTime(t1hour,t1Minute);
                timePickerDialog.show();
            }
        });



        //method pick date of last meal
        final Calendar calendar = Calendar.getInstance();
        final DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterPatientManually.this
                , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Calendar updateDate = Calendar.getInstance();
                updateDate.set(year, month, dayOfMonth);
                textLastMealDate.setText(DateFormat.format("yyyy-MM-dd", updateDate));
            }
        },calendar.get(Calendar.DAY_OF_MONTH ),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());


        //get Last Meal Date
        textLastMealDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        

        //save into database while click button save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //for notification
                final String titletxt = "New Patient Registered!";
                final String msgtxt = "Patient: " + textName.getText().toString().trim();

                if (!titletxt.isEmpty() && !msgtxt.isEmpty()) {
                    PushNotification notification = new PushNotification(new NotificationData(titletxt, msgtxt), TOPIC);
                    sendNotification(notification);
                    insertData();
                }
            }
        });


        //button cancel check in
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( textUserRole.getText().toString().equals("Anaesthetist")) {
                    Intent a = new Intent(RegisterPatientManually.this,Dashboard.class);
                    a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);

                }else if( textUserRole.getText().toString().equals("OT Team")){
                    Intent b = new Intent(RegisterPatientManually.this,DashboardNurse.class);
                    b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(b);

                }else if( textUserRole.getText().toString().equals("Surgical Team")){
                    Intent b = new Intent(RegisterPatientManually.this,DashboardSurgeon.class);
                    b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(b);
                }

            }
        });
    }


    //method to get created date
    private String getCreatedDateWithMonthName(){
        return new SimpleDateFormat("dd-LLL-yyyy", Locale.getDefault()).format(new Date());
    }

    //method to get current time arrival
    private String getTimeArrivalWithAmAndPm(){
        return new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());

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
                    Toast.makeText(RegisterPatientManually.this,"error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(RegisterPatientManually.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    //method save data into database
    private void insertData() {

        final String Nationality = textNation.getText().toString().trim().toUpperCase();

        if (Nationality.equals("MALAYSIAN")){

            final String MRN = textMRN.getText().toString().trim().toUpperCase();
            final String Name = textName.getText().toString().trim().toUpperCase();
            final String Age = textAge.getText().toString().trim();
            final String Gender = textGender.getSelectedItem().toString().trim().toUpperCase();
            final String Race = textRaceList.getSelectedItem().toString().trim().toUpperCase();
            final String Country = "-";
            final String Ward = textWard.getText().toString().trim();
            final String SurgeonName = textSurgicalTeamName.getText().toString().trim();
            final String DiagnosisDescription = textDesc.getText().toString().trim();
            final String ProcedureName1 = textProcedure.getText().toString().trim();
            final String ProcedureName2 = edProcedureName2.getText().toString().trim();
            final String ProcedureName3 = edProcedureName3.getText().toString().trim();
            final String SubSpecialty = textSubSpecialty.getText().toString().trim();
            final String LastMeal = textLastMealDate.getText().toString().trim() + " " + textLastMealTime.getText().toString().trim();


            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");

            if (MRN.isEmpty()) {
                Toast.makeText(this, "Enter Patient ID", Toast.LENGTH_SHORT).show();
                textMRN.setError("Enter Patient MRN");
                return;
            } else if (Name.isEmpty()) {
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
                textName.setError("Enter Name");
                return;
            } else if (Age.isEmpty()) {
                Toast.makeText(this, "Enter Age", Toast.LENGTH_SHORT).show();
                textAge.setError("Enter Age");
                return;
            } else if (Gender.equals("Gender")) {
                Toast.makeText(this, "Enter Gender", Toast.LENGTH_SHORT).show();
                return;
            } else if (Ward.isEmpty()) {
                Toast.makeText(this, "Enter Ward Name", Toast.LENGTH_SHORT).show();
                return;
            } else {
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "insertPatient.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equalsIgnoreCase("Data Inserted")) {

                                    if (textUserRole.getText().toString().equals("Anaesthetist")) {
                                        Intent i = new Intent(RegisterPatientManually.this, Dashboard.class);
                                        Toast.makeText(RegisterPatientManually.this, "Patient successfully register!", Toast.LENGTH_SHORT).show();
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                        progressDialog.dismiss();

                                    } else if (textUserRole.getText().toString().equals("OT Team")) {
                                        Intent b = new Intent(RegisterPatientManually.this, DashboardNurse.class);
                                        Toast.makeText(RegisterPatientManually.this, "Patient successfully register!", Toast.LENGTH_SHORT).show();
                                        b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(b);
                                        progressDialog.dismiss();

                                    } else if (textUserRole.getText().toString().equals("Surgical Team")) {
                                        Intent b = new Intent(RegisterPatientManually.this, DashboardSurgeon.class);
                                        Toast.makeText(RegisterPatientManually.this, "Patient successfully register!", Toast.LENGTH_SHORT).show();
                                        b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(b);
                                        progressDialog.dismiss();
                                    }

                                } else {
                                    Toast.makeText(RegisterPatientManually.this, response, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterPatientManually.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("MRN", MRN);
                        params.put("Name", Name);
                        params.put("Age", Age);
                        params.put("Gender", Gender);
                        params.put("Nationality", Nationality);
                        params.put("Race", Race);
                        params.put("Country", Country);
                        params.put("Ward", Ward);
                        params.put("SurgeonName", SurgeonName);
                        params.put("DiagnosisDescription", DiagnosisDescription);
                        params.put("ProcedureName1", ProcedureName1 );
                        params.put("ProcedureName2",ProcedureName2 );
                        params.put("ProcedureName3", ProcedureName3 );
                        params.put("SubSpecialty", SubSpecialty);
                        params.put("LastMeal",LastMeal);

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(RegisterPatientManually.this);
                requestQueue.add(request);

            }
        }

        else {


            final String MRN = textMRN.getText().toString().trim().toUpperCase();
            final String Name = textName.getText().toString().trim().toUpperCase();
            final String Age = textAge.getText().toString().trim();
            final String Gender = textGender.getSelectedItem().toString().trim().toUpperCase();
            final String Race = "-";
            final String Country = textCountry.getSelectedItem().toString().trim().toUpperCase();
            final String Ward = textWard.getText().toString().trim();
            final String SurgeonName = textSurgicalTeamName.getText().toString().trim();
            final String DiagnosisDescription = textDesc.getText().toString().trim();
            final String ProcedureName1 = textProcedure.getText().toString().trim();
            final String ProcedureName2 = edProcedureName2.getText().toString().trim();
            final String ProcedureName3 = edProcedureName3.getText().toString().trim();
            final String SubSpecialty = textSubSpecialty.getText().toString().trim();
            final String LastMeal = textLastMealDate.getText().toString().trim() + " " + textLastMealTime.getText().toString().trim();

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...");

            if (MRN.isEmpty()) {
                Toast.makeText(this, "Enter Patient ID", Toast.LENGTH_SHORT).show();
                textMRN.setError("Enter Patient ID");
                return;
            } else if (Name.isEmpty()) {
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
                textName.setError("Enter Name");
                return;
            } else if (Age.isEmpty()) {
                Toast.makeText(this, "Enter Age", Toast.LENGTH_SHORT).show();
                textAge.setError("Enter Age");
                return;
            } else if (Gender.equals("Gender")) {
                Toast.makeText(this, "Enter Gender", Toast.LENGTH_SHORT).show();
                return;
            } else if (Ward.isEmpty()) {
                Toast.makeText(this, "Enter Ward Name", Toast.LENGTH_SHORT).show();
                return;
            } else {
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "insertPatient.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if (response.equalsIgnoreCase("Data Inserted")) {

                                    if (textUserRole.getText().toString().equals("Anaesthetist")) {
                                        Intent i = new Intent(RegisterPatientManually.this, Dashboard.class);
                                        Toast.makeText(RegisterPatientManually.this, "Patient successfully register!", Toast.LENGTH_SHORT).show();
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);
                                        progressDialog.dismiss();

                                    } else if (textUserRole.getText().toString().equals("OT Team")) {
                                        Intent b = new Intent(RegisterPatientManually.this, DashboardNurse.class);
                                        Toast.makeText(RegisterPatientManually.this, "Patient successfully register!", Toast.LENGTH_SHORT).show();
                                        b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(b);
                                        progressDialog.dismiss();

                                    } else if (textUserRole.getText().toString().equals("Surgical Team")) {
                                        Intent b = new Intent(RegisterPatientManually.this, DashboardSurgeon.class);
                                        Toast.makeText(RegisterPatientManually.this, "Patient successfully register!", Toast.LENGTH_SHORT).show();
                                        b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(b);
                                        progressDialog.dismiss();
                                    }

                                } else {
                                    Toast.makeText(RegisterPatientManually.this, response, Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterPatientManually.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("MRN", MRN);
                        params.put("Name", Name);
                        params.put("Age", Age);
                        params.put("Gender", Gender);
                        params.put("Nationality", Nationality);
                        params.put("Race", Race);
                        params.put("Country", Country);
                        params.put("Ward", Ward);
                        params.put("SurgeonName", SurgeonName);
                        params.put("DiagnosisDescription", DiagnosisDescription);
                        params.put("ProcedureName1", ProcedureName1 );
                        params.put("ProcedureName2",ProcedureName2 );
                        params.put("ProcedureName3", ProcedureName3 );
                        params.put("SubSpecialty", SubSpecialty);
                        params.put("LastMeal",LastMeal);

                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(RegisterPatientManually.this);
                requestQueue.add(request);

            }
       }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }


    //method for radio button
    public void onRadioButtonClicked(View view) {

        String Nationality;

        if (checkMalaysian.isChecked()) {
            textRaceList.setVisibility(View.VISIBLE);
            textViewRaceList.setVisibility(View.VISIBLE);

            textCountry.setVisibility(View.GONE);
            textViewCountry.setVisibility(View.GONE);

            Nationality = checkMalaysian.getText().toString();
            textNation.setText(Nationality);


        }
        else if (checkNonMalaysian.isChecked()) {
            textCountry.setVisibility(View.VISIBLE);
            textViewCountry.setVisibility(View.VISIBLE);
            textRaceList.setVisibility(View.GONE);
            textViewRaceList.setVisibility(View.GONE);

            Nationality = checkNonMalaysian.getText().toString();
            textNation.setText(Nationality);


        }
        else {
            Toast.makeText(this, "Please select the nationality", Toast.LENGTH_SHORT).show();
        }
    }


    public void RemoveProcedureName2(View view) {
        edProcedureName2.setVisibility(View.GONE);
        image_remove1.setVisibility(View.GONE);
        buttonAddProcedure.setVisibility(View.VISIBLE);
    }


    public void RemoveProcedureName3(View view) {
        edProcedureName3.setVisibility(View.GONE);
        image_remove2.setVisibility(View.GONE);
        buttonAddProcedure.setVisibility(View.VISIBLE);
    }


    public void AddProcedure(View view) {


        edProcedureName2.setVisibility(View.VISIBLE);
        image_remove1.setVisibility(View.VISIBLE);
        edProcedureName3.setVisibility(View.VISIBLE);
        image_remove2.setVisibility(View.VISIBLE);
        buttonAddProcedure.setVisibility(View.GONE);
    }

}