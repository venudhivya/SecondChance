package com.secondchance.service;



import com.secondchance.model.registerresponsemodel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface RetrofitApi {


    @GET("/{path}")
    Call<registerresponsemodel> getregisterResponse(@Path(value = "path", encoded = true) String pathurl,
                                                @Query("email") String email,
                                                @Query("password") String password,
                                                @Query("phone_number") String phone_number);



}
