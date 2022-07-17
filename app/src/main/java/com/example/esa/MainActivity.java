package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView logo;
    Button Login;

    TextView Register, editTextCreatedAt, AddNewSurgeonName;

    Button ButtonSignUp;
    EditText editTextUsername,editTextPassword,editTextName,editTextEmail,editTextPhone;

    AutoCompleteTextView editTextSubSpeciality;
    Spinner editTextLevel;

    float v;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        //get String
        String[] SubSpeciality = getResources().getStringArray(R.array.SubSpeciality);

        //declare variables
        Login = findViewById(R.id.textLogin);
        logo = findViewById(R.id.imageLogo);
        Register = findViewById(R.id.txtSignUp);
        ButtonSignUp  = findViewById(R.id.buttonSignUp);
        editTextUsername  = findViewById(R.id.txtUsername);
        editTextPassword  = findViewById(R.id.txtPassword);
        editTextName  = findViewById(R.id.txtName);
        editTextLevel  = findViewById(R.id.txtLevel);
        editTextSubSpeciality  = findViewById(R.id.txtSubsp);
        editTextEmail  = findViewById(R.id.txtEmailAddress);
        editTextPhone  = findViewById(R.id.txtPhoneNumber);
        editTextCreatedAt = findViewById(R.id.txtCreatedAt);
        AddNewSurgeonName = findViewById(R.id.AddNewSurgeonName);

        //method click on listener
        progressDialog = new ProgressDialog(this);
        ButtonSignUp.setOnClickListener(this);
        Login.setOnClickListener(this);
        editTextLevel.setPadding(55, editTextLevel.getPaddingTop(), editTextLevel.getPaddingRight(), editTextLevel.getPaddingBottom());

        //set the TranslationX
        Login.setTranslationX(800);
        logo.setTranslationX(800);
        Register.setTranslationX(800);
        ButtonSignUp.setTranslationX(800);
        editTextUsername.setTranslationX(800);
        editTextPassword.setTranslationX(800);
        editTextName.setTranslationX(800);
        editTextLevel.setTranslationX(800);
        editTextSubSpeciality.setTranslationX(800);
        editTextEmail.setTranslationX(800);
        editTextPhone.setTranslationX(800);
        AddNewSurgeonName.setTranslationX(800);

        //set float into setAlpha
        Login.setAlpha(v);
        logo.setAlpha(v);
        Register.setAlpha(v);
        ButtonSignUp.setAlpha(v);
        editTextUsername.setAlpha(v);
        editTextPassword.setAlpha(v);
        editTextName.setAlpha(v);
        editTextLevel.setAlpha(v);
        editTextSubSpeciality.setAlpha(v);
        editTextEmail.setAlpha(v);
        editTextPhone.setAlpha(v);
        AddNewSurgeonName.setAlpha(v);

        //animation from left to right
        logo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(200).start();
        Register.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(400).start();
        editTextUsername.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(600).start();
        editTextPassword.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(800).start();
        editTextName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1000).start();
        editTextLevel.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1200).start();
        editTextSubSpeciality.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1400).start();
        editTextEmail.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1600).start();
        editTextPhone.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(1800).start();
        ButtonSignUp.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2000).start();
        Login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2200).start();
        AddNewSurgeonName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(2400).start();

        //set role of users
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.Level, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editTextLevel.setAdapter(adapter2);

        //set subSpecialty
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,SubSpeciality);
        editTextSubSpeciality.setAdapter(adapter);

        //set time created at
        editTextCreatedAt.setText( getCreatedDateWithMonthName() + " " +  getTimeInWithAmAndPm() );


    }

    //method to get created date
    private String getCreatedDateWithMonthName(){
        return new SimpleDateFormat("dd-LLL-yyyy", Locale.getDefault()).format(new Date());
    }

    //method to get current time created
    private String getTimeInWithAmAndPm(){
        return new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());
    }


    //method to register user
    private void registerUser() {

        //check validation
        if (!validateUsername()){
            return;
        }

        else if (!validatePassword()){
            return;
        }

        else if (!validateLengthPassword()){
            return;
        }

        else if (!validateFullName()){
            return;
        }

        else if (editTextLevel.getSelectedItem().toString().trim().equals("Role")) {
            Toast.makeText(MainActivity.this, "Please enter Role!", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (!validateSubSpecialty()){
            return;
        }

        else if (!validateEmail()){
            return;
        }

        else if (!validatePhone()){
            return;
        }

        //declare String for each input to insert into Database
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String name = editTextName.getText().toString().trim().toUpperCase();
        final String role = editTextLevel.getSelectedItem().toString().trim();
        final String subspecialty = editTextSubSpeciality.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        //final String createdAt = "";

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            //Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                            String error = jsonObject.getString("message");

                            if (error.equals("User registered successfully")) {

                                Intent intent = new Intent(MainActivity.this, Login.class);
                                Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                startActivity(intent);
                                finish();

                            } else {

                                Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("name",name);
                params.put("role", role);
                params.put("subspecialty", subspecialty);
                params.put("email", email);
                params.put("phone",phone);
                //params.put("createdAt",createdAt);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    //method validate for Username
    private boolean validateUsername() {
        String val = editTextUsername.getText().toString();

        if (val.isEmpty()){
            editTextUsername.setError("Please fill in each field!");
            return false;
        }
        else if (val.length()>=15){
            editTextUsername.setError("Username is too long!");
            return false;
        }
        else{
            editTextUsername.setError(null);
            return true;
        }
    }

    //method validate for Password
    private boolean validatePassword() {
        String val = editTextPassword.getText().toString();

        if (val.isEmpty()){
            editTextPassword.setError("Please fill in each field!");
            return false;
        }
        else{
            editTextPassword.setError(null);
            return true;
        }
    }

    //method validate for Length Password
    private boolean validateLengthPassword() {
        String val = editTextPassword.getText().toString();

        if(val.length()<6){
            editTextPassword.setError("Password is too short, at least 6 letters.");
            return false;
        }
        else{
            editTextPassword.setError(null);
            return true;
        }
    }

    //method validate for Full Name
    private boolean validateFullName() {
        String val = editTextName.getText().toString();

        if (val.isEmpty()){
            editTextName.setError("Please fill in each field!");
            return false;
        }
        else{
            editTextName.setError(null);
            return true;
        }
    }

    //method validate for Sub Speciality
    private boolean validateSubSpecialty() {
        String val = editTextSubSpeciality.getText().toString();

        if (val.isEmpty()){
            editTextSubSpeciality.setError("Please enter Sub Specialty!");
            return false;
        }
        else{
            editTextSubSpeciality.setError(null);
            return true;
        }
    }

    //method validate for Email
    private boolean validateEmail() {
        String val = editTextEmail.getText().toString();
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            editTextEmail.setError("Please fill in each field!");
            Toast toast = Toast.makeText(getApplicationContext(), "Please fill in each field!", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
            toast.show(); // display the Toast
            return false;
        }
        /**
         else if(val.matches(emailPattern)){
         editTextEmail.setError("Please enter email siswa!");
         Toast toast = Toast.makeText(getApplicationContext(), "Please enter email siswa!", Toast.LENGTH_LONG); // initiate the Toast with context, message and duration for the Toast
         toast.show(); // display the Toast
         return false;
         }**/
        else{
            editTextEmail.setError(null);
            return true;
        }
    }

    //method validate for Phone Number
    private boolean validatePhone() {
        String val = editTextPhone.getText().toString();

        if (val.isEmpty()){
            editTextPhone.setError("Please fill in each field!");
            return false;
        }
        else{
            editTextPhone.setError(null);
            return true;
        }
    }




    //onClick function when user click register button or login button
    @Override
    public void onClick(View view) {
        if (view == ButtonSignUp)
            registerUser();
        if(view == Login)
            startActivity(new Intent(this, Login.class));
    }

    //intent to register new surgeon
    public void AddNewSurgeonName(View view) {
        startActivity(new Intent(this, NewSurgeon.class));
    }
}