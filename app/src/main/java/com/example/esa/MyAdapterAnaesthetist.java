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

public class MyAdapterAnaesthetist extends ArrayAdapter<User> {

    Context context;
    List<User> arrayListAnaesthetist;

    public MyAdapterAnaesthetist(@NonNull Context context, List<User> arrayListAnaesthetist) {
        super(context, R.layout.custom_list_user, arrayListAnaesthetist);

        this.context = context;
        this.arrayListAnaesthetist = arrayListAnaesthetist;

    }

    @Override
    public int getCount() {

        return arrayListAnaesthetist.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_user,null,true);

        TextView tvName = view.findViewById(R.id.txtNameOfUser);

        tvName.setText(arrayListAnaesthetist.get(position).getName());

        return view;




    }
}
