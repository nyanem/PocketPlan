package com.example.pocketplan

import android.app.Application
import android.content.Context
import android.util.Log

class PocketPlanApplication : Application() {
      override fun onCreate() {
        super.onCreate()
        // Apply saved language on app start
        val savedLanguage = LanguageManager.getCurrentLanguage(this)
        Log.d("PocketPlanApplication", "Applying saved language on startup: $savedLanguage")
        LanguageManager.setAppLocale(this, savedLanguage)
        Log.d("PocketPlanApplication", "Language applied, current locale: ${resources.configuration.locales[0]}")
    }
    
    override fun attachBaseContext(base: Context?) {
        // Get the saved language before attaching the base context
        val savedLanguage = if (base != null) {
            val sharedPrefs = base.getSharedPreferences("LanguagePrefs", Context.MODE_PRIVATE)
            sharedPrefs.getString("language", "en") ?: "en"
        } else {
            "en"
        }
        
        Log.d("PocketPlanApplication", "attachBaseContext with language: $savedLanguage")        // Create a localized context with the saved language
        val context = base?.let { 
            val locale = java.util.Locale(savedLanguage)
            java.util.Locale.setDefault(locale)
            
            val config = android.content.res.Configuration(it.resources.configuration)
            config.setLocale(locale)
            
            it.createConfigurationContext(config)
        }
        super.attachBaseContext(context ?: base)
    }
}
