package com.asafin24.weather_involta_safin.db.repository

import androidx.lifecycle.LiveData
import com.asafin24.weather_involta_safin.db.dao.CityDao
import com.asafin24.weather_involta_safin.model.CityModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class CityRealization(private val cityDao: CityDao): CityRepository {
//    override val allCity: LiveData<MutableList<CityModel>>
//        get() = cityDao.getAllCity()

    override suspend fun addCity(cityModel: CityModel, onSuccess: () -> Unit) {
        cityDao.add(cityModel)
        onSuccess()
    }

    override suspend fun editCity(cityModel: CityModel, onSuccess: () -> Unit) {
        cityDao.editCity(cityModel)
        onSuccess()
    }

    override fun searchDataBase(cityName: String) : Flow<List<CityModel>> {
        return cityDao.searchDataBase(cityName)
    }

}