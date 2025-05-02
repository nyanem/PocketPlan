package com.example.pocketplan

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddReceipt : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var uploadButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_receipt)

        imageView = findViewById(R.id.img_SavedPhoto)
        uploadButton = findViewById(R.id.button)
    }

}