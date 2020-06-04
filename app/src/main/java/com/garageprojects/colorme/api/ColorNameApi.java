package com.garageprojects.colorme.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface  ColorNameApi {

    @GET("{hexValues}")
    Call<Colors> getNames(@Path("hexValues") String hexValues);
}
