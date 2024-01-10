package com.example.stadiums

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import android.content.Context
import android.content.Intent
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import android.widget.Button
import androidx.core.content.res.ResourcesCompat


class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    val ADD_STADIUM_REQUEST_CODE = 1  // Ett unikt request code
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION


        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, AddStadiumActivity::class.java)
            startActivityForResult(intent, ADD_STADIUM_REQUEST_CODE)
        }

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Skapa en instans av FirebaseFirestore
        db = Firebase.firestore
        val storage = Firebase.storage
        // Kalla på updateView() här
        updateView()

        val storageRef = storage.reference
        val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)

        val isFirstRun = sharedPref.getBoolean("isFirstRun", true)

        if (isFirstRun) {
            val stadiumData = listOf(
                Triple("Gamla Ullevi", "Göteborg", "Gamla_Ullevi.jpg"),
                Triple("Vale Park", "Stoke-on-Trent", "Port_Vale.jpg"),
                Triple("Liberty Stadium", "Swansea", "Liberty_stadium.jpg")
            )

            for ((stadiumName, city, imageName) in stadiumData) {
                val pathRef = storageRef.child(imageName)

                pathRef.downloadUrl.addOnSuccessListener { uri ->
                    val url = uri.toString()
                    val stadiumDocument = hashMapOf(
                        "name" to stadiumName,
                        "imageUrl" to url,
                        "city" to city
                    )
                    db.collection("stadiums").document().set(stadiumDocument)
                }
            }

            // Sätt isFirstRun till false
            with (sharedPref.edit()) {
                putBoolean("isFirstRun", false)
                apply()
            }
        }

        val stadiumList = mutableListOf<StadiumAdapter.Stadium>()

        db.collection("stadiums").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val name = document.getString("name") ?: "Unknown"
                val city = document.getString("city") ?: "Unknown"
                val imageUrl = document.getString("imageUrl") ?: "Unknown"
                val country = document.getString("country") ?: "Unknown"
                val built = document.getString("built") ?: "Unknown"
                val team = document.getString("team") ?: "Unknown"
                val capacity = document.getString("capacity") ?: "0"


                val stadium = StadiumAdapter.Stadium(name, imageUrl, city, country, built, team, capacity)
                stadiumList.add(stadium)
            }
            val adapter = StadiumAdapter(stadiumList)
            recyclerView.adapter = adapter
        }
    }
    fun updateView() {
        val stadiumList = mutableListOf<StadiumAdapter.Stadium>()

        db.collection("stadiums").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val name = document.getString("name") ?: "Unknown"
                val city = document.getString("city") ?: "Unknown"
                val imageUrl = document.getString("imageUrl") ?: "Unknown"
                val country = document.getString("country") ?: "Unknown"
                val built = document.getString("built") ?: "Unknown"
                val team = document.getString("team") ?: "Unknown"
                val capacity = document.getString("capacity") ?: "0"


                val stadium = StadiumAdapter.Stadium(name, imageUrl, city, country, built, team, capacity)
                stadiumList.add(stadium)
            }
            val adapter = StadiumAdapter(stadiumList)
            recyclerView.adapter = adapter
        }
    }
    // Lägg till denna metod i MainActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_STADIUM_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            updateView()
        }
    }

}