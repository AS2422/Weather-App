package com.asafin24.weather_involta_safin.screens.weather

import android.app.Application
import androidx.lifecycle.*
import com.asafin24.weather_involta_safin.REPOSITORY
import com.asafin24.weather_involta_safin.api.WeatherAPIService
import com.asafin24.weather_involta_safin.db.CityDataBase
import com.asafin24.weather_involta_safin.db.repository.CityRealization
import com.asafin24.weather_involta_safin.model.CityModel
import com.asafin24.weather_involta_safin.model.WeatherModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val apiService = WeatherAPIService()
    private val disposable = CompositeDisposable()

    val data = MutableLiveData<WeatherModel>()
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()


    fun onRefresh(cityName: String) {
        getDataFromAPI(cityName)
    }

    private fun getDataFromAPI(cityName: String) {

        loading.value = true
        disposable.add(
            apiService.getDataService(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {

                    override fun onSuccess(t: WeatherModel) {
                        data.value = t
                        error.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        error.value = true
                        loading.value = false
                    }
                })
        )
    }

    val context = application
    fun initDataBase(){
        val daoCity = CityDataBase.getAdd(context).getCityDao()
        REPOSITORY = CityRealization(daoCity)
    }

    fun editCity(cityModel: CityModel, onSuccess:() -> Unit) =
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.editCity(cityModel){
                onSuccess()
            }
        }

    fun addCity(cityModel: CityModel, onSuccess:() -> Unit) =
        viewModelScope.launch(Dispatchers.IO){
            REPOSITORY.addCity(cityModel){
                onSuccess()
            }
        }

    fun searchDatabase(cityName: String) : LiveData<List<CityModel>> {
        return REPOSITORY.searchDataBase(cityName).asLiveData()
    }
}