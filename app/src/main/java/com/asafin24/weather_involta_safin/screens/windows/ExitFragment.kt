package com.asafin24.weather_involta_safin.screens.windows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asafin24.weather_involta_safin.databinding.FragmentExitBinding
import kotlin.system.exitProcess

class ExitFragment: DialogFragment() {

    lateinit var binding: FragmentExitBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExitBinding.inflate(layoutInflater, container,false)


        binding.buttonYes.setOnClickListener {
            exitProcess(-1)
        }

        binding.buttonNo.setOnClickListener {
            dismiss()
        }

        return binding.root

    }
}