package com.example.pocketplan

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class Language : BaseActivity() {
    private lateinit var languageSpinner: Spinner
    private lateinit var currencySpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        // Initialize views
        languageSpinner = findViewById(R.id.languageSpinner)
        currencySpinner = findViewById(R.id.menuCurrency)

        setupLanguageSpinner()
        setupCurrencySpinner()

        // Find the back button in the layout
        val backButton = findViewById<ImageButton>(R.id.backButton)

        // Set an OnClickListener to handle the back button click
        backButton.setOnClickListener {
            // Close the current activity and return to the previous one (User Profile Page)
            finish()
        }
    }

    private fun setupLanguageSpinner() {
        // Get language options from resources
        val languages = resources.getStringArray(R.array.language_options)
        val languageCodes = resources.getStringArray(R.array.language_codes)

        // Create adapter for spinner
        val adapter = ArrayAdapter(this, R.layout.spinner_item_language, languages)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_language)
        languageSpinner.adapter = adapter
        // Set current selection based on saved preference
        val currentLanguage = LanguageManager.getCurrentLanguage(this)
        val position = languageCodes.indexOf(currentLanguage)
        if (position != -1) {
            languageSpinner.setSelection(position)
        }

        // Set up selection listener
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguageCode = languageCodes[position]
                val selectedLanguageName = languages[position]

                // Only proceed if the language actually changed
                if (selectedLanguageCode != currentLanguage) {
                    // Save and apply the selected language
                    LanguageManager.setLanguage(this@Language, selectedLanguageCode)

                    // Show confirmation message
                    Toast.makeText(
                        this@Language,
                        "${getString(R.string.language_changed)} $selectedLanguageName",
                        Toast.LENGTH_SHORT                    ).show()
                    
                    // Restart the activity to apply changes
                    recreate()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }    private fun setupCurrencySpinner() {
        // Get currency options from resources
        val currencies = resources.getStringArray(R.array.currency_array)

        // Create adapter for spinner
        val adapter = ArrayAdapter(this, R.layout.spinner_item_language, currencies)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item_language)
        currencySpinner.adapter = adapter

        // Set current selection based on saved preference
        val currentCurrency = CurrencyManager.getCurrentCurrency(this)
        val position = currencies.indexOf(currentCurrency)
        if (position != -1) {
            currencySpinner.setSelection(position)
        } else {
            // If no currency is selected, show the placeholder (first item)
            currencySpinner.setSelection(0)
        }

        // Set up selection listener
        currencySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // Skip the placeholder item (position 0)
                    if (position > 0) {
                        val selectedCurrency = currencies[position]

                        // Only proceed if the currency actually changed
                        if (selectedCurrency != currentCurrency) {
                            // Save the selected currency
                            CurrencyManager.setCurrentCurrency(
                                this@Language,
                                selectedCurrency
                            )

                            // Show confirmation message
                            Toast.makeText(
                                this@Language,
                                "Currency changed to $selectedCurrency",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }
    }
}
