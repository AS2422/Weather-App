package com.asafin24.weather_involta_safin.screens.start

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asafin24.weather_involta_safin.R
import com.asafin24.weather_involta_safin.databinding.CityItemBinding
import com.asafin24.weather_involta_safin.model.CityModel
import kotlinx.android.synthetic.main.city_item.view.*

class StartAdapter : RecyclerView.Adapter<StartAdapter.CityVH>() {

    var cities = mutableListOf(
        "Екатеринбург",
        "Казань",
        "Магнитогорск",
        "Иваново",
        "Москва",
        "Санкт-Петербург",
        "Самара",
        "Нижний Новгород",
        "Калининград",
        "Краснодар",
        "Стамбул",
        "Брюгге",
        "Хельсинки",
        "Стокгольм",
        "Осло"
    )

    class CityVH(item: View) : RecyclerView.ViewHolder(item) {

        val binding = CityItemBinding.bind(item)

        fun bind(city: String) {
            binding.tvCityName.text = city
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityVH =
        CityVH(LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false))

    override fun onBindViewHolder(holder: CityVH, position: Int) = holder.itemView.run {

        cities.sortBy { it }
        holder.bind(cities[position])

        tv_city_name.text = cities[position]

    }

    override fun getItemCount(): Int {
        return cities.size
    }


    override fun onViewAttachedToWindow(holder: CityVH) {
        super.onViewAttachedToWindow(holder)
        holder.itemView.setOnClickListener{
            StartFragment.clickCard(cities[holder.adapterPosition])
        }
    }

    override fun onViewDetachedFromWindow(holder: CityVH) {
        holder.itemView.setOnClickListener(null)
    }
}
