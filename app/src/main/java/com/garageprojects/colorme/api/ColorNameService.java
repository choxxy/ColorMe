package com.garageprojects.colorme.api;

import android.text.TextUtils;

import androidx.palette.graphics.Palette;

import com.garageprojects.colorme.colorutil.ColorUtils;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ColorNameService {

    private static int HEX_LEN  = 6;
    private static final String BASE_URL = "https://api.color.pizza/v1/";


    public ColorNameService() {
    }

    private ColorNameApi getApi() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofit.create(ColorNameApi.class);
    }

    public void getColorNames(List<Palette.Swatch> swatches, ResultCallback callback) {

        List<String> colors = new ArrayList<>();

        for (Palette.Swatch swatch : swatches) {
            //padding color because toHexString removes leading zeros
            String color = "000000" + Integer.toHexString(swatch.getRgb() & 0xffffff);
            color = color.substring(color.length() - HEX_LEN);
            colors.add(color);
        }

        String hexValues = TextUtils.join(",", colors);
        
        getApi().getNames(hexValues).enqueue(new Callback<Colors>() {
            @Override
            public void onResponse(Call<Colors> call, Response<Colors> response) {
                if (response.isSuccessful() && response.body().getColors() != null) {
                        callback.onResults(response.body().getColors());
                } else {
                    List<ColorInfo> list = ColorUtils.mapColor(swatches);
                    callback.onResults(list);
                }
            }

            @Override
            public void onFailure(Call<Colors> call, Throwable t) {
                List<ColorInfo> mergedList = ColorUtils.mapColor(swatches);
                callback.onResults(mergedList);
            }
        });
    }


}
