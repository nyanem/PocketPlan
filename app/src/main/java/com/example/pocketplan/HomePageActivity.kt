package com.example.pocketplan

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
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

        val dbHelper = PocketPlanDBHelper(this)
        val savedCategories = dbHelper.getAllCategories()
        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val container = findViewById<LinearLayout>(R.id.categoryCardsContainer)
        val budgetRemainingText = findViewById<TextView>(R.id.budgetRemaining)
        val maxSavingGoal = dbHelper.getMaxSavingGoal()

        // Example overall max saving shown on top
        budgetRemainingText.text = "R${"%.2f".format(maxSavingGoal)}"



        for (category in savedCategories) {
            val goal = prefs.getString("${category}_goal", "0")?.toDoubleOrNull() ?: 0.0
            val spent = prefs.getString("${category}_spent", "0")?.toDoubleOrNull() ?: 0.0
            addCategoryCard(container, category, goal, spent)
        }
    }

    private fun addCategoryCard(container: LinearLayout, categoryName: String, totalBudget: Double, amountSpent: Double) {
        val cardView = LayoutInflater.from(this).inflate(R.layout.category_card, container, false)

        val categoryNameView = cardView.findViewById<TextView>(R.id.categoryName)
        val categoryGoalAmount = cardView.findViewById<TextView>(R.id.categoryGoalAmount)
        val categorySpent = cardView.findViewById<TextView>(R.id.categorySpent)
        val categoryBalance = cardView.findViewById<TextView>(R.id.balance)
        val categoryProgress = cardView.findViewById<ProgressBar>(R.id.categoryProgress)

        val balance = totalBudget - amountSpent
        val progress = if (totalBudget > 0) ((amountSpent / totalBudget) * 100).toInt().coerceAtMost(100) else 0

        categoryNameView.text = categoryName
        categoryGoalAmount.text = "Goal: R${"%.2f".format(totalBudget)}"
        categorySpent.text = "-R${"%.2f".format(amountSpent)}"
        categoryBalance.text = "R${"%.2f".format(balance)}"
        categoryProgress.progress = progress

        container.addView(cardView)
    }
}
