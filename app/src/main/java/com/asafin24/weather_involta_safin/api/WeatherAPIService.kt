package com.asafin24.weather_involta_safin.api

import com.asafin24.weather_involta_safin.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {

    val api = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataService(cityName: String): Single<WeatherModel> {
        return api.getCity(cityName)
    }

}