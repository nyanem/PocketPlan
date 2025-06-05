package com.example.pocketplan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class LandingPageActivity : AppCompatActivity() {

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var db: AppDatabase
    private val selectedCategories = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)

        loginButton.setOnClickListener {
            // Navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val context = LocaleHelper.setLocale(this, "af")
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}