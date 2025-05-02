package com.example.pocketplan

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Language : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        val menuLanguage = findViewById<TextView>(R.id.menuLanguage)
        val menuCurrency = findViewById<TextView>(R.id.menuCurrency)

        menuLanguage.setOnClickListener {
            Toast.makeText(this, "This feature is coming soon", Toast.LENGTH_SHORT).show()
        }

        menuCurrency.setOnClickListener {
            Toast.makeText(this, "This feature is coming soon", Toast.LENGTH_SHORT).show()
        }
    }
}
