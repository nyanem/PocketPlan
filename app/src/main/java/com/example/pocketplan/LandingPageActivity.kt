package com.example.pocketplan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button

class LandingPageActivity : BaseActivity() {    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var debugLanguageBtn: Button
    private lateinit var db: AppDatabase
    private val selectedCategories = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Debug: Check what language is currently set
        val currentLanguage = LanguageManager.getCurrentLanguage(this)
        Log.d("LandingPageActivity", "Current language on startup: $currentLanguage")
        Log.d("LandingPageActivity", "Current locale: ${resources.configuration.locales[0]}")
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


        // Remove the infinite loop - this was causing the app to crash
        // val context = LocaleHelper.setLocale(this, "af")
        // val intent = Intent(context, LandingPageActivity::class.java)
        // startActivity(intent)
        // finish()
    }
}