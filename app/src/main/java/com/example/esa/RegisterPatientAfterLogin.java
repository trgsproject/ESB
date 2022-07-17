package com.example.esa;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class RegisterPatientAfterLogin extends AppCompatActivity {

    private TextView textViewUserName, textViewUserSubs,textViewUserFullName;
    TextView button_close;

    Button button_capture, button_Capture_Manually;
    TextView textViewRole;
    Bitmap bitmap;
    private static final int REQUEST_CAMERA_CODE = 100;
    float v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient_after_login);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.green)));
        getSupportActionBar().setTitle( "Hi, " + SharedPrefManager.getInstance(this).getUserRole());

        //assigned variables
        button_capture = findViewById(R.id.button_capture);
        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        textViewUserSubs = (TextView) findViewById(R.id.textViewUserSubspe);
        textViewUserFullName = (TextView)findViewById(R.id.textViewUserfullname);
        textViewRole = findViewById(R.id.text_data);
        button_Capture_Manually = findViewById(R.id.button_register_manually);
        button_close = findViewById(R.id.close);

        //for users that loggedin
        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, Login.class));
        }

        //get data user
        textViewUserName.setText(SharedPrefManager.getInstance(this).getUsername());
        textViewUserFullName.setText(SharedPrefManager.getInstance(this).getUserFullname());
        textViewUserSubs.setText(SharedPrefManager.getInstance(this).getUserSubspeciality());
        textViewRole.setText(SharedPrefManager.getInstance(this).getUserRole());


        //method for request camera
        if (ContextCompat.checkSelfPermission(RegisterPatientAfterLogin.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(RegisterPatientAfterLogin.this,new  String[]{
                    Manifest.permission.CAMERA
            },REQUEST_CAMERA_CODE);
        }


        //close button intent to dashboard to multi-user dashboard
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( textViewRole.getText().toString().equals("Anaesthetist")) {
                    Intent a = new Intent(RegisterPatientAfterLogin.this,Dashboard.class);
                    Toast.makeText(RegisterPatientAfterLogin.this, "Welcome Back, Anaesthetist", Toast.LENGTH_SHORT).show();
                    a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(a);
                }else if( textViewRole.getText().toString().equals("OT Team")){
                    Intent b = new Intent(RegisterPatientAfterLogin.this,DashboardNurse.class);
                    Toast.makeText(RegisterPatientAfterLogin.this, "Welcome Back, Nurse", Toast.LENGTH_SHORT).show();
                    b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(b);
                }else if( textViewRole.getText().toString().equals("Surgical Team")) {
                    Intent b = new Intent(RegisterPatientAfterLogin.this, DashboardSurgeon.class);
                    Toast.makeText(RegisterPatientAfterLogin.this, "Welcome Back, Surgeon", Toast.LENGTH_SHORT).show();
                    b.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(b);
                }

            }
        });


        //scan mrn code patient
        button_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(RegisterPatientAfterLogin.this);

            }
        });


        //register manually
        button_Capture_Manually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterPatientAfterLogin.this,RegisterPatientManually.class);
                startActivity(i);

            }
        });

    }


    //method for crop image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {

                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    getTextFromImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    //method get text from image
    private void getTextFromImage(Bitmap bitmap){
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if (!recognizer.isOperational()){
            Toast.makeText(this, "Error Occur", Toast.LENGTH_SHORT).show();
        }
        else{
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i =0;i< textBlockSparseArray.size();i++){
                TextBlock textBlock =textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");

            }

            //get text scanner
            String s = stringBuilder.toString();
            String removesSpace = s.replace("\n", ",");
            String removesDot = removesSpace.replace(".", ",");
            String removesSpacesComma = removesDot.replace(", ", ",");
            String [] split = removesSpacesComma.split(",");

            String s1 = split[0]; //id
            String s2 = split[1]; //name
            String s3 = split[2]; //username
            String s4 = split[3]; //age
            String s5 = split[4]; //gender
            String s6 = split[5]; //ic
            String s7 = split[6]; //nation

            Intent intent = new Intent(RegisterPatientAfterLogin.this, RegisterPatientDetails.class);
            intent.putExtra("s1", s1);
            intent.putExtra("s2", s2);
            intent.putExtra("s3", s3);
            intent.putExtra("s4", s4);
            intent.putExtra("s5", s5);
            intent.putExtra("s6", s6);
            intent.putExtra("s7", s7);
            startActivity(intent);

        }
    }

}