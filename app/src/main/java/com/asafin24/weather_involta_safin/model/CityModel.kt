package com.asafin24.weather_involta_safin.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity (tableName = "city_table")
data class CityModel (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo
    var cityName: String,

    @ColumnInfo
    var lastTemp: String = "- °C",

    @ColumnInfo
    var lastLikeTemp: String = "Ощущается как - °C",

    @ColumnInfo
    var lastStatus: String = "-",

    @ColumnInfo
    var lastHumidity: String = "- %",

    @ColumnInfo
    var lastAtmPresSea: String = "- мм. рт. ст",

    @ColumnInfo
    var lastAtmPresGnd: String = "- мм. рт. ст",

    @ColumnInfo
    var lastWindSpeed: String = "- м/с",

    @ColumnInfo
    var lastWindDeg: String = "-",

    @ColumnInfo
    var dataUpdate: String = "-"
) : Serializable

