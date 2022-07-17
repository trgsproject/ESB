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

public class MyAdapterSurgeon extends ArrayAdapter<User> {

    Context context;
    List<User> arrayListSurgeon;

    public MyAdapterSurgeon(@NonNull Context context, List<User> arrayListSurgeon) {
        super(context, R.layout.custom_list_user, arrayListSurgeon);

        this.context = context;
        this.arrayListSurgeon = arrayListSurgeon;

    }

    @Override
    public int getCount() {

        return arrayListSurgeon.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_user,null,true);

        TextView tvName = view.findViewById(R.id.txtNameOfUser);

        tvName.setText(arrayListSurgeon.get(position).getName());

        return view;




    }
}
