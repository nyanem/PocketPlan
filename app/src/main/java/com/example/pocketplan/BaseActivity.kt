package com.example.pocketplan

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    
    override fun attachBaseContext(newBase: Context?) {
        val context = newBase?.let { LanguageManager.getLocalizedContext(it) }
        super.attachBaseContext(context ?: newBase)
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
