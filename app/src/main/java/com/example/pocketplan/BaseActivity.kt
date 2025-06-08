package com.example.pocketplan

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    
    private var currentLanguage: String? = null
    
    override fun attachBaseContext(newBase: Context?) {
        val context = newBase?.let { LanguageManager.getLocalizedContext(it, "en") }
        super.attachBaseContext(context ?: newBase)
    }
    
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        // Store the current language when activity is created
        currentLanguage = LanguageManager.getCurrentLanguage(this)
        android.util.Log.d("BaseActivity", "onCreate - ${this.javaClass.simpleName}, current language: $currentLanguage, locale: ${resources.configuration.locales[0]}")
    }    override fun onResume() {
        super.onResume()
        // Check if language has changed while this activity was paused
        val newLanguage = LanguageManager.getCurrentLanguage(this)
        if (currentLanguage != null && currentLanguage != newLanguage) {
            // Language has changed, recreate the activity
            currentLanguage = newLanguage
            recreate()
            return
        }
        
        // Ensure the correct language is applied when activity resumes
        LanguageManager.setAppLocale(this, newLanguage)
    }
    
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle configuration changes like language switches
        val currentLanguage = LanguageManager.getCurrentLanguage(this)
        LanguageManager.setAppLocale(this, currentLanguage)
    }
    
    // Convenience methods for currency formatting
    protected fun formatCurrency(amount: Double): String {
        return CurrencyManager.formatCurrency(this, amount)
    }
    
    protected fun formatCurrency(amount: String): String {
        return CurrencyManager.formatCurrency(this, amount)
    }
    
    protected fun getCurrencySymbol(): String {
        return CurrencyManager.getCurrentCurrencySymbol(this)
    }
}
