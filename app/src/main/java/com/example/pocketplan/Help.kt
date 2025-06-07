package com.example.pocketplan

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Help : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_help)

        val chip = findViewById<TextView>(R.id.chip)
        val chip2 = findViewById<TextView>(R.id.chip2)

        chip.setOnClickListener {
            Toast.makeText(this, "Feature in Progress", Toast.LENGTH_SHORT).show()
        }

        chip2.setOnClickListener {
            Toast.makeText(this, "Feature in Progress", Toast.LENGTH_SHORT).show()
        }

        // Find the back button in the layout
        val backButton = findViewById<ImageButton>(R.id.backButton)

        // Set an OnClickListener to handle the back button click
        backButton.setOnClickListener {
            // Close the current activity and return to the previous one (User Profile Page)
            finish()
            }
        }
    }
