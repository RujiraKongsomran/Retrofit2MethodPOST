package com.rujirakongsomran.retrofit2methodpost;

import com.rujirakongsomran.retrofit2methodpost.Model.Request;
import com.rujirakongsomran.retrofit2methodpost.Model.Response;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {

    @POST("api/v1/create")
    Call<Response> postCreate(@Body Request request);
}
