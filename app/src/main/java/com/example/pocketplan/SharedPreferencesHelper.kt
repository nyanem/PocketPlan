package com.example.pocketplan



import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

    fun saveUser(username: String, password: String, name: String, surname: String, mobile: String, email: String) {
        prefs.edit().apply {
            putString("username", username)
            putString("password", password)
            putString("name", name)
            putString("surname", surname)
            putString("mobile", mobile)
            putString("email", email)
            apply()
        }
    }

    fun getUser(): Map<String, String?> {
        return mapOf(
            "username" to prefs.getString("username", null),
            "password" to prefs.getString("password", null),
            "name" to prefs.getString("name", null),
            "surname" to prefs.getString("surname", null),
            "mobile" to prefs.getString("mobile", null),
            "email" to prefs.getString("email", null)
        )
    }

    fun userExists(username: String): Boolean {
        return prefs.getString("username", null) == username
    }

    fun validateLogin(username: String, password: String): Boolean {
        return prefs.getString("username", null) == username &&
                prefs.getString("password", null) == password
    }
}
