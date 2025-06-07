package com.example.pocketplan

import android.app.Application
import android.content.Context

class PocketPlanApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Apply saved language on app start
        LanguageManager.setAppLocale(this, LanguageManager.getCurrentLanguage(this))
    }
    
    override fun attachBaseContext(base: Context?) {
        val context = base?.let { LanguageManager.getLocalizedContext(it) }
        super.attachBaseContext(context ?: base)
    }
}
