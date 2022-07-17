package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

public class AnesthetistListForNuse extends AppCompatActivity {

    ListView listView;
    MyAdapterAnaesthetist adapter;

    public static ArrayList<User> arrayListAnesthetist= new ArrayList<>();
    String url = URL.urls + "retrieveAnaesthetist.php";

    User user;
    float v;

    ImageView buttonBack;
    SwipeRefreshLayout refreshLayout;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anesthetist_list_for_nuse);
        getSupportActionBar().hide();

        //declare variables
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        listView = findViewById(R.id.myListView);
        buttonBack =  findViewById(R.id.buttonBack);
        refreshLayout = findViewById(R.id.refreshLayout);
        searchView = findViewById(R.id.search_bar);

        adapter = new MyAdapterAnaesthetist(this, arrayListAnesthetist);
        listView.setAdapter(adapter);

        //animation button
        listView.setTranslationX(300);
        listView.setAlpha(v);
        listView.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(200).start();


        //back button to previous intent
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AnesthetistListForNuse.this, DashboardNurse.class);
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
                        startActivity(new Intent(getApplicationContext(), SettingNurse.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        //search bar
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                AnesthetistListForNuse.this.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                ArrayList<User> filteredName = new ArrayList<>();

                for ( User user : arrayListAnesthetist){

                    if (user.getName().toLowerCase().contains(newText.toLowerCase()) |
                            user.getSubspecialty().toLowerCase().contains(newText.toLowerCase())){
                        filteredName.add(user);
                    }
                }
                MyAdapterSurgeon adapter = new MyAdapterSurgeon(getApplicationContext(),filteredName);
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

                CharSequence[] dialogItem = {"Call Anaesthetist"};

                builder.setTitle(arrayListAnesthetist.get(position).getName());
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse("tel:" + AnesthetistListForNuse.arrayListAnesthetist.get(position).getPhone()));
                                startActivity(intent);
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

                        arrayListAnesthetist.clear();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("users");

                            if (success.equals("1")) {


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String username = object.getString("username");
                                    String password = object.getString("password");
                                    String name = object.getString("name");
                                    String role = object.getString("role");
                                    String subspecialty = object.getString("subspecialty");
                                    String email = object.getString("email");
                                    String phone = object.getString("phone");
                                    String createdAt = object.getString("createdAt");
                                    String updatedAt = object.getString("updatedAt");

                                    user = new User(id, username, password, name, role, subspecialty,
                                            email, phone, createdAt, updatedAt);
                                    arrayListAnesthetist.add(0,user);
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
                Toast.makeText(AnesthetistListForNuse.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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