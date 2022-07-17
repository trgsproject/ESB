package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.esa.Constants.TOPIC;

public class RegisterSchedule extends AppCompatActivity {

    int t1hour,t1Minute;

    EditText textMRN, textName, textCategory, textSurgeryDate, textCallTime, textArrivalTimeToOT;
    EditText textSurgeryStartTime, textSurgeryEndTime, textPHUStartTime, textPACUStartTime;
    EditText textPHUIDBed, textPACUIDBed;

    Spinner textOT;

    TextView textID, textStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_schedule);

        //get topic for notification
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        //defines variables
        textID = findViewById(R.id.textID);
        textMRN = findViewById(R.id.textMRN);
        textName = findViewById(R.id.textName);
        textCategory = findViewById(R.id.textCategory);
        textSurgeryDate = findViewById(R.id.textSurgeryDate);
        textCallTime = findViewById(R.id.textCallTime);
        textArrivalTimeToOT = findViewById(R.id.textArrivalTimeToOT);
        textOT = findViewById(R.id.textOT);
        textSurgeryStartTime = findViewById(R.id.textSurgeryStartTime);
        textSurgeryEndTime = findViewById(R.id.textSurgeryEndTime);
        textPHUStartTime = findViewById(R.id.textPHUStartTime);
        textPACUStartTime = findViewById(R.id.textPACUStartTime);
        textPHUIDBed = findViewById(R.id.textPHUIDBed);
        textPACUIDBed = findViewById(R.id.textPACUIDBed);
        textStatus = findViewById(R.id.textStatus);

        //get patient data
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String mrn = intent.getStringExtra("mrn");
        String patientName = intent.getStringExtra("patientName");
        String category = intent.getStringExtra("category");
        String schedule_status = intent.getStringExtra("schedule_status");

        //set text
        textID.setText(id);
        textMRN.setText(mrn);
        textName.setText(patientName);
        textCategory.setText(category);
        textSurgeryDate.setText(getCurrentDate());
        textStatus.setText(schedule_status);

        //set spinner for OT
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.ot, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        textOT.setAdapter(adapter1);

        //pick call time from anaes to nurse
        textCallTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        t1hour =hourOfDay;
                        t1Minute=minute;

                        Calendar datetime = Calendar.getInstance();

                        datetime.set(0,0,0,t1hour,t1Minute);

                        //edTime.setText(DateFormat.format("hh:mm aaa", datetime));
                        textCallTime.setText(DateFormat.format("HH:mm:00", datetime));
                    }
                },12,0,false);
                timePickerDialog.updateTime(t1hour,t1Minute);
                timePickerDialog.show();
            }
        });

        //pick arrival time to OT
        textArrivalTimeToOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        t1hour =hourOfDay;
                        t1Minute=minute;

                        Calendar datetime = Calendar.getInstance();

                        datetime.set(0,0,0,t1hour,t1Minute);

                        //edTime.setText(DateFormat.format("hh:mm aaa", datetime));
                        textArrivalTimeToOT.setText(DateFormat.format("HH:mm:00", datetime));
                    }
                },12,0,false);
                timePickerDialog.updateTime(t1hour,t1Minute);
                timePickerDialog.show();
            }
        });

        //pick surgery start time
        textSurgeryStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        t1hour =hourOfDay;
                        t1Minute=minute;

                        Calendar datetime = Calendar.getInstance();

                        datetime.set(0,0,0,t1hour,t1Minute);

                        textSurgeryStartTime.setText(DateFormat.format("HH:mm:00", datetime));

                    }
                },12,0,false);
                timePickerDialog.updateTime(t1hour,t1Minute);
                timePickerDialog.show();
            }
        });

        //pick surgery end time
        textSurgeryEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        t1hour =hourOfDay;
                        t1Minute=minute;

                        Calendar datetime = Calendar.getInstance();

                        datetime.set(0,0,0,t1hour,t1Minute);

                        textSurgeryEndTime.setText(DateFormat.format("HH:mm:00", datetime));

                    }
                },12,0,false);
                timePickerDialog.updateTime(t1hour,t1Minute);
                timePickerDialog.show();
            }
        });

        //pick PHU time
        textPHUStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        t1hour =hourOfDay;
                        t1Minute=minute;

                        Calendar datetime = Calendar.getInstance();

                        datetime.set(0,0,0,t1hour,t1Minute);

                        //edTime.setText(DateFormat.format("hh:mm aaa", datetime));
                        textPHUStartTime.setText(DateFormat.format("HH:mm:00", datetime));
                    }
                },12,0,false);
                timePickerDialog.updateTime(t1hour,t1Minute);
                timePickerDialog.show();
            }
        });

        //pick PACU time
        textPACUStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterSchedule.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        t1hour =hourOfDay;
                        t1Minute=minute;

                        Calendar datetime = Calendar.getInstance();

                        datetime.set(0,0,0,t1hour,t1Minute);

                        //edTime.setText(DateFormat.format("hh:mm aaa", datetime));
                        textPACUStartTime.setText(DateFormat.format("HH:mm:00", datetime));
                    }
                },12,0,false);
                timePickerDialog.updateTime(t1hour,t1Minute);
                timePickerDialog.show();
            }
        });


    }

    //method to get current date
    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-LL-dd", Locale.getDefault()).format(new Date());
    }

    //cancel method button
    public void Cancel(View view) {

        final String id = textID.getText().toString();
        final String Status = "Waiting";

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updateScore.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(RegisterSchedule.this, response, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(),WaitingPatientListNurse.class);

                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RegisterSchedule.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("id", id);
                params.put("Status",Status);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(RegisterSchedule.this);
        requestQueue.add(request);

    }

    //save method Button
    public void Save(View view) {


        final String MRN = textMRN.getText().toString().trim().toUpperCase();
        final String PatientName = textName.getText().toString().trim().toUpperCase();
        final String Category = textCategory.getText().toString().trim().toUpperCase();
        final String SurgeryDate = textSurgeryDate.getText().toString().trim();
        final String OT = textOT.getSelectedItem().toString().trim().toUpperCase();
        final String CallTime = textCallTime.getText().toString().trim();
        final String ArrivalTimeToOT = textArrivalTimeToOT.getText().toString().trim();
        final String SurgeryStartTime = textSurgeryStartTime.getText().toString().trim().toUpperCase();
        final String SurgeryEndTime = textSurgeryEndTime.getText().toString().trim().toUpperCase();
        final String PHUStartTime = textPHUStartTime.getText().toString().trim().toUpperCase();
        final String PHUidBed = textPHUIDBed.getText().toString().trim().toUpperCase();
        final String PACUStartTime = textPACUStartTime.getText().toString().trim().toUpperCase();
        final String PACUidBed = textPACUIDBed.getText().toString().trim().toUpperCase();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if (CallTime.equals("")) {
            Toast.makeText(this, "Please insert call time", Toast.LENGTH_SHORT).show();
        } else if (ArrivalTimeToOT.equals("")) {
            Toast.makeText(this, "Please insert arrival time", Toast.LENGTH_SHORT).show();
        } else if (OT.equals("-")) {
            Toast.makeText(this, "Please insert ot room", Toast.LENGTH_SHORT).show();
        } else if (SurgeryStartTime.equals("")) {
            Toast.makeText(this, "Please insert surgery start time", Toast.LENGTH_SHORT).show();
        } else if (SurgeryEndTime.equals("")) {
            Toast.makeText(this, "Please insert surgery end time", Toast.LENGTH_SHORT).show();
        }

        else {

            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "insertScheduleOT.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response.equalsIgnoreCase("Data Inserted")) {

                                //for notification
                                final String titletxt = "Schedule OT successfully register!";
                                final String msgtxt = "Patient: " + textName.getText().toString().trim();

                                Intent i = new Intent(RegisterSchedule.this, DashboardNurse.class);

                                Toast.makeText(RegisterSchedule.this, "Schedule OT successfully register!", Toast.LENGTH_SHORT).show();
                                PushNotification notification = new PushNotification(new NotificationData(titletxt, msgtxt), TOPIC);

                                sendNotification(notification);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                progressDialog.dismiss();


                            } else {
                                Toast.makeText(RegisterSchedule.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterSchedule.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("MRN", MRN);
                    params.put("PatientName", PatientName);
                    params.put("Category", Category);
                    params.put("SurgeryDate", SurgeryDate);
                    params.put("OT", OT);
                    params.put("CallTime", CallTime);
                    params.put("ArrivalTimeToOT", ArrivalTimeToOT);
                    params.put("SurgeryStartTime", SurgeryStartTime);
                    params.put("SurgeryEndTime", SurgeryEndTime);
                    params.put("PHUStartTime", PHUStartTime);
                    params.put("PHUidBed", PHUidBed);
                    params.put("PACUStartTime", PACUStartTime);
                    params.put("PACUidBed", PACUidBed);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterSchedule.this);
            requestQueue.add(request);
        }
    }

    //send notification
    private void sendNotification(PushNotification notification) {
        ApiUtilities.getClient().sendNotification(notification).enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, retrofit2.Response<PushNotification> response) {
                if (response.isSuccessful()) {
                    //Toast.makeText(RegisterPatientDetails.this,"success", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(RegisterSchedule.this, "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(RegisterSchedule.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
