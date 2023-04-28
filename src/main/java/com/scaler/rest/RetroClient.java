package com.scaler.rest;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private JSONPlaceHolderApi api;

    public RetroClient(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        api = retrofit.create(JSONPlaceHolderApi.class);
    }

    public  JSONPlaceHolderApi getApi(){
        return api;
    }
}
