package com.secondchance.service;



import com.google.gson.JsonObject;
import com.secondchance.model.registerresponsemodel;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetrofitApi {

    @Headers("Content-Type: application/json")
    @POST("create_user")
    Call<registerresponsemodel> getregisterResponse(@Body JSONObject jsonObject);



}
