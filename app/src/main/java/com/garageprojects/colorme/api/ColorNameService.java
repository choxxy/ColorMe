package com.garageprojects.colorme.api;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ColorNameClient {

    private static final String BASE_URL = "https://api.color.pizza/v1/";

    public static ColorNameService getRetrofitInstance() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        retrofit.create(ColorNameService.class);
    }
}
