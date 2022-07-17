package com.example.esa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class RegisterPatient extends AppCompatActivity {

    private TextView textViewUsersubsp, textViewUserfullname;
    Button button_capture,button_Capture_Manually;
    Bitmap bitmap;
    TextView textview_data;
    private static final int REQUEST_CAMERA_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);

        //assigned variables
        button_capture = findViewById(R.id.button_capture);
        textViewUsersubsp = (TextView) findViewById(R.id.textViewUsersubsp);
        textViewUserfullname = (TextView) findViewById(R.id.textViewUserfullname);
        textview_data = findViewById(R.id.text_data);
        button_Capture_Manually = findViewById(R.id.button_register_manually);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //navigation bottom
        bottomNavigationView.setSelectedItemId(R.id.register);

        //for users that loggedin
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Login.class));
        }

        //get data user
        textViewUserfullname.setText(SharedPrefManager.getInstance(this).getUserFullname());
        textViewUsersubsp.setText(SharedPrefManager.getInstance(this).getUserSubspeciality());

        //bottom navigation method
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.register:
                        return true;

                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(), Setting.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


        if (ContextCompat.checkSelfPermission(RegisterPatient.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterPatient.this, new String[]{
                    Manifest.permission.CAMERA
            }, REQUEST_CAMERA_CODE);
        }

        //register with mrn code
        button_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(RegisterPatient.this);

            }
        });

        //register manually
        button_Capture_Manually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterPatient.this,RegisterPatientManually.class);
                startActivity(i);

            }
        });

    }

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

    private void getTextFromImage(Bitmap bitmap) {
        TextRecognizer recognizer = new TextRecognizer.Builder(this).build();
        if (!recognizer.isOperational()) {
            Toast.makeText(this, "Error Occur", Toast.LENGTH_SHORT).show();
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < textBlockSparseArray.size(); i++) {
                TextBlock textBlock = textBlockSparseArray.valueAt(i);
                stringBuilder.append(textBlock.getValue());
                stringBuilder.append("\n");

            }

            //get text scanner
            String s = stringBuilder.toString();
            String removesSpace = s.replace("\n", ",");
            String removesDot = removesSpace.replace(".", ",");
            String removesSpacesComma = removesDot.replace(", ", ",");
            String[] split = removesSpacesComma.split(",");

            String s1 = split[0]; //id
            String s2 = split[1]; //name
            String s3 = split[2]; //username
            String s4 = split[3]; //age
            String s5 = split[4]; //gender
            String s6 = split[5]; //ic
            String s7 = split[6]; //nation

            Intent intent = new Intent(RegisterPatient.this, RegisterPatientDetails.class);
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