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

public class ForgotPassword extends AppCompatActivity {

    Button buttonResetPassword;
    ImageView buttonBack;
    EditText editEmail;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, RegisterPatientAfterLogin.class));
            return;
        }

        //declare varaiables
        editEmail = findViewById(R.id.textEmail);
        buttonBack = findViewById(R.id.back);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);

        //button back
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ForgotPassword.this,Login.class);
                startActivity(i);
            }
        });

        //button reset password
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!validateEmail() ){
                    Toast toast = Toast.makeText(getApplicationContext(), "Please enter email siswa!", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
                    toast.show(); // display the Toast
                    return;
                }


                //declares variables
                final String email = editEmail.getText().toString().trim();

                //get intent
                Intent intent = new Intent(getApplicationContext(),NewPassword.class);
                intent.putExtra("email",email);

                startActivity(intent);

            }
        });



    }



    //method validate for Email
    private boolean validateEmail() {
        String val = editEmail.getText().toString();
        // String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            editEmail.setError("Please enter email siswa!");
            return false;
        }
        //  else if(val.matches(emailPattern)){
        //     editEmail.setError("Please enter email siswa!");
        //      return false;
        //  }

        else{
            editEmail.setError(null);
            return true;
        }
    }

}