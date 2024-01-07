package com.example.stadiums

import android.content.Intent
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
        Glide.with(holder.stadiumImage.context)
            .load(stadium.image)
            .into(holder.stadiumImage)

        // Lägg till en onClickListener till hela vyn
        holder.itemView.setOnClickListener {
            // Skapa en Intent för att starta StadiumDetailActivity
            val intent = Intent(holder.itemView.context, StadiumDetailActivity::class.java)

            // Lägg till information om den valda stadionen som "extras" i Intent-objektet
            intent.putExtra("name", stadium.name)
            intent.putExtra("image", stadium.image)
            intent.putExtra("city", stadium.city)

            // Starta StadiumDetailActivity
            holder.itemView.context.startActivity(intent)
        }
    }
}