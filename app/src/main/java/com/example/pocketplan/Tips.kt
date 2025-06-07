package com.example.pocketplan

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge

class Tips : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tips)

        // Find the back button in the layout
        val backButton = findViewById<ImageButton>(R.id.backButton)

        // Set an OnClickListener to handle the back button click
        backButton.setOnClickListener {
            // Close the current activity and return to the previous one (User Profile Page)
            finish()
        }
        }
    }
