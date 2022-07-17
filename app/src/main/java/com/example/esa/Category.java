package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.esa.Constants.TOPIC;

public class Category extends AppCompatActivity {

    TextView tvID, textViewPatientIDText, textViewPatientNameText,  tvPatientName ,tvBookSurgeryTime;
    TextView tvPatientID,  tvAnaesthetistName, tvArrivalTime, tvCategory, tvViewQuestion, textViewAnaesthetistid;

    RadioGroup radioGroup;
    RadioButton num1,num2,num3,num4;

    Button buttonCancelCategory, buttonSaveCategory;

    CheckBox edPACU, edICU, edHighDependencyArea,edNone;
    TextView edAnswer;
    ArrayList<String> mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().hide();

        //get topic for notification
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        //declare variables
        tvID = findViewById(R.id.textID);
        tvPatientID = findViewById(R.id.textViewPatientID);
        tvPatientName = findViewById(R.id.textViewPatientName);
        textViewAnaesthetistid = findViewById(R.id.textViewAnaesthetistid);
        tvAnaesthetistName = findViewById(R.id.textViewAnaesthetistName);
        textViewPatientIDText = findViewById(R.id.textViewPatientIDText);
        textViewPatientNameText = findViewById(R.id.textViewPatientNameText);
        tvBookSurgeryTime = findViewById(R.id.textBookSurgeryTime);
        edPACU = findViewById(R.id.check_PACU);
        edICU = findViewById(R.id.check_ICU);
        edHighDependencyArea= findViewById(R.id.check_HighDependencyArea);
        edNone = findViewById(R.id.check_None);
        edAnswer = findViewById(R.id.ed_Answer);

        radioGroup = findViewById(R.id.radio_group);
        num1 = (RadioButton) findViewById(R.id.q1);
        num2 = (RadioButton) findViewById(R.id.q2);
        num3 = (RadioButton) findViewById(R.id.q3);
        num4 = (RadioButton) findViewById(R.id.q4);
        tvCategory = findViewById(R.id.category);

        buttonCancelCategory = findViewById(R.id.buttonCancelCategory);
        buttonSaveCategory = findViewById(R.id.buttonSaveCategory);
        tvArrivalTime = findViewById(R.id.textArrivalTime);
        tvViewQuestion= findViewById(R.id.textViewQuestion);


        //get patient data
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String MRN = intent.getStringExtra("MRN");
        String PatientName = intent.getStringExtra("PatientName");

        //set text and call intent
        tvID.setText(id);
        tvPatientID.setText("MRN : " + MRN);
        tvPatientName.setText("Name : " + PatientName);
        textViewPatientIDText.setText(MRN);
        textViewPatientNameText.setText(PatientName);
        textViewAnaesthetistid.setText(SharedPrefManager.getInstance(this).getUsername());
        tvAnaesthetistName.setText(SharedPrefManager.getInstance(this).getUserFullname());

        //set for PACU, ICU and high dependency area
        mResult = new ArrayList<>();
        edAnswer.setEnabled(false);


        /**
         edPACU.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        if (edPACU.isChecked()) {
        mResult.add("PACU");

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : mResult)
        stringBuilder.append(s).append("\n");

        edAnswer.setText(stringBuilder.toString());
        edAnswer.setEnabled(false);
        }
        else {
        mResult.remove("PACU");

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : mResult)
        stringBuilder.append(s).append("\n");

        edAnswer.setText(stringBuilder.toString());
        edAnswer.setEnabled(false);
        }
        }
        });
         edICU.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        if (edICU.isChecked()) {
        mResult.add("ICU");

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : mResult)
        stringBuilder.append(s).append("\n");

        edAnswer.setText(stringBuilder.toString());
        edAnswer.setEnabled(false);
        }
        else {
        mResult.remove("ICU");

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : mResult)
        stringBuilder.append(s).append("\n");

        edAnswer.setText(stringBuilder.toString());
        edAnswer.setEnabled(false);
        }
        }
        });
         edHighDependencyArea.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        if (edHighDependencyArea.isChecked()) {
        mResult.add("High Dependency Area");

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : mResult)
        stringBuilder.append(s).append("\n");

        edAnswer.setText(stringBuilder.toString());
        edAnswer.setEnabled(false);
        }
        else {
        mResult.remove("High Dependency Area");

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : mResult)
        stringBuilder.append(s).append("\n");

        edAnswer.setText(stringBuilder.toString());
        edAnswer.setEnabled(false);
        }
        }
        });
         **/


