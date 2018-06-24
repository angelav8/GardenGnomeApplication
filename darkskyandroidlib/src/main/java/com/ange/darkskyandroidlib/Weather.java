package com.example.ange.gardengnome;

import com.example.ange.gardengnome.darkskyandroidlib.models.Request;
import com.example.ange.gardengnome.darkskyandroidlib.models.WeatherResponse;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

public interface Weather {

    @GET("/{request}")
    void getWeather(@Path("request") Request params, @QueryMap Map<String,String> query, Callback<WeatherResponse> cb);

    @GET("/{request}")
    Observable<WeatherResponse> getWeather(@Path("request") Request params, @QueryMap Map<String,String> query);

}
