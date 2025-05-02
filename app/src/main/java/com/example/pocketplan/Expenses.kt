package com.example.pocketplan

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// We'll use the Transaction from Models.kt now
// data class is in Models.kt

class Expenses : AppCompatActivity() {

    private lateinit var transactionList: RecyclerView
    private lateinit var balanceText: TextView
    private lateinit var rewardsButton: Button
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses)

        // Initialize DatabaseHelper
        dbHelper = DatabaseHelper(this)

        // Initialize views
        balanceText = findViewById(R.id.balanceTextView)
        transactionList = findViewById(R.id.transactionRecyclerView)
        rewardsButton = findViewById(R.id.btn_rewards)

        // Set up RecyclerView
        transactionList.layoutManager = LinearLayoutManager(this)

        // Load and display transactions
        loadTransactions()

        rewardsButton.setOnClickListener {
            val intent = Intent(this, Rewards::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload transactions when returning to this screen
        // (e.g., after adding a new transaction)
        loadTransactions()
    }

    private fun loadTransactions() {
        // Get transactions from database
        val transactions = dbHelper.getAllTransactions()

        // Calculate and display balance
        val balance = transactions.sumOf { it.amount }
        balanceText.text = "R${String.format("%.2f", balance)}"

        // Set up adapter with transactions
        adapter = TransactionAdapter(transactions)
        transactionList.adapter = adapter
    }
}
