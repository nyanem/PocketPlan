package com.example.pocketplan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

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



//package com.example.pocketplan
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import android.widget.Button
//import android.widget.EditText
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class RegisterActivity : AppCompatActivity() {
//
//    private lateinit var sharedPrefs: SharedPreferencesHelper
//    private lateinit var dbHelper: PocketPlanDBHelper
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_register)
//
////        sharedPrefs = SharedPreferencesHelper(this)
//
//        dbHelper = PocketPlanDBHelper(this)
//
//        val usernameField = findViewById<EditText>(R.id.email_txtbox)
//        val nameField = findViewById<EditText>(R.id.name_txtbox)
//        val surnameField = findViewById<EditText>(R.id.surname_txtbox)
//        val mobileField = findViewById<EditText>(R.id.number_txtbox)
//        val emailField = findViewById<EditText>(R.id.email_txtbox)
//        val registerButton = findViewById<Button>(R.id.RegisterButton)
//
//        registerButton.setOnClickListener {
//            val username = usernameField.text.toString().trim()
//            val password = "123456" // Replace with password field later
//            val name = nameField.text.toString().trim()
//            val surname = surnameField.text.toString().trim()
//            val mobile = mobileField.text.toString().trim()
//            val email = emailField.text.toString().trim()
//
//            val validationMsg = validateUsername(username)
//            if (validationMsg != null) {
//                Toast.makeText(this, validationMsg, Toast.LENGTH_LONG).show()
//                return@setOnClickListener
//            }
//
//            if (dbHelper.userExists(username)) {
//                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
//            } else {
//                val success = dbHelper.insertUser(username, password, name, surname, mobile, email)
//                if (success) {
//                    Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
//                    startActivity(Intent(this, CreateProfileActivity::class.java))
//                } else {
//                    Toast.makeText(this, "Failed to register user", Toast.LENGTH_SHORT).show()
//                }
//            }
////            if (!validateUsername(username)) {
////                Toast.makeText(this, "Invalid username format.", Toast.LENGTH_LONG).show()
////                return@setOnClickListener
////            }
//
//            if (sharedPrefs.userExists(username)) {
//                Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show()
//            } else {
//                sharedPrefs.saveUser(username, password, name, surname, mobile, email)
//                Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this, CreateProfileActivity::class.java))
//            }
//        }
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//
//        val users = dbHelper.getAllUsers()
//        Log.d("USER_TABLE", "Users: $users")
//
//        registerButton.setOnClickListener {
//            val intent = Intent(this, CreateProfileActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    private fun validateUsername(username: String): String? {
//        if (!username.matches(Regex("^[A-Z][a-zA-Z0-9]{7,}$"))) {
//            return when {
//                !username.any { it.isUpperCase() } -> "Username must start with a capital letter"
//                username.length < 8 -> "Username must be at least 8 characters long"
//                !username.any { it.isDigit() } -> "Username must include at least one number"
//                else -> "Invalid username format"
//            }
//        }
//        return null
//    }
//
//
//}
//
