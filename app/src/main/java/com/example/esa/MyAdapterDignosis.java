package com.example.esa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyAdapterDignosis extends ArrayAdapter<Diagnosis> {

    Context context;
    List<Diagnosis> arrayListPatient;


    public MyAdapterDignosis(@NonNull Context context, List<Diagnosis> arrayListPatient) {
        super(context, R.layout.custom_list_diagnosis_item,arrayListPatient);

        this.context = context;
        this.arrayListPatient = arrayListPatient;

    }

    @Override
    public int getCount() {
        return arrayListPatient.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_diagnosis_item,null,true);

        TextView tvName = view.findViewById(R.id.txt_name);
        TextView tvStatus = view.findViewById(R.id.textApprovedStatus);
        TextView tvSurgeonName = view.findViewById(R.id.textSurgeonName);

        tvName.setText(arrayListPatient.get(position).getName());
        tvStatus.setText(arrayListPatient.get(position).getApproved_status());
        tvSurgeonName.setText(arrayListPatient.get(position).getSurgical_team());

        return view;

    }
}
