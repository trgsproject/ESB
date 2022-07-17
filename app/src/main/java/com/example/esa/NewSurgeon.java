package com.example.esa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

public class NewSurgeon extends AppCompatActivity {

    EditText textSurgeonName;
    AutoCompleteTextView textSubSpeciality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_surgeon);
        getSupportActionBar().hide();

        //set string for autoComplete
        String[] SubSpecialty = getResources().getStringArray(R.array.SubSpeciality);

        //declare variables
        textSurgeonName = findViewById(R.id.textSurgeonName);
        textSubSpeciality = findViewById(R.id.textSubSpeciality);

        //for autocomplete SubSpecialty
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, SubSpecialty);
        textSubSpeciality.setAdapter(adapter1);

    }

    //insert new surgeon into database
    public void InsertSurgeon(View view) {

        final String surgeon_name = textSurgeonName.getText().toString().trim().toUpperCase();
        final String sub_speciality = textSubSpeciality.getText().toString().trim().toUpperCase();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if(surgeon_name.isEmpty()){
            Toast.makeText(this, "Please enter your full name", Toast.LENGTH_SHORT).show();
            textSurgeonName.setError("Please enter your full name");
            return;
        }
        else if(sub_speciality.isEmpty()){
            Toast.makeText(this, "Please enter your sub specialty", Toast.LENGTH_SHORT).show();
            textSubSpeciality.setError("Please enter your sub specialty");
            return;
        }


        else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST,  URL.urls + "insertNewSurgeon.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("Data Inserted")){

                                Intent i = new Intent(NewSurgeon.this,Login.class);
                                Toast.makeText(NewSurgeon.this, "New Surgeon successfully register!", Toast.LENGTH_SHORT).show();
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                progressDialog.dismiss();

                            }
                            else{
                                Toast.makeText(NewSurgeon.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(NewSurgeon.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();

                    params.put("surgeon_name",surgeon_name);
                    params.put("sub_speciality",sub_speciality);

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(NewSurgeon.this);
            requestQueue.add(request);

        }

    }

    //intent back
    public void back(View view) {
        startActivity(new Intent(this, Login.class));
    }
}