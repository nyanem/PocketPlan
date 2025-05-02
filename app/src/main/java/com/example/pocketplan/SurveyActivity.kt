package com.example.pocketplan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SurveyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_survey)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val incomeBox = findViewById<EditText>(R.id.income_txtbox)
        val maxSavingsBox = findViewById<EditText>(R.id.maxsavings_txtbox)
        val minSavingsBox = findViewById<EditText>(R.id.minsavings_txtbox)
        val saveBtn = findViewById<Button>(R.id.saveSurveyButton)

        saveBtn.setOnClickListener {
            val income = incomeBox.text.toString().trim()
            val maxSaving = maxSavingsBox.text.toString().trim()
            val minSaving = minSavingsBox.text.toString().trim()

            val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
            with(prefs.edit()) {
                putString("income", income)
                putString("maxSaving", maxSaving)
                putString("minSaving", minSaving)
                apply()
            }

            Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show()
        }
        val saveSurveyButton = findViewById<Button>(R.id.saveSurveyButton)
        saveSurveyButton.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
    }
}