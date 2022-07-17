package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NewPassword extends AppCompatActivity {

    Button buttonSavePassword;
    ImageView buttonBack;
    EditText newpass,confirmpass,editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        getSupportActionBar().hide();

        //declare variables
        editEmail = findViewById(R.id.textEmail);
        newpass = findViewById(R.id.newpassword);
        confirmpass = findViewById(R.id.verifypassword);
        buttonBack = findViewById(R.id.back);
        buttonSavePassword = findViewById(R.id.buttonSavePassword);

        //button back
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(NewPassword.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        //get user data
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        //set text and call intent
        editEmail.setText(email);




    }

    public void btn_updatePass(View view) {

        final String email = editEmail.getText().toString();
        final String newpassword = newpass.getText().toString();
        final String password = confirmpass.getText().toString();

        if (!validateNewPass() | !validateConfirmPass() | !validateLengthpassword() ) {
            //Toast toast = Toast.makeText(getApplicationContext(), "New password and confirm password must be same!", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
            // toast.show(); // display the Toast
            return;
        }

        else if(newpassword.equals(password)) {

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Updating....");
            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, URL.urls + "updateForgotPass.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            startActivity(new Intent(getApplicationContext(), Login.class));
                            Toast.makeText(NewPassword.this, response, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();


                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(NewPassword.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<String, String>();

                    params.put("email", email);
                    params.put("password", password);

                    return params;
                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(NewPassword.this);
            requestQueue.add(request);

            return;
        }

        else {
            Toast toast = Toast.makeText(getApplicationContext(), "New password and confirm password must be same!", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
            toast.show(); // display the Toast
            return;

        }

    }


    //method validate for Length Password
    private boolean validateLengthpassword() {
        String newPass = newpass.getText().toString();
        String conPass = confirmpass.getText().toString();

        if(newPass.length()<6 && conPass.length()<6){
            newpass.setError("Password is too short, at least 6 letters.");
            confirmpass.setError("Password is too short, at least 6 letters.");
            return false;
        }
        else{

            return true;
        }
    }

    //validate confirm password
    private boolean validateConfirmPass() {
        String val = confirmpass.getText().toString();

        if (val.isEmpty()){
            confirmpass.setError("Please fill in each field!");
            return false;
        }
        else{
            confirmpass.setError(null);
            return true;
        }
    }

    //validate new password
    private boolean validateNewPass() {
        String val = newpass.getText().toString();

        if (val.isEmpty()) {
            newpass.setError("Please fill in each field!");
            return false;
        } else {
            newpass.setError(null);
            return true;
        }
    }
}