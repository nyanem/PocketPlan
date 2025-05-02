package com.example.pocketplan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    private val context: Context,
    private val categories: List<String> // or List<Category>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryName: TextView = itemView.findViewById(R.id.categoryName)
        val categoryGoalAmount: TextView = itemView.findViewById(R.id.maxsavings_txtbox)
        val categoryBalance: TextView = itemView.findViewById(R.id.balance)
        val categorySpent: TextView = itemView.findViewById(R.id.categorySpent)
        val categoryProgress: ProgressBar = itemView.findViewById(R.id.categoryProgress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.category_card, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.categoryName.text = category

        // Get goal from SharedPreferences
        val prefs = context.getSharedPreferences("GoalPrefs", Context.MODE_PRIVATE)
        val goalAmount = prefs.getString(category, "0") ?: "0"
        holder.categoryGoalAmount.text = "Goal: R$goalAmount"

        // Placeholder: Fetch spent & balance from your data source
        val spent = 300 // example only
        val goal = goalAmount.toFloatOrNull() ?: 0f
        val balance = goal - spent

        holder.categorySpent.text = "-R$spent"
        holder.categoryBalance.text = "R$balance"

        // Progress bar calculation
        val progress = if (goal > 0) ((spent / goal) * 100).toInt() else 0
        holder.categoryProgress.progress = progress
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}
