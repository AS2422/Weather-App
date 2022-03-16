package com.asafin24.weather_involta_safin.screens.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.asafin24.weather_involta_safin.APP
import com.asafin24.weather_involta_safin.R
import com.asafin24.weather_involta_safin.databinding.FragmentStartBinding
import com.asafin24.weather_involta_safin.model.CityModel
import com.asafin24.weather_involta_safin.screens.windows.ExitFragment


class StartFragment : Fragment() {

    lateinit var binding: FragmentStartBinding
    private val adapterCity = StartAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(layoutInflater, container, false)

        //выход из приложения
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val fragmentExit = ExitFragment()
                fragmentExit.show(((activity as FragmentActivity).supportFragmentManager),"exitDialog")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        //

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCity.adapter = adapterCity

    }

    companion object{
        fun clickCard(cityName: String) {
            val bundle = Bundle()
            bundle.putSerializable("city", cityName)
            APP.navController.navigate(R.id.action_startFragment_to_weatherFragment, bundle)
        }
    }

}