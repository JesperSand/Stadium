package com.example.stadiums

import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore

class StadiumDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stadium_detail)
        window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val db = Firebase.firestore

        // Hämta informationen från Intent-objektet
        val stadiumName = intent.getStringExtra("name")
        val stadiumImage = intent.getStringExtra("image")
        val stadiumCity = intent.getStringExtra("city")

        // Hitta TextViews i layouten
        val nameTextView = findViewById<TextView>(R.id.stadiumName)
        val cityTextView = findViewById<TextView>(R.id.city)
        val imageView = findViewById<ImageView>(R.id.stadiumImage)

        // Sätt texten på TextViews till informationen vi hämtade
        nameTextView.text = "Name: $stadiumName"
        cityTextView.text = "City: $stadiumCity"

        // Använd Glide för att ladda bilden
        Glide.with(this)
            .load(stadiumImage)
            .centerCrop()
            .into(imageView)

        db.collection("stadiums")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val documentStadiumName = document.getString("name")
                    if (stadiumName == documentStadiumName) {
                        updateUI(document)
                        break // Avbryt loopen efter att vi har hittat rätt dokument
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
    fun updateUI(document: DocumentSnapshot) {
        val capacityTextView = findViewById<TextView>(R.id.capacity)
        val builtTextView = findViewById<TextView>(R.id.built)
        val teamTextView = findViewById<TextView>(R.id.team)
        val countryTextView = findViewById<TextView>(R.id.country)

        val capacity = document.getString("capacity")
        val built = document.getString("built")
        val team = document.getString("team")
        val country = document.getString("country")

        if (capacity == null || built == null || team == null || country == null ) {
            Log.e(TAG, "Failed to update UI: capacity=$capacity, built=$built, team=$team")
            return
        }

        Log.d(TAG, "Updating UI with country: $country capacity: $capacity, built: $built, team: $team")

        capacityTextView.text = "Capacity: $capacity"
        builtTextView.text = "Year built: $built"
        teamTextView.text = "Team: $team"
        countryTextView.text= "Country: $country"
    }

}