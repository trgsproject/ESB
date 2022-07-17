package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DiagnosisList extends AppCompatActivity {

    ListView listView;
    MyAdapterDignosis adapter;

    public static ArrayList<Diagnosis> diagnosisArrayList = new ArrayList<>();
    String url = URL.urls + "retrieveDiagnosisForNurse.php";

    Diagnosis diagnosis;
    float v;

    LinearLayoutManager mLinearLayoutManager;
    ImageView buttonBack;

    SwipeRefreshLayout refreshLayout;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_list);
        getSupportActionBar().hide();

        //declare variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        buttonBack = findViewById(R.id.buttonBack);
        refreshLayout = findViewById(R.id.refreshLayout);
        searchView = findViewById(R.id.search_bar);
        listView = findViewById(R.id.myListView);

        //getUsername
        adapter = new MyAdapterDignosis(this, diagnosisArrayList);
        listView.setAdapter(adapter);

        //animation button
        listView.setTranslationX(300);
        listView.setAlpha(v);
        listView.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();

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

        //button back
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DiagnosisList.this, DashboardNurse.class);
                startActivity(i);
            }
        });



        //search View
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                DiagnosisList.this.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                ArrayList<Diagnosis> filteredName = new ArrayList<>();

                for ( Diagnosis diagnosis : diagnosisArrayList){

                    if (diagnosis.getName().toLowerCase().contains(newText.toLowerCase())  || diagnosis.getProcedure_name().toLowerCase().contains(newText.toLowerCase())
                            || diagnosis.getApproved_status().toLowerCase().contains(newText.toLowerCase()) ){
                        filteredName.add(diagnosis);
                    }
                }
                MyAdapterDignosis adapter = new MyAdapterDignosis(getApplicationContext(),filteredName);
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

                CharSequence[] dialogItem = {"View Patient", "Edit Patient"};

                builder.setTitle(diagnosisArrayList.get(position).getName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                startActivity(new Intent(getApplicationContext(), PatientDetailsDiagnosisNurse.class)
                                        .putExtra("position", position));
                                break;
                            case 1:
                                startActivity(new Intent(getApplicationContext(), PatientEditDiagnosisNurse.class)
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


    //retrieve data
    public void retrieveData() {

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        diagnosisArrayList.clear();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("Diagnosis_Tb");

                            if (sucess.equals("1")) {


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String mrn = object.getString("mrn");
                                    String name = object.getString("name");
                                    String name_diagnosis_desc = object.getString("name_diagnosis_desc");
                                    String procedure_name = object.getString("procedure_name");
                                    String procedure_name_2 = object.getString("procedure_name_2");
                                    String procedure_name_3 = object.getString("procedure_name_3");
                                    String nurse_id = object.getString("nurse_id");
                                    String surgeon_id = object.getString("surgeon_id");
                                    String surgical_team = object.getString("surgical_team");
                                    String sub_specialty = object.getString("sub_specialty");
                                    String last_meal = object.getString("last_meal");
                                    String arrival_time_to_surgeon = object.getString("arrival_time_to_surgeon");
                                    String high_dependency_area =object.getString("high_dependency_area");
                                    String on_call = object.getString("on_call");
                                    String inform_anaesthetist = object.getString("inform_anaesthetist");
                                    String approved_status = object.getString("approved_status");
                                    String time_approval = object.getString("time_approval");
                                    String category_status = object.getString("category_status");

                                    diagnosis = new Diagnosis( id,  mrn,  name,  name_diagnosis_desc,  procedure_name,procedure_name_2, procedure_name_3, nurse_id,  surgeon_id, surgical_team,sub_specialty, last_meal,arrival_time_to_surgeon, high_dependency_area,on_call,inform_anaesthetist, approved_status, time_approval, category_status);
                                    diagnosisArrayList.add(0,diagnosis);
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
                Toast.makeText(DiagnosisList.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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