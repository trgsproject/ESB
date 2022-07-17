package com.example.esa.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.esa.Constants.BASE_URL;

public class ApiUtilities {
    private static Retrofit retrofit = null;

    public static ApiInterface getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return  retrofit.create(ApiInterface.class);
    }
}

