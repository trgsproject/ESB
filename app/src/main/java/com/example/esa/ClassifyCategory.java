package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.esa.Constants.TOPIC;

public class ClassifyCategory extends AppCompatActivity {

    TextView tvPatientName, tvPatientID,  tvAnaesthetistName, tvQuestion, tvArrivalTime;
    TextView Q1, Q2, Q3, Q4,  textViewPatientIDText, textViewPatientNameText,tvID;

    EditText numA, numB, numC, numD;
    Button buttonCancelCategory, buttonSaveCategory;

    AutoCompleteTextView categoryText;
    TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_category);
        getSupportActionBar().hide();

        //get topic for notification
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        //get String
        String[] Category = getResources().getStringArray(R.array.Category);

        //declare variables
        tvID = findViewById(R.id.textID);
        tvPatientID = findViewById(R.id.textViewPatientID);
        tvPatientName = findViewById(R.id.textViewPatientName);
        tvAnaesthetistName = findViewById(R.id.textViewAnaesthetistName);
        textViewPatientIDText = findViewById(R.id.textViewPatientIDText);
        textViewPatientNameText = findViewById(R.id.textViewPatientNameText);
        numA = findViewById(R.id.num1);
        numB = findViewById(R.id.num2);
        numC = findViewById(R.id.num3);
        numD = findViewById(R.id.num4);
        scoreText = findViewById(R.id.score);
        categoryText = findViewById(R.id.category);
        tvQuestion = findViewById(R.id.textViewQuestion);
        Q1 = findViewById(R.id.question1);
        Q2 = findViewById(R.id.question2);
        Q3 = findViewById(R.id.question3);
        Q4 = findViewById(R.id.question4);
        buttonCancelCategory = findViewById(R.id.buttonCancelCategory);
        buttonSaveCategory = findViewById(R.id.buttonSaveCategory);
        tvArrivalTime = findViewById(R.id.textArrivalTime);

        //get patient data
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String patientID = intent.getStringExtra("patientID");
        String patientName = intent.getStringExtra("patientName");
        String arrivalTime = intent.getStringExtra("arrival_time");

        //set text and call intent
        tvID.setText(id);
        tvPatientID.setText("MRN : " + patientID);
        tvPatientName.setText("Name : " + patientName);
        textViewPatientIDText.setText(patientID);
        textViewPatientNameText.setText(patientName);
        tvAnaesthetistName.setText(SharedPrefManager.getInstance(this).getUserFullname());
        tvArrivalTime.setText(arrivalTime);


        //set category
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,Category);
        categoryText.setAdapter(adapter);


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

                //if input not null
                if (!numA.getText().toString().equals("") && !numB.getText().toString().equals("") && !numC.getText().toString().equals("")
                        && !numD.getText().toString().equals("") ) {

                    if (!validateLengthScoreA() | !validateLengthScoreB() | !validateLengthScoreC() | !validateLengthScoreD() ) {
                        Toast.makeText(ClassifyCategory.this, "Score must be less than equal 100!", Toast.LENGTH_SHORT).show();
                    }
                    else {

                        insertData();

                    }
                }
                else {
                    Toast.makeText(ClassifyCategory.this, "Please fill in each field!", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //text watcher for auto calculation (auto generated highest value and category)
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //if input not null
                if (!numA.getText().toString().equals("") && !numB.getText().toString().equals("") && !numC.getText().toString().equals("")
                        && !numD.getText().toString().equals("") ) {


                    int temp1 = Integer.parseInt(numA.getText().toString());
                    int temp2 = Integer.parseInt(numB.getText().toString());
                    int temp3 = Integer.parseInt(numC.getText().toString());
                    int temp4 = Integer.parseInt(numD.getText().toString());

                    int[] decMax = {0, temp1, temp2, temp3, temp4};

                    int maxx = decMax[0];

                    for (int i = 0; i < decMax.length; i++) {
                        if (maxx < decMax[i]) {
                            maxx = decMax[i];

                            if (maxx == temp1) {

                                scoreText.setText(String.valueOf( (maxx)));
                                categoryText.setText("A");
                                tvQuestion.setText(Q1.getText().toString());


                            } else if (maxx == temp2) {

                                scoreText.setText(String.valueOf( (maxx)));
                                categoryText.setText("B");
                                tvQuestion.setText(Q2.getText().toString());

                            } else if (maxx == temp3) {

                                scoreText.setText(String.valueOf( (maxx)));
                                categoryText.setText("C");
                                tvQuestion.setText(Q3.getText().toString());

                            } else if (maxx == temp4) {

                                scoreText.setText(String.valueOf( (maxx)));
                                categoryText.setText("D");
                                tvQuestion.setText(Q4.getText().toString());

                            }
                        }
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        numA.addTextChangedListener(textWatcher);
        numB.addTextChangedListener(textWatcher);
        numC.addTextChangedListener(textWatcher);
        numD.addTextChangedListener(textWatcher);


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
                    Toast.makeText(ClassifyCategory.this,"error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(ClassifyCategory.this,t.getMessage(), Toast.LENGTH_SHORT).show();
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

                        Toast.makeText(ClassifyCategory.this, response, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ClassifyCategory.this,Dashboard.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ClassifyCategory.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(ClassifyCategory.this);
        requestQueue.add(request);

    }


    //method save data into database
    private void insertData() {

        final String mrn = textViewPatientIDText.getText().toString().trim().toUpperCase();
        final String patient_name = textViewPatientNameText.getText().toString().trim().toUpperCase();
        final String anaesthetist_name = tvAnaesthetistName.getText().toString().trim();
        final String question = tvQuestion.getText().toString().trim();
        final String category = categoryText.getText().toString().trim().toUpperCase();
        final String score = scoreText.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if (score.equals("") && category.equals("")){
            Toast.makeText(this, "Please enter the score!", Toast.LENGTH_SHORT).show();
            return;

        }
        else {

            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "insertCategory.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("Data Inserted")) {

                                //for notification
                                final String titletxt = "Patient to be approved!";
                                final String msgtxt = "Patient: " + tvPatientName.getText().toString().trim();

                                Intent i = new Intent(ClassifyCategory.this, Dashboard.class);

                                Toast.makeText(ClassifyCategory.this, "Patient have been categorized!", Toast.LENGTH_SHORT).show();
                                PushNotification notification = new PushNotification(new NotificationData(titletxt, msgtxt), TOPIC);

                                sendNotification(notification);

                                startActivity(i);
                                progressDialog.dismiss();


                            } else {
                                Toast.makeText(ClassifyCategory.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ClassifyCategory.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    params.put("score", score);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(ClassifyCategory.this);
            requestQueue.add(request);
        }

    }


    //validation for input A
    private boolean validateLengthScoreA() {
        int val = Integer.parseInt(numA.getText().toString());

        if (val > 100) {
            numA.setError("Score must be less than equal 100!");
            return false;
        }
        else{
            numA.setError(null);
            return true;
        }
    }


    //validation for input B
    private boolean validateLengthScoreB() {
        int val = Integer.parseInt(numB.getText().toString());

        if(val > 100  ){
            numB.setError("Score must be less than equal 100!");
            return false;
        }
        else{
            numB.setError(null);
            return true;
        }
    }


    //validation for input C
    private boolean validateLengthScoreC() {
        int val = Integer.parseInt(numC.getText().toString());

        if(val > 100  ){
            numC.setError("Score must be less than equal 100!");
            return false;
        }
        else{
            numC.setError(null);
            return true;
        }
    }


    //validation for input D
    private boolean validateLengthScoreD() {
        int val = Integer.parseInt(numD.getText().toString());

        if(val > 100  ){
            numD.setError("Score must be less than equal 100!");
            return false;
        }
        else{
            numD.setError(null);
            return true;
        }
    }




}