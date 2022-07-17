package com.example.esa.api;

import com.example.esa.model.PushNotification;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.example.esa.Constants.CONTENT_TYPE;
import static com.example.esa.Constants.SERVER_KEY;

public interface ApiInterface {

    @Headers({"Authorization: key=" + SERVER_KEY, "Content-Type:" + CONTENT_TYPE })
    @POST("fcm/send")
    Call<PushNotification> sendNotification(@Body PushNotification notification);

}

