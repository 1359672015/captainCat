package com.example.captaincat.api.core;

import static com.example.captaincat.information.Config.Config.baseUrl;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {
    static RetrofitService instance;
    Retrofit retrofit;
    OkHttpClient client;
    ApiService apiService;
/**
 * 单例模式、建造者模式*/
    private RetrofitService(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("NetInfoLog",message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))//关键，不然不能获取实例。
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//
                .addCallAdapterFactory(LiveDataCallAdapterFactory.create())//
                // .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }
   public static void  iniService(){
        if (instance==null)
            instance = new RetrofitService();
        else return;
    }
    public static RetrofitService getInstance(){
        return instance;
    }

    public ApiService getApiService(){
        return apiService;
    }

}
