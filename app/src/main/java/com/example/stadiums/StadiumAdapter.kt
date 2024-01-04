package com.example.stadiums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StadiumAdapter(private val stadiumList: List<Stadium>) : RecyclerView.Adapter<StadiumAdapter.StadiumViewHolder>() {
    data class Stadium(
        val name: String,
        val image: String
    )
    inner class StadiumViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val stadiumImage: ImageView = view.findViewById(R.id.stadiumImage)
        val stadiumName: TextView = view.findViewById(R.id.stadiumName)
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
        // Här behöver du skriva kod för att ladda din bild in i `holder.stadiumImage`
    }
}