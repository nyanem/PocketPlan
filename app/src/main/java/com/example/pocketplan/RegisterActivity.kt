package com.example.pocketplan

import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        
        Toast.makeText(this, "Register Activity Loaded", Toast.LENGTH_SHORT).show()
    }
}
