

//package com.example.pocketplan
//
//import android.content.Intent
//import android.graphics.Color
//import android.os.Bundle
//import android.widget.Button
//import android.widget.EditText
//import android.widget.LinearLayout
//import android.widget.TextView
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import androidx.core.content.res.ResourcesCompat
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class GoalsActivity : AppCompatActivity() {
//
//    private val inputMap = mutableMapOf<String, EditText>()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_goals)
//
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        val goalContainer = findViewById<LinearLayout>(R.id.goalContainer)
//
//        val dbHelper = CategoryDatabaseHelper(this)
//        val savedCategories = dbHelper.getAllCategories()
//
//        for (category in getAllCategories) {
//            // Create category label
//            val label = TextView(this).apply {
//                text = category
//                textSize = 17f
//                setTextColor(Color.parseColor("#AAB6E0"))
//                typeface = ResourcesCompat.getFont(this@GoalsActivity, R.font.montserrat_semibold)
//                layoutParams = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//                ).apply {
//                    topMargin = 20
//                }
//            }
//
//            // Create input field
//            val input = EditText(this).apply {
//                hint = "Enter amount"
//                setTextColor(Color.BLACK)
//                setHintTextColor(Color.parseColor("#6E79BA"))
//                background = ContextCompat.getDrawable(this@GoalsActivity, R.drawable.rounded_edittext)
//                setPadding(30, 30, 30, 30)
//                layoutParams = LinearLayout.LayoutParams(
//                    dpToPx(240),
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//                ).apply {
//                    topMargin = 30
//                }
//            }
//
//            inputMap[category] = input // Store input for later use if needed
//
//            goalContainer.addView(label)
//            goalContainer.addView(input)
//        }
//
//        val saveGoalsButton = findViewById<Button>(R.id.saveGoalsButton)
//        saveGoalsButton.setOnClickListener {
//            // Example: log or process inputs
//            for ((category, input) in inputMap) {
//                val value = input.text.toString()
//                // You can save these values to a DB or pass them to the next activity
//                println("Category: $category, Goal: $value")
//            }
//
//            val intent = Intent(this, HomePageActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    private fun dpToPx(dp: Int): Int {
//        val density = resources.displayMetrics.density
//        return (dp * density).toInt()
//    }
//}

package com.example.pocketplan

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class GoalsActivity : BaseActivity() {

    private val inputMap = mutableMapOf<String, EditText>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val goalContainer = findViewById<LinearLayout>(R.id.goalContainer)
        val dbHelper = PocketPlanDBHelper(this)
        val savedCategories = dbHelper.getAllCategories()
        val prefs = getSharedPreferences("GoalPrefs", MODE_PRIVATE)



        for (category in savedCategories) {
            // Create category label
            val label = TextView(this).apply {
                text = category
                textSize = 17f
                setTextColor(Color.parseColor("#AAB6E0"))
                typeface = ResourcesCompat.getFont(this@GoalsActivity, R.font.montserrat_semibold_family)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 20
                }
            }            // Create input field
            val input = EditText(this).apply {
                hint = getString(R.string.enter_amount)
                setTextColor(Color.BLACK)
                setHintTextColor(Color.parseColor("#6E79BA"))
                background = ContextCompat.getDrawable(this@GoalsActivity, R.drawable.rounded_edittext)
                setPadding(30, 30, 30, 30)
                layoutParams = LinearLayout.LayoutParams(
                    dpToPx(240),
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = 10
                }

                // Pre-fill if a goal was saved previously
                setText(prefs.getString(category, ""))
            }

            inputMap[category] = input

            goalContainer.addView(label)
            goalContainer.addView(input)

        }



        val saveGoalsButton = findViewById<Button>(R.id.saveGoalsButton)
        saveGoalsButton.setOnClickListener {
            val editor = prefs.edit()

            for ((category, input) in inputMap) {
                val amount = input.text.toString().trim()
                if (amount.isNotEmpty()) {
                    editor.putString(category, amount)
                }
            }
            editor.apply()

            Toast.makeText(this, getString(R.string.goals_saved_successfully), Toast.LENGTH_SHORT).show()

            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}

//private fun PocketPlanDBHelper.Companion.insertCategories(string: String, i: Int) {}
