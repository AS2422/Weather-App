package com.asafin24.weather_involta_safin.screens.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.asafin24.weather_involta_safin.APP
import com.asafin24.weather_involta_safin.R
import com.asafin24.weather_involta_safin.databinding.FragmentWeatherBinding
import com.asafin24.weather_involta_safin.model.CityModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_weather.*


class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    lateinit var binding: FragmentWeatherBinding
    lateinit var currentCity: CityModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()

        val city = currentCity.cityName
        loadingFromDB(city)

        viewModel.onRefresh(city)
        getLiveData()

        //свайп вниз
        binding.srl.setOnRefreshListener {
            viewModel.onRefresh(city)
            getLiveData()
            binding.srl.isRefreshing = false
        }
        //
    }

    override fun onPause() {
        editCity()
        super.onPause()
    }

    private fun init() {
        val cityName = arguments?.getSerializable("city") as String
        currentCity = CityModel(cityName = cityName)
        binding.btnSelectCity.text = currentCity.cityName

        viewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
        viewModel.initDataBase()

        binding.ibBack.setOnClickListener {
            APP.navController.navigate(R.id.action_weatherFragment_to_startFragment)
        }

        //Системная кнопка "Назад"
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                APP.navController.navigate(R.id.action_weatherFragment_to_startFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        //
    }

    private fun getLiveData() {

        viewModel.data.observe(viewLifecycleOwner, Observer { data ->
            data?.let {
                binding.tvCurrentTemp.text = String.format("%.0f", data.main.temp) + " °C"
                binding.tvFeltTemp.text = "Ощущается как " + String.format("%.0f", data.main.feels_like) + " °C"
                binding.tvStatus.text = data.weather[0].description
                binding.tvHumidityValue.text = data.main.humidity.toString() + " %"
                binding.tvAtmPressureSeaValue.text = data.main.sea_level.toString() + " мм. рт. ст."
                binding.tvAtmPressureEarthValue.text = data.main.grnd_level.toString() + " мм. рт. ст."
                binding.tvWindValue.text = data.wind.speed.toString() + " м/с"
                binding.tvDataUpdate.text = "Обновлено: " + java.text.SimpleDateFormat("dd.MM.YYYY HH:mm:ss").format(java.util.Date(data.dt.toLong() * 1000))

                binding.tvWindDirection.text =
                    if (data.wind.deg in 350..360  || data.wind.deg in 0..10) "Восточный"
                    else if (data.wind.deg in 10..80) "Северо-восточный"
                    else if (data.wind.deg in 80..100) "Северный"
                    else if (data.wind.deg in 100..170) "Северо-западный"
                    else if (data.wind.deg in 170..190) "Западный"
                    else if (data.wind.deg in 190..260) "Юго-западный"
                    else if (data.wind.deg in 260..280) "Южный"
                    else "Юго-западный"

                Glide.with(this)
                    .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                    .into(iv_status)
            }
            setBackground()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (error) Toast.makeText(context, "Ошибка при загрузке данных. Проверьте интернет-соединение", Toast.LENGTH_SHORT).show()
            }
            setBackground()
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (loading) {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.tvDataUpdate.visibility = View.GONE

                } else {
                    binding.pbLoading.visibility = View.GONE
                    binding.tvDataUpdate.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun loadingFromDB(city: String) {
        viewModel.searchDatabase(city).observe(viewLifecycleOwner) { list ->
            list?.let {
                if (it.isNotEmpty()) {
                    currentCity = it[0]
                    binding.tvCurrentTemp.text = currentCity.lastTemp
                    binding.tvFeltTemp.text = currentCity.lastLikeTemp
                    binding.tvStatus.text = currentCity.lastStatus
                    binding.tvHumidityValue.text = currentCity.lastHumidity
                    binding.tvAtmPressureSeaValue.text = currentCity.lastAtmPresSea
                    binding.tvAtmPressureEarthValue.text = currentCity.lastAtmPresGnd
                    binding.tvWindValue.text = currentCity.lastWindSpeed
                    binding.tvWindDirection.text = currentCity.lastWindDeg
                    binding.tvDataUpdate.text = currentCity.dataUpdate
                } else {
                    viewModel.addCity(CityModel(cityName = city)) {}
                    loadingFromDB(city)
                }
            }
        }
    }

    private fun editCity() {
        currentCity.lastTemp = binding.tvCurrentTemp.text.toString()
        currentCity.lastHumidity = binding.tvHumidityValue.text.toString()
        currentCity.lastLikeTemp = binding.tvFeltTemp.text.toString()
        currentCity.lastAtmPresSea = binding.tvAtmPressureSeaValue.text.toString()
        currentCity.lastAtmPresGnd = binding.tvAtmPressureEarthValue.text.toString()
        currentCity.lastStatus = binding.tvStatus.text.toString()
        currentCity.lastWindSpeed = binding.tvWindValue.text.toString()
        currentCity.lastWindDeg = binding.tvWindDirection.text.toString()
        currentCity.dataUpdate = binding.tvDataUpdate.text.toString()
        viewModel.editCity(currentCity) {}
    }

    private fun setBackground() {

        binding.srl.setBackgroundResource(when (binding.tvStatus.text) {
            "ясно" -> R.drawable.background_clear
            "пасмурно" -> R.drawable.background_cloudy
            "облачно с прояснениями", "переменная облачность", "небольшая облачность"  -> R.drawable.background_few_cloudy
            "дождь", "гроза", "небольшой дождь", "туман" -> R.drawable.background_rain
            "снег", "небольшой снег" -> R.drawable.background_snow
            else -> R.drawable.background_clear
        })
    }
}