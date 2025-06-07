package com.example.pocketplan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategorySelectionActivity : BaseActivity() {    private lateinit var recyclerView: RecyclerView
    private lateinit var dbHelper: PocketPlanDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_selection)

        dbHelper = PocketPlanDBHelper(this)
        recyclerView = findViewById(R.id.recyclerViewCategories)

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

//        // Get categories from database
//        val categories = dbHelper.getAllCategories()
//
//        // Set up adapter
//        val adapter = CategoryAdapter(categories) { category ->
//            // Return selected category to AddTransaction
//            val returnIntent = Intent()
//            returnIntent.putExtra("category_id", category.id)
//            returnIntent.putExtra("category_name", category.name)
//            setResult(RESULT_OK, returnIntent)
//            finish()
//        }

//        recyclerView.adapter = adapter
    }

    // Category adapter for the RecyclerView
    inner class CategoryAdapter(
        private val categories: List<Category>,
        private val onCategoryClick: (Category) -> Unit
    ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val categoryName: TextView = itemView.findViewById(R.id.textViewCategoryTitle)

            init {
                itemView.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onCategoryClick(categories[position])
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_category_selection, parent, false)
            return CategoryViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            val category = categories[position]
            holder.categoryName.text = category.name
        }

        override fun getItemCount() = categories.size
    }
}

private fun DatabaseHelper.getAllCategories() {

}
