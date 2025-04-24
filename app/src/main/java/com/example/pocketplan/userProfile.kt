package com.example.pocketplan

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class userProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        val context = this
        findViewById<TextView>(R.id.menuBanking).setOnClickListener {
            startActivity(Intent(context, Help::class.java))
        }

        findViewById<TextView>(R.id.menuBanking).setOnClickListener {
            startActivity(Intent(context, tips::class.java))
        }

        findViewById<TextView>(R.id.menuBanking).setOnClickListener {
            startActivity(Intent(context, language::class.java))
        }

       /* findViewById<TextView>(R.id.menuBanking).setOnClickListener {
            startActivity(Intent(context, bankingApp::class.java))
        }*/
        //make a plan here.

    }
}

