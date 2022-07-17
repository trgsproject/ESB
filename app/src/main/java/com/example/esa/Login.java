package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {

    float v;
    Button Register;

    ImageView logo;
    TextView Login,forgotPassword;

    EditText editTextUsername,editTextPassword;
    Button ButtonLogin;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        //declare variables
        Register = findViewById(R.id.txtRegister);
        logo = findViewById(R.id.imageLogo);
        Login = findViewById(R.id.txtLogin);
        forgotPassword = findViewById(R.id.txtForgotPassword);
        editTextUsername = findViewById(R.id.txtUsername);
        editTextPassword = findViewById(R.id.txtPassword);
        ButtonLogin = findViewById(R.id.buttonLogin);

        //method on click listener
        Register.setOnClickListener(this);
        ButtonLogin.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        //set Translation X for each variables
        Register.setTranslationX(800);
        logo.setTranslationX(800);
        Login.setTranslationX(800);
        forgotPassword.setTranslationX(800);
        editTextUsername.setTranslationX(800);
        editTextPassword.setTranslationX(800);
        ButtonLogin.setTranslationX(800);

        //set Alpha v  for each variables
        Register.setAlpha(v);
        logo.setAlpha(v);
        Login.setAlpha(v);
        forgotPassword.setAlpha(v);
        editTextUsername.setAlpha(v);
        editTextPassword.setAlpha(v);
        ButtonLogin.setAlpha(v);

        //set animation for each variables
        logo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        Login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        editTextUsername.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        editTextPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        forgotPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();
        ButtonLogin.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1200).start();
        Register.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1400).start();


        //set String role
        final String role = SharedPrefManager.getInstance(this).getUserRole();

        //getInstance user role
        if(SharedPrefManager.getInstance(this).isLoggedIn()){

            if (role.equals("OT Team") ) {

                finish();
                startActivity(new Intent(this, DashboardNurse.class));
                return;

            }

            else if(role.equals("Anaesthetist")){

                finish();
                startActivity(new Intent(this, Dashboard.class));
                return;

            }

            else if(role.equals("Surgical Team")){

                finish();
                startActivity(new Intent(this, RegisterPatientAfterLogin.class));
                return;

            }

        }

        //forgot password method
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,ForgotPassword.class);
                startActivity(i);
            }
        });

    }


    //login method and save into database
    private void userLogin(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext())
                                        .userLogin(
                                                obj.getString("username"),
                                                obj.getString("name"),
                                                obj.getString("role"),
                                                obj.getString("subspecialty"),
                                                obj.getString("email"),
                                                obj.getString("phone")
                                        );

                                if(  obj.getString("role").equals("Anaesthetist") ){

                                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                                    Toast.makeText(Login.this,"Welcome Back, Anaesthetist", Toast.LENGTH_LONG).show();

                                    finish();

                                }
                                else if( obj.getString("role").equals("OT Team")){

                                    startActivity(new Intent(getApplicationContext(), DashboardNurse.class));
                                    Toast.makeText(Login.this,"Welcome Back, Nurse", Toast.LENGTH_LONG).show();

                                    finish();
                                }

                                else if( obj.getString("role").equals("Surgical Team")){

                                    startActivity(new Intent(getApplicationContext(), RegisterPatientAfterLogin.class));
                                    Toast.makeText(Login.this,"Welcome Back, Surgeon", Toast.LENGTH_LONG).show();

                                    finish();
                                }


                            }else{
                                Toast.makeText(
                                        getApplicationContext(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }



    //Onclick method listener for login and register
    @Override
    public void onClick(View view) {
        if (view == ButtonLogin)
            userLogin();
        if(view == Register)
            startActivity(new Intent(this, MainActivity.class));

    }

}