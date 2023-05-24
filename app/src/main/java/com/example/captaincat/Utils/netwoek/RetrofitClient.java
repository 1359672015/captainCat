package com.example.captaincat.Utils.netwoek;

/**
 * FileName: RetrofitClient
 * Founder: howie
 * Create Date: 2022/3/6 19:23
 * Profile:


public class RetrofitClient {

    // TODO: 2022/3/6 改成自己的url
    private static final String BASE_URL = "http://192.168.56.1:8080/pagingserver_war/";
    private static RetrofitClient mInstance;
    private Retrofit retrofit;


    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .client(getClient())
                .build();
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder().build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }

    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
 */