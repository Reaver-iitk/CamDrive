package com.testapp.camdrive.retrofit;


import com.testapp.camdrive.retromodel.Login;
import com.testapp.camdrive.retromodel.OnlineCamera;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("/mobile/api_native/login/")
    Call<Login> getLogin(@Query("username") String username,
                         @Query("password") String password);

    @GET("/mobile/api_native/logout")
    Call<Login> getExit();

    @GET("/mobile/api_native/cameras")
    Call<OnlineCamera> getCamera();
}
