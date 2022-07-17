package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordForNurse extends AppCompatActivity {

    private EditText textNewPass,textConfirmPass;
    TextView textUsername;
    ImageView buttonBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_for_nurse);
        getSupportActionBar().hide();

        //declare variables
        textNewPass = (EditText)findViewById(R.id.textNewPass);
        textConfirmPass = (EditText)findViewById(R.id.textConfirmPass);
        textUsername = findViewById(R.id.textUsername);
        buttonBack =  findViewById(R.id.buttonBack);

        //get user data
        textUsername.setText(SharedPrefManager.getInstance(this).getUsername());

        //back button to previous intent
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChangePasswordForNurse.this,SettingNurse.class);
                startActivity(i);
            }
        });

        //bottomNavigation
        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.setting);

        //intent bottomNavigation
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),DashboardNurse.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.register:
                        startActivity(new Intent(getApplicationContext(),RegisterPatientNurse.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.setting:
                        return true;
                }
                return false;
            }
        });

    }

    //method to update password
    public void btn_updatePass(View view) {

        if (!validateNewPass() | !validateConfirmPass() | !validateLengthpassword() ) {
            Toast toast = Toast.makeText(getApplicationContext(), "Please fill in each field!", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
            toast.show(); // display the Toast
            return;
        }

        final String username = textUsername.getText().toString();
        final String password = textConfirmPass.getText().toString();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST,  URL.urls + "updatePass.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        startActivity(new Intent(getApplicationContext(), SettingNurse.class));
                        Toast.makeText(ChangePasswordForNurse.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ChangePasswordForNurse.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();

                params.put("username", username);
                params.put("password", password);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ChangePasswordForNurse.this);
        requestQueue.add(request);

    }


    //method validate for Length Password
    private boolean validateLengthpassword() {
        String newpass = textNewPass.getText().toString();
        String conpass = textConfirmPass.getText().toString();

        if(newpass.length()<6 && conpass.length()<6){
            textNewPass.setError("Password is too short, at least 6 letters.");
            textConfirmPass.setError("Password is too short, at least 6 letters.");
            return false;
        }
        else{

            return true;
        }
    }

    //validate confirm password
    private boolean validateConfirmPass() {
        String val = textConfirmPass.getText().toString();

        if (val.isEmpty()){
            textConfirmPass.setError("Please fill in each field!");
            return false;
        }
        else{
            textConfirmPass.setError(null);
            return true;
        }
    }

    //validate new password
    private boolean validateNewPass() {
        String val = textNewPass.getText().toString();

        if (val.isEmpty()) {
            textNewPass.setError("Please fill in each field!");
            return false;
        } else {
            textNewPass.setError(null);
            return true;

        }
    }
}