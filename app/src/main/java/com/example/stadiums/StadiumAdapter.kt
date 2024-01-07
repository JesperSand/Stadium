package com.example.stadiums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class StadiumAdapter(private val stadiumList: List<Stadium>) : RecyclerView.Adapter<StadiumAdapter.StadiumViewHolder>() {
    data class Stadium(
        val name: String,
        val image: String,
        val city: String
    )
    inner class StadiumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stadiumImage: ImageView = view.findViewById(R.id.stadiumImage)
        val stadiumName: TextView = view.findViewById(R.id.stadiumName)
        val city: TextView = view.findViewById(R.id.city)
    }
    override fun getItemCount(): Int {
        return stadiumList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StadiumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stadium, parent, false)
        return StadiumViewHolder(view)
    }

    override fun onBindViewHolder(holder: StadiumViewHolder, position: Int) {
        val stadium = stadiumList[position]
        holder.stadiumName.text = stadium.name
        holder.city.text = stadium.city

        // Använd Glide för att ladda bilden
        Glide.with(holder.stadiumImage.context) // Använd context från ImageView
            .load(stadium.image)
            .into(holder.stadiumImage)
    }
}