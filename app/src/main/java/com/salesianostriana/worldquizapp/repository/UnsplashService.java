package com.salesianostriana.worldquizapp.repository;

import com.salesianostriana.worldquizapp.model.unsplash.Image;
import com.salesianostriana.worldquizapp.model.unsplash.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UnsplashService {

    @GET("search/photos")
    Call<Image> getImagesUnsplash(@Query("query") String nameCountry, @Query("page") String numPag);
}
