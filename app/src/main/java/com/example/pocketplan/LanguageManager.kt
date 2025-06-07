package com.example.pocketplan

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import java.util.Locale

object LanguageManager {
    private const val PREFS_NAME = "LanguagePrefs"
    private const val LANGUAGE_KEY = "language"
    private const val DEFAULT_LANGUAGE = "en"
    
    fun setLanguage(context: Context, languageCode: String) {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPrefs.edit().putString(LANGUAGE_KEY, languageCode).apply()
        
        setAppLocale(context, languageCode)
    }
    
    fun getCurrentLanguage(context: Context): String {
        val sharedPrefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getString(LANGUAGE_KEY, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
    }
    
    fun setAppLocale(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        
        @Suppress("DEPRECATION")
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
    
    fun getLocalizedContext(context: Context): Context {
        val languageCode = getCurrentLanguage(context)
        
        val locale = Locale(languageCode)
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)
        
        return context.createConfigurationContext(config)
    }
}
