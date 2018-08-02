package com.textmessaging.sau.textmessaging.retrofit;

import com.textmessaging.sau.textmessaging.pojo.LogInResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface ApiInterface {

    @FormUrlEncoded
    @POST("Send_otp")
    Call<LogInResponse> getCountryList(@Field("contact_no") String contact);
}