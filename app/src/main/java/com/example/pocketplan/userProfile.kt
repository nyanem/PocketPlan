package com.example.pocketplan

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class userProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

       // val menuBanking = findViewById<TextView>(R.id.menuBanking)
        val menuHelp = findViewById<TextView>(R.id.menuHelp)
        val menuTips = findViewById<TextView>(R.id.menuTips)
        val menuLanguage = findViewById<TextView>(R.id.menuLanguage)


        menuHelp.setOnClickListener {
            val intent = Intent(this@userProfile, Help::class.java)
            startActivity(intent)
        }

        menuTips.setOnClickListener {
            val intent = Intent(this@userProfile, tips::class.java)
            startActivity(intent)
        }

        menuLanguage.setOnClickListener {
            val intent = Intent(this@userProfile, language::class.java)
            startActivity(intent)
        }


    }
}

