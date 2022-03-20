package com.asafin24.weather_involta_safin.api

import com.asafin24.weather_involta_safin.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("data/2.5/weather?appid=77b25b683547c602c1484577e67bb360&units=metric&lang=ru")

    fun getCity(@Query("q") cityName: String): Single<WeatherModel>

}