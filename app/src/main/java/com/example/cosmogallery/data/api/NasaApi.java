package com.example.cosmogallery.data.api;

import com.example.cosmogallery.data.model.NasaApod;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NasaApi {

    @GET("planetary/apod")
    Single<List<NasaApod>> getApodCountPhotos(@Query("api_key") String api_key,
                                                   @Query("count") Integer count,
                                                   @Query("hd") Boolean hd);
}
