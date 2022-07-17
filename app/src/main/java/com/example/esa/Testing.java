package com.example.esa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.esa.api.ApiUtilities;
import com.example.esa.model.NotificationData;
import com.example.esa.model.PushNotification;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.esa.Constants.TOPIC;

public class Testing extends AppCompatActivity  {

    AutoCompleteTextView ed_ProcedureName2,ed_ProcedureName3;

    TextView buttonAddProcedure;

    ImageView image_remove1, image_remove2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        getSupportActionBar().hide();

        ed_ProcedureName2 = findViewById(R.id.ed_ProcedureName2);
        ed_ProcedureName3 = findViewById(R.id.ed_ProcedureName3);
        buttonAddProcedure = findViewById(R.id.buttonAddProcedure);
        image_remove1 = findViewById(R.id.image_remove1);
        image_remove2 = findViewById(R.id.image_remove2);

    }

    public void btn_updateData(View view) {
    }

    public void AddProcedure(View view) {
        ed_ProcedureName2.setVisibility(View.VISIBLE);
        image_remove1.setVisibility(View.VISIBLE);
        ed_ProcedureName3.setVisibility(View.VISIBLE);
        image_remove2.setVisibility(View.VISIBLE);
        buttonAddProcedure.setVisibility(View.GONE);

    }

    public void RemoveProcedureName2(View view) {
        ed_ProcedureName2.setVisibility(View.GONE);
        image_remove1.setVisibility(View.GONE);
        buttonAddProcedure.setVisibility(View.VISIBLE);
    }

    public void RemoveProcedureName3(View view) {
        ed_ProcedureName3.setVisibility(View.GONE);
        image_remove2.setVisibility(View.GONE);
        buttonAddProcedure.setVisibility(View.VISIBLE);
    }
}

