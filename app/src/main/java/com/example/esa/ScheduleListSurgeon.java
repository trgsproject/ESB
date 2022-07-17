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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScheduleListSurgeon extends AppCompatActivity {

    ListView listView;
    MyAdapterSchedule adapter;

    public static ArrayList<Schedule> scheduleArrayList = new ArrayList<>();
    String url =URL.urls + "retrieveScheduleList.php";

    Schedule schedule;
    float v;

    LinearLayoutManager mLinearLayoutManager;
    SwipeRefreshLayout refreshLayout;

    SearchView searchView;
    ImageView buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_list_surgeon);
        getSupportActionBar().hide();

        //declare variables
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        refreshLayout = findViewById(R.id.refreshLayout);
        searchView = findViewById(R.id.search_bar);
        buttonBack = findViewById(R.id.buttonBack);

        //getUsername
        listView = findViewById(R.id.myListView);

        adapter = new MyAdapterSchedule(this, scheduleArrayList);
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
                        startActivity(new Intent(getApplicationContext(),Setting.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        //buttonBack return to dashboard
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScheduleListSurgeon.this,DashboardSurgeon.class);
                startActivity(i);
            }
        });

        //search View
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                ScheduleListSurgeon.this.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                ArrayList<Schedule> filteredName = new ArrayList<>();

                for ( Schedule schedule : scheduleArrayList){

                    if (schedule.getPatientName().toLowerCase().contains(newText.toLowerCase()) || schedule.getCategory().toLowerCase().contains(newText.toLowerCase()) || schedule.getMRN().toLowerCase().contains(newText.toLowerCase())
                            || schedule.getOT().toLowerCase().contains(newText.toLowerCase()) ){
                        filteredName.add(schedule);
                    }
                }
                MyAdapterSchedule adapter = new MyAdapterSchedule(getApplicationContext(),filteredName);
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

                CharSequence[] dialogItem = {"View Patient Detail"};

                builder.setTitle(scheduleArrayList.get(position).getPatientName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                startActivity(new Intent(getApplicationContext(), SchedulePatientDetailsSurgeon.class)
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

                        scheduleArrayList.clear();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("schedule");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String MRN = object.getString("MRN");
                                    String PatientName = object.getString("PatientName");
                                    String Category = object.getString("Category");
                                    String SurgeryDate = object.getString("SurgeryDate");
                                    String OT = object.getString("OT");
                                    String CallTime = object.getString("CallTime");
                                    String ArrivalTimeToOT = object.getString("ArrivalTimeToOT");
                                    String SurgeryStartTime = object.getString("SurgeryStartTime");
                                    String SurgeryEndTime = object.getString("SurgeryEndTime");
                                    String PHUStartTime = object.getString("PHUStartTime");
                                    String PHUidBed = object.getString("PHUidBed");
                                    String PACUStartTime = object.getString("PACUStartTime");
                                    String PACUidBed = object.getString("PACUidBed");

                                    schedule = new Schedule(id, MRN, PatientName, Category, SurgeryDate, OT, CallTime, ArrivalTimeToOT, SurgeryStartTime, SurgeryEndTime, PHUStartTime, PHUidBed, PACUStartTime, PACUidBed);
                                    scheduleArrayList.add(0,schedule);
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
                Toast.makeText(ScheduleListSurgeon.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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