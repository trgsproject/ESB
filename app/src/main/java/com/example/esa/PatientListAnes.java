package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PatientListAnes extends AppCompatActivity {

    ListView listView;
    MyAdapterPatient adapter;

    public static ArrayList<Patient> patientArrayList = new ArrayList<>();
    String url = URL.urls + "retrievePatient.php";

    Patient patient;
    float v;

    ImageView buttonBack;
    SwipeRefreshLayout refreshLayout;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list_anes);
        getSupportActionBar().hide();

        //declare variables
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        listView = findViewById(R.id.myListView);
        buttonBack = findViewById(R.id.buttonBack);
        refreshLayout = findViewById(R.id.refreshLayout);
        searchView = findViewById(R.id.search_bar);

        //get details patient
        adapter = new MyAdapterPatient(this, patientArrayList);
        listView.setAdapter(adapter);

        //animation button
        listView.setTranslationX(300);
        listView.setAlpha(v);
        listView.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();

        //back button
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PatientListAnes.this,Dashboard.class);
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
                        startActivity(new Intent(getApplicationContext(),Setting.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });



        //search View
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                PatientListAnes.this.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                ArrayList<Patient> filteredName = new ArrayList<>();

                for ( Patient patient : patientArrayList){

                    if (patient.getName().toLowerCase().contains(newText.toLowerCase()) || patient.getStatus().toLowerCase().contains(newText.toLowerCase()) ||  patient.getAge().toLowerCase().contains(newText.toLowerCase()) || patient.getGender().toLowerCase().contains(newText.toLowerCase())
                            || patient.getNationality().toLowerCase().contains(newText.toLowerCase()) ){
                        filteredName.add(patient);
                    }
                }
                MyAdapterPatient adapter = new MyAdapterPatient(getApplicationContext(),filteredName);
                listView.setAdapter(adapter);

                return false;
            }
        });


        //call patient list from database
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                ProgressDialog progressDialog = new ProgressDialog(view.getContext());

                CharSequence[] dialogItem = {"Classify Category Patient", "Approve Patient"};

                builder.setTitle(patientArrayList.get(position).getName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                startActivity(new Intent(getApplicationContext(), PatientViewDetailsAnesthetist.class)
                                        .putExtra("position", position));
                                break;

                            case 1:
                                startActivity(new Intent(getApplicationContext(), PatientEditAnesthetist.class)
                                        .putExtra("position", position));
                                break;

                        }
                    }
                });

                builder.create().show();
            }
        });
        retrieveData();

        //refresh data
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                retrieveData();
                refreshLayout.setRefreshing(false);

            }
        });

    }


    //delete data
    private void deleteData(final String id) {

        StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "deletePatient.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equalsIgnoreCase("Data Deleted")) {
                            startActivity(new Intent(getApplicationContext(),Testing.class));
                            Toast.makeText(PatientListAnes.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(PatientListAnes.this, "Data Not Deleted", Toast.LENGTH_SHORT).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PatientListAnes.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }


    //retrieve data
    public void retrieveData() {

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        patientArrayList.clear();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("Patient_Tb");

                            if (success.equals("1")) {


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String MRN = object.getString("MRN");
                                    String Name = object.getString("Name");
                                    String Age = object.getString("Age");
                                    String Gender = object.getString("Gender");
                                    String Nationality = object.getString("Nationality");
                                    String Race = object.getString("Race");
                                    String Country = object.getString("Country");
                                    String Ward = object.getString("Ward");
                                    String SurgeonName = object.getString("SurgeonName");
                                    String DiagnosisDescription = object.getString("DiagnosisDescription");
                                    String ProcedureName1 = object.getString("ProcedureName1");
                                    String ProcedureName2 = object.getString("ProcedureName2");
                                    String ProcedureName3 = object.getString("ProcedureName3");
                                    String SubSpecialty= object.getString("SubSpecialty");
                                    String LastMeal = object.getString("LastMeal");
                                    String InformAnesthetist = object.getString("InformAnesthetist");
                                    String AnesthetistName = object.getString("AnesthetistName");
                                    String HighDependencyArea = object.getString("HighDependencyArea");
                                    String ArrivalTime = object.getString("ArrivalTime");
                                    String CreatedTime = object.getString("CreatedTime");
                                    String ApprovedTime = object.getString("ApprovedTime");
                                    String Status = object.getString("Status");
                                    String category_status = object.getString("category_status");

                                    patient = new Patient(  id, MRN, Name, Age, Gender, Nationality, Race, Country,Ward, SurgeonName, DiagnosisDescription, ProcedureName1, ProcedureName2, ProcedureName3,
                                            SubSpecialty, LastMeal, InformAnesthetist, AnesthetistName, HighDependencyArea, ArrivalTime, CreatedTime, ApprovedTime, Status, category_status);
                                    patientArrayList.add(0,patient);
                                    adapter.notifyDataSetChanged();


                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PatientListAnes.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }



    @Override
    public void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
