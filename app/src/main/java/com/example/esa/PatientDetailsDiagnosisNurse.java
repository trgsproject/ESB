package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.esa.api.ApiUtilities;
import com.example.esa.model.NotificationData;
import com.example.esa.model.PushNotification;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.esa.Constants.TOPIC;

public class PatientDetailsDiagnosisNurse extends AppCompatActivity {

    int position;

    TextView tvID, tvMRN, tvname, tvDiagnosisDesc, tvProcedureName, tvSurgeryTime,tvSurgicalTeam;
    TextView tvSurgeryDuration, tvSubSpeciality, tvLastMeal, tvHighDependencyArea , txtInformedAnaesthetist, txtAnaesthetistOnCall;

    TextView tvArrivalTimeToSurgeon, tvTimeApproval;
    ImageView buttonBack;

    TextView userRole, textViewProcedureName2, textViewProcedureName3, tvProcedureName2, tvProcedureName3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_diagnosis_nurse);
        getSupportActionBar().hide();

        //declare variables
        tvID = findViewById(R.id.txtid);
        tvMRN = findViewById(R.id.txtMRN);
        tvname = findViewById(R.id.txtname);
        tvDiagnosisDesc = findViewById(R.id.txtDiagnosisDescription);
        tvProcedureName = findViewById(R.id.txtProcedureName);
        //tvSurgeryDuration = findViewById(R.id. txtSurgeryDuration);
        tvTimeApproval = findViewById(R.id.txtTimeApproval);
        //tvSurgeryTime = findViewById(R.id.txtSurgeryTime);
        buttonBack = findViewById(R.id.buttonBack);
        tvSurgicalTeam = findViewById(R.id.txtSurgicalTeam);
        tvSubSpeciality = findViewById(R.id.txtSubSpeciality);
        tvLastMeal = findViewById(R.id.txtLastMeal);
        tvArrivalTimeToSurgeon = findViewById(R.id.txtArrivalTimeToSurgeon);
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        userRole = findViewById(R.id.userRole);
        textViewProcedureName2 = findViewById(R.id.textViewProcedureName2);
        textViewProcedureName3 = findViewById(R.id.textViewProcedureName3);
        tvProcedureName2 = findViewById(R.id.txtProcedureName2);
        tvProcedureName3 = findViewById(R.id.txtProcedureName3);
        tvHighDependencyArea = findViewById(R.id.txtHighDependencyArea);
        txtInformedAnaesthetist = findViewById(R.id.txtInformedAnaesthetist);
        txtAnaesthetistOnCall = findViewById(R.id.txtAnaesthetistOnCall);

        //get patient data
        Intent intent =getIntent();
        position = intent.getExtras().getInt("position");
        tvMRN.setText(DiagnosisList.diagnosisArrayList.get(position).getMrn());
        tvname.setText(DiagnosisList.diagnosisArrayList.get(position).getName());
        tvDiagnosisDesc.setText(DiagnosisList.diagnosisArrayList.get(position).getName_diagnosis_desc());
        tvProcedureName.setText(DiagnosisList.diagnosisArrayList.get(position).getProcedure_name());
        //tvSurgeryDuration.setText(DiagnosisList.diagnosisArrayList.get(position).getSurgery_duration());
        //tvSurgeryTime.setText(DiagnosisList.diagnosisArrayList.get(position).getSurgeryTime());
        tvTimeApproval.setText(DiagnosisList.diagnosisArrayList.get(position).getTime_approval());
        tvSurgicalTeam.setText(DiagnosisList.diagnosisArrayList.get(position).getSurgical_team());
        tvLastMeal.setText(DiagnosisList.diagnosisArrayList.get(position).getLast_meal());
        tvSubSpeciality.setText(DiagnosisList.diagnosisArrayList.get(position).getSub_specialty());
        tvArrivalTimeToSurgeon.setText(DiagnosisList.diagnosisArrayList.get(position).getArrival_time_to_surgeon());
        userRole.setText(SharedPrefManager.getInstance(this).getUserRole());
        tvProcedureName2.setText(DiagnosisList.diagnosisArrayList.get(position).getProcedure_name_2());
        tvProcedureName3.setText(DiagnosisList.diagnosisArrayList.get(position).getProcedure_name_3());
        tvHighDependencyArea.setText(DiagnosisList.diagnosisArrayList.get(position).getHigh_dependency_area());
        txtInformedAnaesthetist.setText(DiagnosisList.diagnosisArrayList.get(position).getInform_anaesthetist());
        txtAnaesthetistOnCall.setText(DiagnosisList.diagnosisArrayList.get(position).getOn_call());

        //check if procedure 2 and 3 exist
        String ProcedureCheck2 = tvProcedureName2.getText().toString().trim();

        if (ProcedureCheck2.equals("-")){
            tvProcedureName2.setVisibility(View.GONE);
            textViewProcedureName2.setVisibility(View.GONE);
        }



        String ProcedureCheck3 = tvProcedureName3.getText().toString().trim();

        if(ProcedureCheck3.equals("-")){
            textViewProcedureName3.setVisibility(View.GONE);
            tvProcedureName3.setVisibility(View.GONE);

        }


        //back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientDetailsDiagnosisNurse.this,DiagnosisList.class);
                startActivity(i);
            }
        });


        //bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        return true;

                    case R.id.register:
                        startActivity(new Intent(getApplicationContext(),RegisterPatientNurse.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(),SettingNurse.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    public void btn_AlertAnaesthetist(View view) {

        final String titletxt = "Please alert! Patient need to be categorized immediately!";
        final String msgtxt = "Patient: " + tvname.getText().toString().trim();

        final String role = userRole.getText().toString().trim();

        if (!titletxt.isEmpty() && !msgtxt.isEmpty()) {
            PushNotification notification = new PushNotification(new NotificationData(titletxt, msgtxt), TOPIC);
            sendNotification(notification);
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
                    Toast.makeText(PatientDetailsDiagnosisNurse.this,"error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(PatientDetailsDiagnosisNurse.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}