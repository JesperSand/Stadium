package com.example.stadiums

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.text.NumberFormat

class AddStadiumActivity : AppCompatActivity() {

    private val REQUEST_CODE = 1
    lateinit var imageViewStadium: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_stadium)
        window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION

        val db = Firebase.firestore

        val editTextStadiumName = findViewById<EditText>(R.id.editTextStadiumName)
        val editTextCountry = findViewById<EditText>(R.id.editTextCountry)
        val editTextCity = findViewById<EditText>(R.id.editTextCity)
        val editTextCapacity = findViewById<EditText>(R.id.editTextCapacity)
        val editTextBuilt = findViewById<EditText>(R.id.editTextBuilt)
        val editTextTeam = findViewById<EditText>(R.id.editTextTeam)
        val buttonUploadImage = findViewById<Button>(R.id.buttonUploadImage)
        this.imageViewStadium = findViewById(R.id.imageViewStadium)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)

        buttonUploadImage.setOnClickListener {
            val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickPhoto, REQUEST_CODE)
        }

        buttonAdd.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val name = editTextStadiumName.text.toString()
                val country = editTextCountry.text.toString()
                val city = editTextCity.text.toString()
                val built = editTextBuilt.text.toString()
                val team = editTextTeam.text.toString()
                var formattedCapacity: String = ""
                try {
                    val capacity = editTextCapacity.text.toString().toInt()
                    formattedCapacity = NumberFormat.getNumberInstance().format(capacity)
                } catch (e: NumberFormatException) {
                    // Visa ett felmeddelande till användaren
                }

                val stadium = hashMapOf(
                    "name" to name,
                    "country" to country,
                    "city" to city,
                    "built" to built,
                    "team" to team,
                    "capacity" to formattedCapacity
                )

                // Din kod...
                // I AddStadiumActivity, när du har lagt till den nya stadion
                db.collection("stadiums")
                    .add(stadium)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                        setResult(Activity.RESULT_OK)  // Sätt resultatet till OK
                        finish()  // Avsluta aktiviteten
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
            }
        })

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val selectedImage = data.data
            // Gör något med selectedImage, till exempel sätt det i din ImageView
            Log.d("ImageHandling", "Selected image: $selectedImage")
            imageViewStadium.setImageURI(selectedImage)
            Log.d("ImageHandling", "Image set in ImageView")
        }
    }
}