package com.example.pocketplan

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomePageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val budgetRemainingText = findViewById<TextView>(R.id.budgetRemaining)
        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val container = findViewById<LinearLayout>(R.id.categoryCardsContainer)

        val maxSaving = prefs.getString("maxSaving", "5000")!!.toDoubleOrNull() ?: 0.0
        val minSaving = prefs.getString("minSaving", "0")!!.toDoubleOrNull() ?: 0.0
        budgetRemainingText.text = "R$maxSaving"

        // Simulate spent values (replace with real tracking later)
        val maxSpent = 1000.0
        val minSpent = 0.0

//        addCategoryCard(container, "Max Saving Goal", maxSaving, maxSpent)
//        addCategoryCard(container, "Min Saving Goal", minSaving, minSpent)
          addCategoryCard(container, "Groceries", maxSpent, minSpent)

    }
    private fun addCategoryCard(container: LinearLayout, categoryName: String, totalBudget: Double, amountSpent: Double) {
        val cardView = LayoutInflater.from(this).inflate(R.layout.category_card, container, false)

        val nameText = cardView.findViewById<TextView>(R.id.categoryName)
        val spentText = cardView.findViewById<TextView>(R.id.categorySpent)
        val progressBar = cardView.findViewById<ProgressBar>(R.id.categoryProgress)
        val categoryGoalAmount = findViewById<TextView>(R.id.categoryGoalAmount)
        val categorySpent = findViewById<TextView>(R.id.categorySpent)
        val categoryProgress = findViewById<ProgressBar>(R.id.categoryProgress)
        val categoryNameView = findViewById<TextView>(R.id.categoryName)
        val categoryBalance = findViewById<TextView>(R.id.balance)

//        categoryNameView.text = categoryName
//        categoryGoalAmount.text = "R${"%.2f".format(totalBudget)}"
//        categorySpent.text = "-R${"%.2f".format(amountSpent)}"
//
//        // Get the category name
//        val category = categoryNameView.text.toString()
//
//// Retrieve the saved goal amount from SharedPreferences
//        val prefs = getSharedPreferences("GoalPrefs", MODE_PRIVATE)
//        val goal = prefs.getString(category, "0")?.toFloatOrNull() ?: 0f
//
//// Example hardcoded spent amount (you can later replace this with actual tracking)
//        val spent = 300f
//
//// Calculate balance
//        val balance = goal - spent
//
//// Update views
//        categoryGoalAmount.text = "Goal: R${goal.toInt()}"
//        categorySpent.text = "-R${spent.toInt()}"
//
//        categoryBalance.text = "R${balance.toInt()}"
//        categoryProgress.progress = if (goal > 0) ((spent / goal) * 100).toInt() else 0
//
//// Update progress bar
//        val progress = if (goal > 0f) ((spent / goal) * 100).toInt() else 0
//        categoryProgress.progress = progress
//        val percent = if (totalBudget > 0) ((amountSpent / totalBudget) * 100).toInt().coerceAtMost(100) else 0
//        progressBar.progress = percent

        container.addView(cardView)
    }
}