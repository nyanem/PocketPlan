package com.example.pocketplan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : BaseActivity() {
    private lateinit var usernameField: EditText
    private lateinit var passwordField: EditText
    private lateinit var nameField: EditText
    private lateinit var surnameField: EditText
    private lateinit var mobileField: EditText
    private lateinit var emailField: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameField = findViewById(R.id.usernameInput)        // Use email as username
        passwordField = findViewById(R.id.passwordInput)       // Temporary: password field missing, maybe mobile used as password?
        nameField = findViewById(R.id.name_txtbox)
        surnameField = findViewById(R.id.surname_txtbox)
        mobileField = findViewById(R.id.number_txtbox)
        emailField = findViewById(R.id.email_txtbox)
//        registerButton = findViewById(R.id.RegisterButton)

        val profileButton = findViewById<Button>(R.id.profileButton)
        val dbHelper = PocketPlanDBHelper(this)

        profileButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()
            val name = nameField.text.toString()
            val surname = surnameField.text.toString()
            val mobile = mobileField.text.toString()
            val email = emailField.text.toString()


            //Validate username
            if (username.length < 8 || !username.any { it.isUpperCase() } || !username.any { it.isDigit() } || !username.all { it.isLetterOrDigit() }) {
                Toast.makeText(this, "Username must be at least 8 characters, include a capital letter," +
                        " a number, and no special characters.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            // Validate password
            if (password.length < 8 || !password.any { it.isUpperCase() } || !password.any { it.isDigit() } || !password.all { it.isLetterOrDigit() }) {
                Toast.makeText(this, "Password must be at least 8 characters, include a capital letter," +
                        " a number, and no special characters.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val success = dbHelper.insertUser(username, password, name, surname, mobile, email)
            if (success) {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
            // Navigate to login
            val intent = Intent(this, SurveyActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Registration failed. Username might already exist.", Toast.LENGTH_SHORT).show()
        }
        }
    }
}
