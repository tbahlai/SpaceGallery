package com.example.cosmogallery.data.api;

import com.example.cosmogallery.data.model.NasaApod;
import com.example.cosmogallery.data.model.NasaDatePhoto;
import com.example.cosmogallery.data.model.NasaPhoto;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NasaApi {

    @GET("EPIC/api/natural/all")
    Single<List<NasaDatePhoto>> getDatesWithPhoto();

    @GET("EPIC/api/natural/date/{date}")
    Single<List<NasaPhoto>> getPhotosEPIC(@Path("date") String date);

    @GET("EPIC/api/enhanced/date/{date}")
    Single<List<NasaPhoto>> getPhotosEnhancedEPIC(@Path("date") String date);

    @GET("planetary/apod")
    Single<List<NasaApod>> getApodCountPhotos(@Query("api_key") String api_key,
                                              @Query("count") Integer count,
                                              @Query("hd") Boolean hd);
}