        //user can only choose one only
        edPACU.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mResult.add("PACU");

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s : mResult)
                        stringBuilder.append(s);

                    edAnswer.setText(stringBuilder.toString());
                    edAnswer.setEnabled(false);

                    edICU.setChecked(false);
                    edHighDependencyArea.setChecked(false);
                    edNone.setChecked(false);

                }else {
                    mResult.remove("PACU");

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s : mResult)
                        stringBuilder.append(s);

                    edAnswer.setText(stringBuilder.toString());
                    edAnswer.setEnabled(false);

                }
            }
        });


        //user can only choose one only
        edICU.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mResult.add("ICU");

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s : mResult)
                        stringBuilder.append(s);

                    edAnswer.setText(stringBuilder.toString());
                    edAnswer.setEnabled(false);

                    edPACU.setChecked(false);
                    edHighDependencyArea.setChecked(false);
                    edNone.setChecked(false);

                }else {
                    mResult.remove("ICU");

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s : mResult)
                        stringBuilder.append(s);

                    edAnswer.setText(stringBuilder.toString());
                    edAnswer.setEnabled(false);

                }
            }
        });


        //user can only choose one only
        edHighDependencyArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mResult.add("High Dependency Area");

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s : mResult)
                        stringBuilder.append(s);

                    edAnswer.setText(stringBuilder.toString());
                    edAnswer.setEnabled(false);

                    edPACU.setChecked(false);
                    edICU.setChecked(false);
                    edNone.setChecked(false);

                }else {
                    mResult.remove("High Dependency Area");

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s : mResult)
                        stringBuilder.append(s);

                    edAnswer.setText(stringBuilder.toString());
                    edAnswer.setEnabled(false);

                }
            }
        });


        //user can only choose one only
        edNone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mResult.add("None");

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s : mResult)
                        stringBuilder.append(s);

                    edAnswer.setText(stringBuilder.toString());
                    edAnswer.setEnabled(false);

                    edPACU.setChecked(false);
                    edICU.setChecked(false);
                    edHighDependencyArea.setChecked(false);

                }else {
                    mResult.remove("None");

                    StringBuilder stringBuilder = new StringBuilder();
                    for (String s : mResult)
                        stringBuilder.append(s);

                    edAnswer.setText(stringBuilder.toString());
                    edAnswer.setEnabled(false);

                }
            }
        });


        //cancel button
        buttonCancelCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // updateData();

                Intent i = new Intent(getApplicationContext(),PatientListAnes.class);
                updateData();
                startActivity(i);
            }
        });


        //save button
        buttonSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();

            }
        });



    }

    //method update status to pending when user click cancel button
    private void updateData() {

        final String id = tvID.getText().toString();
        final String category_status = "Pending";

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updateCategoryStatus.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Category.this, response, Toast.LENGTH_SHORT).show();
                        //Intent i = new Intent(Category.this,Dashboard.class);
                        getIntent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        //startActivity(i);
                        progressDialog.dismiss();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Category.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(Category.this);
        requestQueue.add(request);

    }


    //method save data into database
    private void insertData() {

        final String MRN = textViewPatientIDText.getText().toString().trim().toUpperCase();
        final String PatientName = textViewPatientNameText.getText().toString().trim().toUpperCase();
        final String AnesthetistID = textViewAnaesthetistid.getText().toString().trim();
        final String AnesthetistName = tvAnaesthetistName.getText().toString().trim();
        final String ClinicalDescriptor = tvViewQuestion.getText().toString().trim();
        final String Category = tvCategory.getText().toString().trim().toUpperCase();
        final String ArrivalTimeToSurgeon = "";
        final String Needed_by_patient = edAnswer.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if (Category.equals("") || Category.equals("?")) {
            Toast.makeText(this, "Please select the clinical descriptor", Toast.LENGTH_SHORT).show();
        }
        else if (Needed_by_patient.equals("")) {
            Toast.makeText(this, "Please select the required requirements for the patient", Toast.LENGTH_SHORT).show();
        }
        else{

            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "insertCategory.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("Data Inserted")) {

                                //for notification
                                final String titletxt = "Patient have been categorized!";
                                final String msgtxt = "Patient: " + textViewPatientNameText.getText().toString().trim();

                                Intent i = new Intent(Category.this, Dashboard.class);

                                Toast.makeText(Category.this, "Patient have been categorized!", Toast.LENGTH_SHORT).show();
                                PushNotification notification = new PushNotification(new NotificationData(titletxt, msgtxt), TOPIC);

                                sendNotification(notification);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                progressDialog.dismiss();


                            } else {
                                Toast.makeText(Category.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Category.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("MRN", MRN);
                    params.put("PatientName", PatientName);
                    params.put("AnesthetistID", AnesthetistID);
                    params.put("AnesthetistName", AnesthetistName);
                    params.put("ClinicalDescriptor", ClinicalDescriptor);
                    params.put("Category",Category);
                    params.put("Needed_by_patient",Needed_by_patient);
                    params.put("ArrivalTimeToSurgeon",ArrivalTimeToSurgeon);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(Category.this);
            requestQueue.add(request);
        }

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
                    Toast.makeText(Category.this,"error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(Category.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    //method for radio button
    public void onRadioButtonClicked(View view) {

        String Question;

        if (num1.isChecked()) {
            tvCategory.setText("A");
            Question = num1.getText().toString();
            Toast.makeText(this, "This patient is category A", Toast.LENGTH_SHORT).show();
            tvViewQuestion.setText(Question);

        }
        else if (num2.isChecked()) {
            tvCategory.setText("B");
            Question = num2.getText().toString();
            Toast.makeText(this, "This patient is category B", Toast.LENGTH_SHORT).show();
            tvViewQuestion.setText(Question);

        }
        else if (num3.isChecked()) {
            tvCategory.setText("C");
            Question = num3.getText().toString();
            Toast.makeText(this, "This patient is category C", Toast.LENGTH_SHORT).show();
            tvViewQuestion.setText(Question);

        }
        else if (num4.isChecked()) {
            tvCategory.setText("D");
            Question = num4.getText().toString();
            Toast.makeText(this, "This patient is category D", Toast.LENGTH_SHORT).show();
            tvViewQuestion.setText(Question);

        }
        else {
            Toast.makeText(this, "Please select the clinical descriptor", Toast.LENGTH_SHORT).show();
        }

    }
}

