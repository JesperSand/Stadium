package com.example.stadiums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class StadiumDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stadium_detail)

        // Hämta informationen från Intent-objektet
        val stadiumName = intent.getStringExtra("name")
        val stadiumImage = intent.getStringExtra("image")
        val stadiumCity = intent.getStringExtra("city")

        // Hitta TextViews i layouten
        val nameTextView = findViewById<TextView>(R.id.stadiumName)
        val cityTextView = findViewById<TextView>(R.id.city)
        val imageView = findViewById<ImageView>(R.id.stadiumImage)

        // Sätt texten på TextViews till informationen vi hämtade
        nameTextView.text = stadiumName
        cityTextView.text = stadiumCity

        // Använd Glide för att ladda bilden
        Glide.with(this)
            .load(stadiumImage)
            .into(imageView)
    }
}