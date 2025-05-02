package com.example.pocketplan

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Help : AppCompatActivity() {
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
        }
    }
