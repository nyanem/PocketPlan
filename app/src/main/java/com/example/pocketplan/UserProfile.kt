package com.example.pocketplan

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class UserProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val helpBtn = findViewById<TextView>(R.id.menuHelp)
        val tipsBtn = findViewById<TextView>(R.id.menuTips)
        val languageBtn = findViewById<TextView>(R.id.menuLanguage)

        helpBtn.setOnClickListener {
            startActivity(Intent(this, Help::class.java))
        }

        tipsBtn.setOnClickListener {
            startActivity(Intent(this, Tips::class.java))
        }

        languageBtn.setOnClickListener {
            startActivity(Intent(this, Language::class.java))
        }

    }
}
