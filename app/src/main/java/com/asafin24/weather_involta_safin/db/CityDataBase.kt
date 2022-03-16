package com.asafin24.weather_involta_safin.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.asafin24.weather_involta_safin.db.dao.CityDao
import com.asafin24.weather_involta_safin.model.CityModel


@Database(entities = [CityModel::class], version = 1)
abstract class CityDataBase: RoomDatabase() {
    abstract fun getCityDao(): CityDao

    companion object {
        private var database: CityDataBase ?= null

        @Synchronized
        fun getAdd(context: Context): CityDataBase {
            return if (database == null) {
                database = Room.databaseBuilder(context, CityDataBase::class.java, "db_city").build()
                database as CityDataBase
            } else {
                database as CityDataBase
            }
        }
    }
}