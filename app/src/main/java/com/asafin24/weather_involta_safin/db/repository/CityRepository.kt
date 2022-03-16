package com.asafin24.weather_involta_safin.db.repository

import androidx.lifecycle.LiveData
import com.asafin24.weather_involta_safin.model.CityModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CityRepository {
   // val allCity: LiveData<MutableList<CityModel>>
    suspend fun addCity(cityModel: CityModel, onSuccess:() -> Unit)
    suspend fun editCity(cardModel: CityModel, onSuccess: () -> Unit)
    fun searchDataBase(cityName: String): Flow<List<CityModel>>
}