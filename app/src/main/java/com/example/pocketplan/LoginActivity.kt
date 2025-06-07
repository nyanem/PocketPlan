package com.example.pocketplan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : BaseActivity() {
    private lateinit var dbHelper: PocketPlanDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = PocketPlanDBHelper(this)

        val usernameField = findViewById<EditText>(R.id.username_txtbox)
        val passwordField = findViewById<EditText>(R.id.password_txtbox)
        val loginButton = findViewById<Button>(R.id.loginButton)

        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (dbHelper.checkLogin(username, password)) {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
                // Navigate to home screen
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }


        }
    }
}





//}
//val loginButton = findViewById<Button>(R.id.loginButton)
//loginButton.setOnClickListener {
//    val intent = Intent(this, HomePageActivity::class.java)
//    startActivity(intent)
//}