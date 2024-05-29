package com.example.karaduser.NetworkController;


import com.example.karaduser.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MyConfig {
    public static final String JSON_BASE_URL = BuildConfig.API_URL;
    public static Retrofit retrofit;
    public static Retrofit getRetrofit() {
        Gson gson = new GsonBuilder().setLenient().create();
       if(retrofit==null)
       {
           OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                   .connectTimeout(60, TimeUnit.SECONDS)
                   .readTimeout(60, TimeUnit.SECONDS)
                   .writeTimeout(60, TimeUnit.SECONDS);

           HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
           if (BuildConfig.DEBUG) {
               // Only add logging interceptor in debug mode
               loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
           }else{
               loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
           }
           httpClientBuilder.addInterceptor(loggingInterceptor);

           retrofit = new Retrofit.Builder()
                   .baseUrl(JSON_BASE_URL)  // Assuming you have set API_URL in your BuildConfig
                   .client(httpClientBuilder.build())
                   .addConverterFactory(GsonConverterFactory.create(gson))
                   .build();
       }

        return retrofit;
    }

}
