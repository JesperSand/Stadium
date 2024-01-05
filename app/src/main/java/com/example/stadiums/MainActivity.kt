package com.example.stadiums

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Initiera din RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        // 2. Skapa din lista av Stadium objekt
        // (Observera att du kommer att behöva ersätta detta med din faktiska data)
        val stadiumList = listOf(
            StadiumAdapter.Stadium("Stadium 1", "Image 1"),
            StadiumAdapter.Stadium("Stadium 2", "Image 2"),
            StadiumAdapter.Stadium("Stadium 3", "Image 3"),
            StadiumAdapter.Stadium("Stadium 4", "Image 4")

        )


        // 3. Skapa en instans av din StadiumAdapter
        val adapter = StadiumAdapter(stadiumList)

        // 4. Sätt din StadiumAdapter som adaptern för din RecyclerView
        recyclerView.adapter = adapter

    }
}