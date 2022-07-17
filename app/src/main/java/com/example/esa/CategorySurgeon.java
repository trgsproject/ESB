package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.esa.Constants.TOPIC;

public class CategorySurgeon extends AppCompatActivity {

    TextView tvID, textViewPatientIDText, textViewPatientNameText,  tvPatientName ,tvBookSurgeryTime;
    TextView tvPatientID,  tvAnaesthetistName, tvArrivalTime, Category, tvViewQuestion;

    RadioGroup radioGroup;
    RadioButton num1,num2,num3,num4;

    RadioButton radioButton;

    Button buttonCancelCategory, buttonSaveCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_surgeon);
        getSupportActionBar().hide();

        //get topic for notification
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        //declare variables
        tvID = findViewById(R.id.textID);
        tvPatientID = findViewById(R.id.textViewPatientID);
        tvPatientName = findViewById(R.id.textViewPatientName);
        tvAnaesthetistName = findViewById(R.id.textViewAnaesthetistName);
        textViewPatientIDText = findViewById(R.id.textViewPatientIDText);
        textViewPatientNameText = findViewById(R.id.textViewPatientNameText);
        tvBookSurgeryTime = findViewById(R.id.textBookSurgeryTime);

        radioGroup = findViewById(R.id.radio_group);
        num1 = (RadioButton) findViewById(R.id.q1);
        num2 = (RadioButton) findViewById(R.id.q2);
        num3 = (RadioButton) findViewById(R.id.q3);
        num4 = (RadioButton) findViewById(R.id.q4);
        Category = findViewById(R.id.category);

        buttonCancelCategory = findViewById(R.id.buttonCancelCategory);
        buttonSaveCategory = findViewById(R.id.buttonSaveCategory);
        tvArrivalTime = findViewById(R.id.textArrivalTime);
        tvViewQuestion= findViewById(R.id.textViewQuestion);


        //get patient data
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String patientID = intent.getStringExtra("patientID");
        String patientName = intent.getStringExtra("patientName");
        String surgeryTime = intent.getStringExtra("surgeryTime");

        //set text and call intent
        tvID.setText(id);
        tvPatientID.setText("MRN : " + patientID);
        tvPatientName.setText("Name : " + patientName);
        textViewPatientIDText.setText(patientID);
        textViewPatientNameText.setText(patientName);
        tvAnaesthetistName.setText(SharedPrefManager.getInstance(this).getUserFullname());
        tvBookSurgeryTime.setText(surgeryTime);

        //cancel button
        buttonCancelCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
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

                        Toast.makeText(CategorySurgeon.this, response, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(CategorySurgeon.this,DashboardSurgeon.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(CategorySurgeon.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(CategorySurgeon.this);
        requestQueue.add(request);

    }


    //method save data into database
    private void insertData() {


        final String mrn = textViewPatientIDText.getText().toString().trim().toUpperCase();
        final String patient_name = textViewPatientNameText.getText().toString().trim().toUpperCase();
        final String anaesthetist_name = tvAnaesthetistName.getText().toString().trim();
        final String question = tvViewQuestion.getText().toString().trim();
        final String category = Category.getText().toString().trim().toUpperCase();
        final String bookSurgeryTime = tvBookSurgeryTime.getText().toString().trim().toUpperCase();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if (category.equals("") || category.equals("?")) {
            Toast.makeText(this, "Please select the clinical descriptor!", Toast.LENGTH_SHORT).show();
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
                                final String msgtxt = "Patient: " + tvPatientName.getText().toString().trim();

                                Intent i = new Intent(CategorySurgeon.this, DashboardSurgeon.class);

                                Toast.makeText(CategorySurgeon.this, "Patient have been categorized!", Toast.LENGTH_SHORT).show();
                                PushNotification notification = new PushNotification(new NotificationData(titletxt, msgtxt), TOPIC);

                                sendNotification(notification);

                                startActivity(i);
                                progressDialog.dismiss();


                            } else {
                                Toast.makeText(CategorySurgeon.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CategorySurgeon.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("mrn", mrn);
                    params.put("patient_name", patient_name);
                    params.put("anaesthetist_name", anaesthetist_name);
                    params.put("question", question);
                    params.put("category", category);
                    params.put("bookSurgeryTime",bookSurgeryTime);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(CategorySurgeon.this);
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
                    Toast.makeText(CategorySurgeon.this,"error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(CategorySurgeon.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    //method for radio button
    public void onRadioButtonClicked(View view) {

        String Question;

        if (num1.isChecked()) {
            Category.setText("A");
            Question = num1.getText().toString();

            tvViewQuestion.setText(Question);

        }
        else if (num2.isChecked()) {
            Category.setText("B");
            Question = num2.getText().toString();

            tvViewQuestion.setText(Question);

        }
        else if (num3.isChecked()) {
            Category.setText("C");
            Question = num3.getText().toString();

            tvViewQuestion.setText(Question);

        }
        else if (num4.isChecked()) {
            Category.setText("D");
            Question = num4.getText().toString();

            tvViewQuestion.setText(Question);

        }
        else {
            Toast.makeText(this, "Please select the clinical descriptor!", Toast.LENGTH_SHORT).show();
        }

    }
}

