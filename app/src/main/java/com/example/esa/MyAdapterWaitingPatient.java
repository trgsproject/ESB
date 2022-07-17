package com.example.esa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MyAdapterWaitingPatient extends ArrayAdapter<WaitingPatient> {

    Context context;
    List<WaitingPatient> arrayListWaitingPatient;



    public MyAdapterWaitingPatient(@NonNull Context context, List<WaitingPatient> arrayListWaitingPatient) {
        super(context, R.layout.custom_list_waiting_patient_item,arrayListWaitingPatient);

        this.context = context;
        this.arrayListWaitingPatient = arrayListWaitingPatient;


    }

    @Override
    public int getCount() {
        return arrayListWaitingPatient.size();
    }


    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        @SuppressLint("ViewHolder") View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_waiting_patient_item, null, true);

        TextView tvName = view.findViewById(R.id.txt_name);
        TextView tvCategory = view.findViewById(R.id.textCategory);
        TextView tvWaitingTime = view.findViewById(R.id.textDurationTime);
        TextView tvCurrentTime =view.findViewById(R.id.txtCurrentTime);
        TextView tvArrivalTime =view.findViewById(R.id.txtWaitingTime);

        tvName.setText(arrayListWaitingPatient.get(position).getPatientName());
        tvCategory.setText(arrayListWaitingPatient.get(position).getCategory());
        tvArrivalTime.setText(arrayListWaitingPatient.get(position).getArrivalTimeToSurgeon());
        tvCurrentTime.setText(getCurrentDate() + " " +getCurrentTime());

        //tvWaitingTime.setText(getCurrentDate() + " " +getCurrentTime() );

        String Time1 = tvArrivalTime.getText().toString().trim();
        String Time2 = tvCurrentTime.getText().toString().trim();


        // date format
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(Time1);
            d2 = format.parse(Time2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        double diff= d2.getTime() - d1.getTime();


        long diffSeconds =(int) (diff / 1000 % 60);
        long diffMinutes = (int) (diff / (60 * 1000) % 60);
        long diffHours = (int) (diff / (60 * 60 * 1000) % 24);
        long diffDays =(int) (diff / (24 * 60 * 60 * 1000));



        if(diffDays == 0 && diffHours == 0){
            tvWaitingTime.setText("Waiting Time: " + diffMinutes+ " min " +diffSeconds+ " sec");
        }

        else if(diffDays == 0 ){
            tvWaitingTime.setText("Waiting Time: " + diffHours+ " hrs " + diffMinutes+ " min " +diffSeconds+ " sec");
        }

        else{
            tvWaitingTime.setText("Waiting Time: " + diffDays + " days " + diffHours+ " hrs " +diffMinutes+ " min");
        }



        return view;
    }

    //method to get current date
    private String getCurrentTime(){
        return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    }

    //method to get current date
    private String getCurrentDate() {
        return new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).format(new Date());
    }



}
