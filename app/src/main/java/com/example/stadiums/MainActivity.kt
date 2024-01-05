package com.example.stadiums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Initiera din RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Skapa en instans av FirebaseFirestore
        db = Firebase.firestore

        val stadiumList = mutableListOf<StadiumAdapter.Stadium>()

        db.collection("stadiums").get().addOnSuccessListener { documents ->
            for (document in documents) {
                val name = document.getString("name") ?: "Unknown"
                val image = document.getString("image") ?: "Unknown"
                val stadium = StadiumAdapter.Stadium(name, image)
                stadiumList.add(stadium)
            }
            val adapter = StadiumAdapter(stadiumList)
            recyclerView.adapter = adapter
        }
    }
}