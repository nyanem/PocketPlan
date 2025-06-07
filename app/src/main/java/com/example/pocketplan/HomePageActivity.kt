package com.example.pocketplan

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomePageActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val addTransactionButton = findViewById<FloatingActionButton>(R.id.addTransaction)

        addTransactionButton.setOnClickListener {
            val intent = Intent(this, AddTransaction::class.java)//
            startActivity(intent)
        }
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_transactions -> {
                    startActivity(Intent(this, AddTransaction::class.java))
                    true
                }
                R.id.nav_reports -> {
                    startActivity(Intent(this, Reports::class.java))
                    true
                }
                R.id.nav_user_profile -> {
                    startActivity(Intent(this, UserProfile::class.java))
                    true
                }
                else -> false
            }
        }


        val filterButton = findViewById<Button>(R.id.filterButton)

        filterButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

            // Step 1: Pick start date
            DatePickerDialog(this, { _, startYear, startMonth, startDay ->
                val startDate = Calendar.getInstance()
                startDate.set(startYear, startMonth, startDay)

                // Step 2: Pick end date after start is picked
                DatePickerDialog(this, { _, endYear, endMonth, endDay ->
                    val endDate = Calendar.getInstance()
                    endDate.set(endYear, endMonth, endDay)

                    val startStr = dateFormat.format(startDate.time)
                    val endStr = dateFormat.format(endDate.time)

                    // Step 3: Fetch and show filtered totals
                    filterCategoriesByDateRange(startStr, endStr)

                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()

        }


        val dbHelper = PocketPlanDBHelper(this)
        val savedCategories = dbHelper.getAllCategories()
        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val container = findViewById<LinearLayout>(R.id.categoryCardsContainer)
        val budgetRemainingText = findViewById<TextView>(R.id.budgetRemaining)
        val maxSavingGoal = dbHelper.getMaxSavingGoal()        // Example overall max saving shown on top
        budgetRemainingText.text = formatCurrency(maxSavingGoal)



        for (category in savedCategories) {
            val goal = prefs.getString("${category}_goal", "0")?.toDoubleOrNull() ?: 0.0
            val spent = prefs.getString("${category}_spent", "0")?.toDoubleOrNull() ?: 0.0
            addCategoryCard(container, category, goal, spent)
        }
    }

    private fun filterCategoriesByDateRange(startMillis: Long, endMillis: Long) {
        val dbHelper = PocketPlanDBHelper(this)
        val filteredCategories = dbHelper.getCategoriesBetweenDates(startMillis, endMillis)

        val container = findViewById<LinearLayout>(R.id.categoryCardsContainer)
        container.removeAllViews() // Clear previous cards

        for (category in filteredCategories) {
            val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
            val goal = prefs.getString(category, "0")?.toDoubleOrNull() ?: 0.0
            val spent = 300.0 // Simulate or fetch real spent value

            addCategoryCard(container, category, goal, spent)
        }
    }

    private fun filterCategoriesByDateRange(startDate: String, endDate: String) {
        val dbHelper = PocketPlanDBHelper(this)
        val filteredTotals = dbHelper.getCategoryTotalsBetweenDates(startDate, endDate)

        // You can update your UI here however you like.
        // For example: rebuild the category cards or show a popup.

        val container = findViewById<LinearLayout>(R.id.categoryCardsContainer)
        container.removeAllViews() // Clear previous cards

        for ((categoryName, totalAmount) in filteredTotals) {
            val cardView = LayoutInflater.from(this).inflate(R.layout.category_card, container, false)

            val categoryNameView = cardView.findViewById<TextView>(R.id.categoryName)
            val categorySpent = cardView.findViewById<TextView>(R.id.categorySpent)
            val categoryBalance = cardView.findViewById<TextView>(R.id.balance)
            val categoryProgress = cardView.findViewById<ProgressBar>(R.id.categoryProgress)
            val categoryGoalAmount = cardView.findViewById<TextView>(R.id.categoryGoalAmount)
            categoryNameView.text = categoryName
            categorySpent.text = "-${formatCurrency(totalAmount)}"
            // You might fetch category goal from DB or preferences
            val goal = 5000.0 // Example fallback
            categoryGoalAmount.text = "Goal: ${formatCurrency(goal)}"
            val balance = goal - totalAmount
            categoryBalance.text = formatCurrency(balance)
            categoryProgress.progress = if (goal > 0) ((totalAmount / goal) * 100).toInt().coerceAtMost(100) else 0

            container.addView(cardView)
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
        categoryGoalAmount.text = "Goal: ${formatCurrency(totalBudget)}"
        categorySpent.text = "-${formatCurrency(amountSpent)}"
        categoryBalance.text = formatCurrency(balance)
        categoryProgress.progress = progress

        container.addView(cardView)
    }

    override fun onResume() {
        super.onResume()
        // Refresh the budget remaining display with current currency
        val budgetRemainingText = findViewById<TextView>(R.id.budgetRemaining)
        val dbHelper = PocketPlanDBHelper(this)
        val maxSavingGoal = dbHelper.getMaxSavingGoal()
        budgetRemainingText.text = formatCurrency(maxSavingGoal)
        
        // Also refresh category cards with current currency
        refreshCategoryCards()
    }
    
    private fun refreshCategoryCards() {
        val dbHelper = PocketPlanDBHelper(this)
        val savedCategories = dbHelper.getAllCategories()
        val prefs = getSharedPreferences("UserData", MODE_PRIVATE)
        val container = findViewById<LinearLayout>(R.id.categoryCardsContainer)
        
        container.removeAllViews() // Clear existing cards
        
        for (category in savedCategories) {
            val goal = prefs.getString("${category}_goal", "0")?.toDoubleOrNull() ?: 0.0
            val spent = prefs.getString("${category}_spent", "0")?.toDoubleOrNull() ?: 0.0
            addCategoryCard(container, category, goal, spent)
        }
    }
}
