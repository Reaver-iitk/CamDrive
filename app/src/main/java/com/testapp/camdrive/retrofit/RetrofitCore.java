package com.testapp.camdrive.retrofit;

import android.content.Context;

import com.testapp.camdrive.cookie.MyCookieJar;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitCore {



    public static Retrofit retrofit = null;
    private static final String ROOT_URL = "https://ms1.dev.camdrive.org";

    public static Retrofit getRetrofitInstance(Context context)
    {
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new MyCookieJar());
        builder.addInterceptor(logging);
        client = builder.build();


        if(retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(ROOT_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    public static ApiService getApiService(Context context){
        return getRetrofitInstance(context).create(ApiService.class);
    }
}