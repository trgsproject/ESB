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

public class MyAdapterPatient  extends ArrayAdapter<Patient> {

    Context context;
    List<Patient> arrayListPatient;


    public MyAdapterPatient(@NonNull Context context, List<Patient> arrayListPatient) {
        super(context, R.layout.custom_list_item,arrayListPatient);

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

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,null,true);

        TextView tvName = view.findViewById(R.id.txt_name);
        TextView tvStatus = view.findViewById(R.id.textStatus);
        TextView tvTimeArrival = view.findViewById(R.id.textTimeArrival);

        tvName.setText(arrayListPatient.get(position).getName());
        tvStatus.setText(arrayListPatient.get(position).getStatus());
        tvTimeArrival.setText(arrayListPatient.get(position).getArrivalTime());

        return view;

    }
}
