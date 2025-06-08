package com.example.pocketplan

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class UserProfile : BaseActivity() {
    
    private val languageActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Language was changed, recreate this activity to apply new language
            recreate()
        }
    }
    
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
            val intent = Intent(this, Language::class.java)
            languageActivityLauncher.launch(intent)
        }
    }
}
