package com.example.pocketplan


import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CategoryActivity : AppCompatActivity() {

    //    private lateinit var db: AppDatabase
//    private lateinit var categoryDao: CategoryDao

    private lateinit var dbHelper: CategoryDatabaseHelper
//    private val selectedCategories = mutableListOf<String>()
    private lateinit var db: AppDatabase
    private val selectedCategories = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val dbHelper = CategoryDatabaseHelper(this)
        db = AppDatabase.getDatabase(this)

        val buttons = listOf(
            Pair(R.id.Groceries, "Groceries"),
            Pair(R.id.Rent, "Rent"),
            Pair(R.id.Petrol, "Petrol"),
            Pair(R.id.Bills, "Bills"),
            Pair(R.id.Home, "Home"),
            Pair(R.id.Vacation, "Vacation")
        )

        buttons.forEach { (id, categoryName) ->
            findViewById<Button>(id).setOnClickListener {
                if (!selectedCategories.contains(categoryName)) {
                    selectedCategories.add(categoryName)
                    it.setBackgroundColor(Color.parseColor("#5C6BC0")) // Optional highlight
                    Toast.makeText(this, "$categoryName selected", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val customBtn = findViewById<Button>(R.id.btnAddCategory)
        val customInput = findViewById<EditText>(R.id.editTextCustomCategory)

        customBtn.setOnClickListener {
            val customName = customInput.text.toString().trim()
            if (customName.isNotEmpty() && !selectedCategories.contains(customName)) {
                selectedCategories.add(customName)
                Toast.makeText(this, "$customName added", Toast.LENGTH_SHORT).show()
                customInput.setText("")
            }
        }

        val selectBtn = findViewById<Button>(R.id.selectCategoriesButton)
        selectBtn.setOnClickListener {
            lifecycleScope.launch {
                selectedCategories.forEach {
                    db.categoryDao().insertCategory(CategoryEntity(name = it))
                }
                Toast.makeText(this@CategoryActivity, "Categories saved!", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this@CategoryActivity, GoalsActivity::class.java)
                startActivity(intent)
            }
        }

        findViewById<Button>(R.id.selectCategoriesButton).setOnClickListener {
            selectedCategories.forEach { dbHelper.insertCategory(it) }

            val intent = Intent(this, GoalsActivity::class.java)
//            intent.putExtra("categories", selectedCategories.toTypedArray())
            startActivity(intent)
        }
    }
}

