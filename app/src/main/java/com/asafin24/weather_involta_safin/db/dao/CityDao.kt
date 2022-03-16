package com.asafin24.weather_involta_safin.db.dao

import android.util.MutableBoolean
import androidx.lifecycle.LiveData
import androidx.room.*
import com.asafin24.weather_involta_safin.model.CityModel
import kotlinx.coroutines.flow.StateFlow

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(cityModel: CityModel)

//    @Query("SELECT * FROM city_table ORDER BY cityName ASC")
//    fun getAllCity() : LiveData<MutableList<CityModel>>

    @Update
    suspend fun editCity(cityModel: CityModel)

    @Query("SELECT * FROM city_table WHERE cityName LIKE :cityName")
    fun searchDataBase(cityName: String) : kotlinx.coroutines.flow.Flow<List<CityModel>>
}