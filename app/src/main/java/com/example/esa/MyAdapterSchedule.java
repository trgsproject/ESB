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

public class MyAdapterSchedule extends ArrayAdapter<Schedule> {

    Context context;
    List<Schedule> arrayListSchedule;


    public MyAdapterSchedule(@NonNull Context context, List<Schedule> arrayListSchedule) {
        super(context, R.layout.custom_list_schedule_list_item,arrayListSchedule);

        this.context = context;
        this.arrayListSchedule = arrayListSchedule;

    }

    @Override
    public int getCount() {
        return arrayListSchedule.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_schedule_list_item,null,true);

        TextView tvName = view.findViewById(R.id.txtName);
        TextView tvCategory = view.findViewById(R.id.textCategory);
        TextView tvOTRoom = view.findViewById(R.id.textOTRoom);

        tvName.setText(arrayListSchedule.get(position).getPatientName());
        tvCategory.setText(arrayListSchedule.get(position).getCategory());
        tvOTRoom.setText(arrayListSchedule.get(position).getOT());

        return view;

    }
}
