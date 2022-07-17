package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

public class RegisterDiagnosis extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText textMRN, textName, textNurseID, textTimeArrival, textNameDiagnosisDesc, textBookSurgeryTime , textLastMealTime, textLastMealDate, textBookSurgeryDate;
    TextView textID, textApprovedStatus, buttonAddProcedure;

    AutoCompleteTextView textProcedureName, textBookOT, textSurgicalTeam, textSubSpecialty, edProcedureName2,edProcedureName3;
    Spinner textSurgeryDuration;

    Button btnCancel, btnSave;

    DatePickerDialog.OnDateSetListener setListener;
    int t1hour, t1Minute;

    ImageView image_remove1, image_remove2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_diagnosis);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));

        //get topic for notification
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC);

        //set string for autoComplete
        String[] procedureName = getResources().getStringArray(R.array.procedureName);
        String[] BookOT = getResources().getStringArray(R.array.ot);
        String[] SubSpecialty = getResources().getStringArray(R.array.SubSpeciality);
        //String[] SurgeonName = getResources().getStringArray(R.array.SurgeonName);

        //declare variables
        textID = findViewById(R.id.textID);
        textMRN = findViewById(R.id.textMRN);
        textName = findViewById(R.id.textName);
        textNurseID = findViewById(R.id.textNurseID);
        textTimeArrival = findViewById(R.id.textTimeArrival);
        textNameDiagnosisDesc = findViewById(R.id.textNameDiagnosisDesc);
        textProcedureName = findViewById(R.id.textProcedureName);
        textApprovedStatus = findViewById(R.id.textViewApprovedStatus);
        btnCancel = findViewById(R.id.buttonCancel);
        btnSave = findViewById(R.id.buttonSave);
        textSurgicalTeam = findViewById(R.id.textSurgicalTeam);
        textSubSpecialty = findViewById(R.id.textSubSpecialty);
        textLastMealDate = findViewById(R.id.textLastMealDate);
        textLastMealTime = findViewById(R.id.textLastMealTime);
        textBookSurgeryDate = findViewById(R.id.textBookSurgeryDate);
        edProcedureName2 = findViewById(R.id.ed_ProcedureName2);
        edProcedureName3 = findViewById(R.id.ed_ProcedureName3);
        buttonAddProcedure = findViewById(R.id.buttonAddProcedure);
        image_remove1 = findViewById(R.id.image_remove1);
        image_remove2 = findViewById(R.id.image_remove2);

        //get patient details from previous activity
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String mrn = intent.getStringExtra("mrn");
        String name = intent.getStringExtra("name");
        String nurseID = intent.getStringExtra("nurseID");
        String timeArrival = intent.getStringExtra("timeArrival");

        //set text and call intent from previous activity
        textID.setText(id);
        textName.setText(name);
        textMRN.setText(mrn);
        textNurseID.setText(nurseID);
        textTimeArrival.setText(timeArrival);
        textBookSurgeryDate.setText(getCurrentDate());

        //for autocomplete Procedure Name
        ArrayAdapter<String> adapter1a = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, procedureName);
        textProcedureName.setAdapter(adapter1a);

        //for autocomplete Procedure Name
        ArrayAdapter<String> adapter1b = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, procedureName);
        edProcedureName2.setAdapter(adapter1b);

        //for autocomplete Procedure Name
        ArrayAdapter<String> adapter1c = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, procedureName);
        edProcedureName3.setAdapter(adapter1c);


        //for autocomplete Ot room
        //ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, BookOT);
        //textBookOT.setAdapter(adapter2);


        //for autocomplete Sub Specialty
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, SubSpecialty);
        textSubSpecialty.setAdapter(adapter3);

        //set role of users
        //ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.surgeryDuration, android.R.layout.simple_spinner_item);
        //adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //textSurgeryDuration.setAdapter(adapter4);

       // ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,
               // android.R.layout.simple_list_item_1,SurgeonName);
        //textSurgicalTeam.setAdapter(adapter5);


        /**
        //pick booking OT time
        textBookSurgeryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterDiagnosis.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        t1hour =hourOfDay;
                        t1Minute=minute;

                        Calendar datetime = Calendar.getInstance();

                        datetime.set(0,0,0,t1hour,t1Minute);

                        //edTime.setText(DateFormat.format("hh:mm aaa", datetime));
                        textBookSurgeryTime.setText(DateFormat.format("HH:mm:00", datetime));
                    }
                },12,0,false);
                timePickerDialog.updateTime(t1hour,t1Minute);
                timePickerDialog.show();
            }
        });
**/

        //pick last meal time
        textLastMealTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(RegisterDiagnosis.this, new TimePickerDialog.OnTimeSetListener() {
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
        final DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterDiagnosis.this
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
                final String titletxt = "Patient to be approve!";
                final String msgtxt = "Patient: " + textName.getText().toString().trim();

                final String name_diagnosis_desc = textNameDiagnosisDesc.getText().toString().trim();
                final String procedure_name = textProcedureName.getText().toString().trim();
                //final String surgery_duration = textSurgeryDuration.getSelectedItem().toString().trim();
                final String surgical_team = textSurgicalTeam.getText().toString().toUpperCase().trim();
                final String sub_specialty = textSubSpecialty.getText().toString().trim();
                final String lastMealTime =  textLastMealTime.getText().toString().trim();
                final String lastMealDate =  textLastMealTime.getText().toString().trim();
                final String operationDate =  textBookSurgeryDate.getText().toString().trim();
                //final String operationTime =  textBookSurgeryTime.getText().toString().trim();

                if (!lastMealTime.isEmpty() && !lastMealDate.isEmpty() && !operationDate.isEmpty()
                        && !surgical_team.isEmpty() && !sub_specialty.isEmpty() &&
                        !procedure_name.isEmpty() && !name_diagnosis_desc.isEmpty()) {
                    PushNotification notification = new PushNotification(new NotificationData(titletxt, msgtxt), TOPIC);
                    sendNotification(notification);
                    insertData();
                }else {
                    Toast.makeText(RegisterDiagnosis.this, "Please insert the data", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //button cancel check in
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RegisterDiagnosis.this,PatientList.class);
                updateData();
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        //text watcher for auto calculation (auto generated sub specialty after user input the surgeon name)
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //if input not null
                if (!textSurgicalTeam.getText().toString().equals("") ) {

                    String surgeonName = textSurgicalTeam.getText().toString();

                    //switch get surgeon name
                    switch (surgeonName) {
                        //for OPHTHALMOLOGY only
                        case "JEMAIMA BTE CHE HAMZAH":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "MAE-LYNN CATHERINE BASTION":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "YONG MENG HSIEN":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "MUSHAWIAHTI BINTI MUSTAPHA":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "RONA ASNIDA NASARUDDIN":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "NOOR ANIAH AZMI":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "NUR SHAHIRAH BINTI AMIR HAMZAH":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "CHENG TECK CHEE":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "WAN HASLINA BT WAN ABDUL HALIM":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "TANG SENG FAI":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "AYESHA BINTI MOHD ZAIN":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "NORSHAMSIAH MD DIN":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "OTHMALIZA BINTI OTHMAN":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "AINAL ADLIN BINTI NAFFI":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;
                        case "SAFINAZ BINTI MOHD KHIALDIN":
                            textSubSpecialty.setText("OPHTHALMOLOGY");
                            break;

                        // for hepatobiliary only
                        case "ZAMRI BIN ZUHDI":
                            textSubSpecialty.setText("HEPATOBILIARY");
                            break;
                        case "AZLANUDIN BIN AZMAN":
                            textSubSpecialty.setText("HEPATOBILIARY");
                            break;
                        case "RAZMAN BIN JARMIN":
                            textSubSpecialty.setText("HEPATOBILIARY");
                            break;
                        case "BOO HAN SIN":
                            textSubSpecialty.setText("HEPATOBILIARY");
                            break;
                        case "FAHROL FAHMY BIN JAAFAR":
                            textSubSpecialty.setText("HEPATOBILIARY");
                            break;
                        case "CHIK IAN":
                            textSubSpecialty.setText("HEPATOBILIARY");
                            break;

                        //for breast and endocrine only
                        case "SHAHRUN NIZA BIN ABDULLAH SUHAIMI":
                            textSubSpecialty.setText("BREAST AND ENDOCRINE");
                            break;
                        case "ROHAIZAK BIN MUHAMMAD":
                            textSubSpecialty.setText("BREAST AND ENDOCRINE");
                            break;
                        case "NANI HARLINA BT MD. LATAR":
                            textSubSpecialty.setText("BREAST AND ENDOCRINE");
                            break;
                        case "ADI SYAZNI BIN MUHAMMED":
                            textSubSpecialty.setText("BREAST AND ENDOCRINE");
                            break;

                        //for colorectal only
                        case "NUR AFDZILLAH ABDUL RAHMAN":
                            textSubSpecialty.setText("COLORECTAL");
                            break;
                        case "ZAIRUL AZWAN BIN MOHD AZMAN":
                            textSubSpecialty.setText("COLORECTAL");
                            break;
                        case "ISMAIL BIN SAGAP":
                            textSubSpecialty.setText("COLORECTAL");
                            break;

                        //for breast only
                        case "NORLIA BT. ABDULLAH":
                            textSubSpecialty.setText("BREAST");
                            break;

                        //for UPPER GI MINIMALLY INVASIVE only
                        case "NIK RITZA KOSAI BIN NIK MAHMOOD":
                            textSubSpecialty.setText("UPPER GI MINIMALLY INVASIVE");
                            break;
                        case "REYNU A/P RAJAN":
                            textSubSpecialty.setText("UPPER GI MINIMALLY INVASIVE");
                            break;

                        //for CARDIOTHORASIC only
                        case "MOHD RAMZISHAM BIN ABDUL RAHMAN":
                            textSubSpecialty.setText("CARDIOTHORASIC");
                            break;
                        case "HAIRULFAIZI BIN HARON":
                            textSubSpecialty.setText("CARDIOTHORASIC");
                            break;
                        case "NURAYUB MD ALI":
                            textSubSpecialty.setText("CARDIOTHORASIC");
                            break;

                        //for UROLOGY only
                        case "ZULKIFLI MD ZAINUDDIN":
                            textSubSpecialty.setText("UROLOGY");
                            break;
                        case "FAM XENG INN":
                            textSubSpecialty.setText("UROLOGY");
                            break;
                        case "LIM LI YI":
                            textSubSpecialty.setText("UROLOGY");
                            break;
                        case "KHOO HAU CHUN":
                            textSubSpecialty.setText("UROLOGY");
                            break;

                        //for NEUROSURGERY only
                        case "AINUL SYAHRILFAZLI BIN JAAFAR":
                            textSubSpecialty.setText("NEUROSURGERY");
                            break;
                        case "FARIZAL BIN FADZIL":
                            textSubSpecialty.setText("NEUROSURGERY");
                            break;
                        case "KAMALANATHAN A/L PALANIANDY":
                            textSubSpecialty.setText("NEUROSURGERY");
                            break;
                        case "JEGAN A/L THANABALAN":
                            textSubSpecialty.setText("NEUROSURGERY");
                            break;
                        case "RAMESH KUMAR":
                            textSubSpecialty.setText("NEUROSURGERY");
                            break;
                        case "SANMUGARAJAH A/L PARAMASVARAN":
                            textSubSpecialty.setText("NEUROSURGERY");
                            break;
                        case "AZIZI BIN ABU BAKAR":
                            textSubSpecialty.setText("NEUROSURGERY");
                            break;
                        case "TOH CHARNG JIENG":
                            textSubSpecialty.setText("NEUROSURGERY");
                            break;

                        //for PLASTIC only
                        case "FARRAH HANI BINTI IMRAN":
                            textSubSpecialty.setText("PLASTIC");
                            break;
                        case "ADZIM POH YUEN WEN":
                            textSubSpecialty.setText("PLASTIC");
                            break;
                        case "SITI MUYASSARAH BINTI RUSLI":
                            textSubSpecialty.setText("PLASTIC");
                            break;

                        //for MAXILOFACIAL only
                        case "AHMAD SHUHUD IRFANI BIN ZAKARIA":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "SYED NABIL BIN SYED OMAR":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "JOTHI RAAMAHLINGAM A/L RAJARAN":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "FADZLINA BINTI ABD KARIM":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "ASIAH BINTI ABD WAHAB":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "ALIDA BINTI MAHYUDDIN":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "TAN HUANN LAN":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "MOHD NAZIMI BIN ABD JABAR":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "ROSZALINA BT. RAMLI":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "SITI SALMIAH MOHD YUNUS":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "RAMA KRSNA A/L RAJANDRAM":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "FARINAWATI BINTI YAZID":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "S. NAGARAJAN A/L M.P SOCKALINGAM":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "RIFQAH BINTI NORDIN":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;
                        case "ELAVARASI KUPPUSAMY":
                            textSubSpecialty.setText("MAXILOFACIAL");
                            break;

                        //for VASCULAR only
                        case "HANAFIAH HARUNARASHID":
                            textSubSpecialty.setText("VASCULAR");
                            break;
                        case "MOHAMAD AZIM BIN MD IDRIS":
                            textSubSpecialty.setText("VASCULAR");
                            break;
                        case "LENNY SURIANI SAFRI":
                            textSubSpecialty.setText("VASCULAR");
                            break;
                        case "KISHEN RAJ A/L CHANDRA SAKARAN":
                            textSubSpecialty.setText("VASCULAR");
                            break;

                        //for PAEDIATRIC SURGERY only
                        case "MARJMIN BINTI OSMAN":
                            textSubSpecialty.setText("PAEDIATRIC SURGERY");
                            break;
                        case "DAYANG ANITA ABD AZIZ":
                            textSubSpecialty.setText("PAEDIATRIC SURGERY");
                            break;

                        //for GENERAL ENT only
                        case "LUM SAI GUAN":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "FARAH LIANA LOKMAN":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "ANEEZA KHAIRIYAH BINTI WAN HAMIZAN":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "SALINA HUSAIN":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "GOH BEE SEE":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "LOKMAN SAIM":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "ASMA BINTI ABDULLAH":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "FARAH DAYANA BINTI ZAHEDI":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "ZARA NASSERI":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "NOOR DINA HASHIM":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "MARINA BT MAT BAKI":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "MAWADDAH BINTI AZMAN":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "MOHD RAZIF BIN MOHAMAD YUNUS":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;
                        case "HARDIP SINGH GENDEH":
                            textSubSpecialty.setText("GENERAL ENT");
                            break;

                        //for PAEDIATRIC ORTHOPEDIC only
                        case "MUHD KAMAL MUHD ABDUL JAMIL":
                            textSubSpecialty.setText("PAEDIATRIC ORTHOPEDIC");
                            break;
                        case "ABDUL HALIM BIN ABD RASHID":
                            textSubSpecialty.setText("PAEDIATRIC ORTHOPEDIC");
                            break;

                        //for ADVANCE TRAUMA only
                        case "MOHD YAZID BIN BAJURI":
                            textSubSpecialty.setText("ADVANCE TRAUMA");
                            break;

                        //for SPINAL only
                        case "AZMI BIN BAHARUDIN":
                            textSubSpecialty.setText("SPINAL");
                            break;
                        case "MOHD HISAM BIN MOHD ARIFFIN":
                            textSubSpecialty.setText("SPINAL");
                            break;
                        case "TAN JIN AUNM":
                            textSubSpecialty.setText("SPINAL");
                            break;
                        case "NG BING WUI":
                            textSubSpecialty.setText("SPINAL");
                            break;
                        case "SABARUL AFIAN BIN MOKHTAR":
                            textSubSpecialty.setText("SPINAL");
                            break;

                        //for ARTHOPLASTY only
                        case "NOR HAMDAN BIN MOHAMAD YAHAYA":
                            textSubSpecialty.setText("ARTHOPLASTY");
                            break;
                        case "RIZAL BIN ABDUL RANI":
                            textSubSpecialty.setText("ARTHOPLASTY");
                            break;

                        //for ORTHO-ONCOLOGY only
                        case "NOR HAZLA BINTI MOHAMED HAFLAH":
                            textSubSpecialty.setText("ORTHO-ONCOLOGY");
                            break;
                        case "MOHAMED HAFIZUDIN ABDULLAH SANI":
                            textSubSpecialty.setText("ORTHO-ONCOLOGY");
                            break;

                        //for SPORT only
                        case "BADRUL AKMAL HISHAM B. MD. YUSOFF":
                            textSubSpecialty.setText("SPORT");
                            break;
                        case "AHMAD FARIHAN BIN MOHD DON":
                            textSubSpecialty.setText("SPORT");
                            break;
                    }

                }else {

                    String surgeonName = textSurgicalTeam.getText().toString();

                    switch (surgeonName) {

                        case "":
                            textSubSpecialty.setText("");
                            break;

                    }

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        textSurgicalTeam.addTextChangedListener(textWatcher);

    }

    //method to get current date
    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-LL-dd", Locale.getDefault()).format(new Date());

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
                    Toast.makeText(RegisterDiagnosis.this,"error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Toast.makeText(RegisterDiagnosis.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    //update status on patient_tb to make it disappear from list
    private void updateData() {

        final String id = textID.getText().toString().trim().toUpperCase();
        final String status = "-";

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");


            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST,  URL.urls + "insertStatus.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(RegisterDiagnosis.this, response, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterDiagnosis.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();

                    params.put("id",id);
                    params.put("status",status);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterDiagnosis.this);
            requestQueue.add(request);


    }


    //method save data into database
    private void insertData() {

        final String mrn = textMRN.getText().toString().trim().toUpperCase();
        final String name = textName.getText().toString().trim().toUpperCase();
        final String name_diagnosis_desc = textNameDiagnosisDesc.getText().toString().trim();
        final String procedure_name = textProcedureName.getText().toString().trim();
        final String procedure_name_2 = edProcedureName2.getText().toString().trim();
        final String procedure_name_3 = edProcedureName3.getText().toString().trim();
        //final String surgery_duration = textSurgeryDuration.getSelectedItem().toString().trim();
        final String nurse_id = textNurseID.getText().toString().trim();
        //final String otroom = textBookOT.getText().toString().trim();
        //final String surgeryTime =  textBookSurgeryDate.getText().toString().trim() + " " + textBookSurgeryTime.getText().toString().trim();
        final String surgical_team = textSurgicalTeam.getText().toString().toUpperCase().trim();
        final String sub_specialty = textSubSpecialty.getText().toString().trim();
        final String last_meal = textLastMealDate.getText().toString().trim() + " " + textLastMealTime.getText().toString().trim();

        //to check validate
        final String lastMealTime =  textLastMealTime.getText().toString().trim();
        final String lastMealDate =  textLastMealTime.getText().toString().trim();
        final String operationDate =  textBookSurgeryDate.getText().toString().trim();
        //final String operationTime =  textBookSurgeryTime.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if(lastMealDate.isEmpty()){
            Toast.makeText(this, "Please enter last meal of patient!", Toast.LENGTH_SHORT).show();
            textLastMealDate.setError("Please enter date of last meal!");
            return;
        }
        else if(lastMealTime.isEmpty()){
            Toast.makeText(this, "Please enter last meal of patient!", Toast.LENGTH_SHORT).show();
            textLastMealTime.setError("Please enter time of last meal!");
            return;
        }
        else if (operationDate.isEmpty()) {
            Toast.makeText(this, "Please enter surgery date!", Toast.LENGTH_SHORT).show();
            textBookSurgeryDate.setError("Please enter surgery date!");
            return;
        }
        else if(surgical_team.isEmpty()){
            Toast.makeText(this, "Please enter surgical team!", Toast.LENGTH_SHORT).show();
            textSurgicalTeam.setError("Please enter surgical team!");
            return;
        }
        else if(sub_specialty.isEmpty()){
            Toast.makeText(this, "Please enter sub specialty!", Toast.LENGTH_SHORT).show();
            textSubSpecialty.setError("Please enter sub specialty!");
            return;
        }
        else if(procedure_name.isEmpty()){
            Toast.makeText(this, "Please enter procedure name!", Toast.LENGTH_SHORT).show();
            textProcedureName.setError("Please enter procedure name!");
            return;
        }
        else if(name_diagnosis_desc.isEmpty()){
            Toast.makeText(this, "Please enter diagnosis description!", Toast.LENGTH_SHORT).show();
            textNameDiagnosisDesc.setError("Please enter diagnosis description!");
            return;
        }

        else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST,  URL.urls + "insertDiagnosis.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("Data Inserted")){

                                Intent i = new Intent(RegisterDiagnosis.this,DashboardNurse.class);
                                Toast.makeText(RegisterDiagnosis.this, "Diagnosis of patient successfully register!", Toast.LENGTH_SHORT).show();
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                progressDialog.dismiss();

                            }
                            else{
                                Toast.makeText(RegisterDiagnosis.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(RegisterDiagnosis.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();

                    params.put("mrn",mrn);
                    params.put("name",name);
                    params.put("name_diagnosis_desc",name_diagnosis_desc);
                    params.put("procedure_name",procedure_name);
                    params.put("procedure_name_2",procedure_name_2);
                    params.put("procedure_name_3",procedure_name_3);
                    //params.put("surgeryTime",surgeryTime);
                    //params.put("surgery_duration",surgery_duration);
                    params.put("nurse_id",nurse_id);
                    //params.put("otroom",otroom);
                    params.put("surgical_team",surgical_team);
                    params.put("sub_specialty",sub_specialty);
                    params.put("last_meal",last_meal);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(RegisterDiagnosis.this);
            requestQueue.add(request);

        }

    }


    //method to get created date
    private String getCreatedDateWithMonthName(){
        return new SimpleDateFormat("dd-LLL-yyyy", Locale.getDefault()).format(new Date());
    }


    //method to get current time created
    private String getTimeInWithAmAndPm(){
        return new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());
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