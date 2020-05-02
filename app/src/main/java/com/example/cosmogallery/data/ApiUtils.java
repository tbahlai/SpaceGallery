package com.example.cosmogallery.data;

import com.example.cosmogallery.BuildConfig;
import com.example.cosmogallery.data.api.ApiKeyInterceptor;
import com.example.cosmogallery.data.api.NasaApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiUtils {

    private static OkHttpClient client;
    private static Gson gson;
    private static Retrofit retrofit;
    private static NasaApi nasaApi;

    public static final List<Class<? extends IOException>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            ConnectException.class,
            SocketTimeoutException.class
    );

    public static OkHttpClient getClient() {
        if (client == null) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.addInterceptor(new ApiKeyInterceptor());
            if (!BuildConfig.BUILD_TYPE.contains("release")) {
                builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
            }
            client = builder.build();
        }
        return client;
    }

    public static Retrofit getRetrofit() {
        if (gson == null)
            gson = new Gson();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static NasaApi getNasaApi() {
        if (nasaApi == null)
            nasaApi = getRetrofit().create(NasaApi.class);
        return nasaApi;
    }

}
