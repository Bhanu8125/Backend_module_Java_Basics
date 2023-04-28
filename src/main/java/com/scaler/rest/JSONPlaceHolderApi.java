package com.scaler.rest;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface JSONPlaceHolderApi {
    @GET("/photos")
    Call<List<Photo>> getPhotos();
}
