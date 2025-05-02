package com.example.pocketplan



import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class PocketPlanDBHelper(context: Context) :

    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "PocketPlan.db"
        private const val DATABASE_VERSION = 2

        // Table: Categories
        private const val CATEGORY_TABLE_NAME = "selected_categories"
        private const val CATEGORY_COLUMN_ID = "id"
        private const val CATEGORY_COLUMN_NAME = "name"

        // Table: Users
        private const val USER_TABLE_NAME = "users"
        private const val USER_COLUMN_ID = "id"
        private const val USER_COLUMN_USERNAME = "username"
        private const val USER_COLUMN_PASSWORD = "password"
        private const val USER_COLUMN_NAME = "name"
        private const val USER_COLUMN_SURNAME = "surname"
        private const val USER_COLUMN_MOBILE = "mobile"
        private const val USER_COLUMN_EMAIL = "email"

        // Table: Survey
        private const val SURVEY_TABLE_NAME = "survey_data"
        private const val SURVEY_COLUMN_ID = "id"
        private const val SURVEY_COLUMN_INCOME = "income"
        private const val SURVEY_COLUMN_MAX_SAVING = "max_saving"
        private const val SURVEY_COLUMN_MIN_SAVING = "min_saving"

        // Table: Category Goal Amount
        private const val CATEGORY_GOAL_TABLE_NAME = "categories_goal"
        private const val GOAL_COLUMN_ID = "id"
        private const val CATEGORY_NAME_COLUMN = "category name"
        private const val GOAL_AMOUNT_COLUMN = "goal amount"

    }

    override fun onCreate(db: SQLiteDatabase) {
        val createCategoryTable = """
            CREATE TABLE IF NOT EXISTS $CATEGORY_TABLE_NAME (
                $CATEGORY_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $CATEGORY_COLUMN_NAME TEXT NOT NULL
            )
        """

        val createUserTable = """
            CREATE TABLE IF NOT EXISTS $USER_TABLE_NAME (
                $USER_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $USER_COLUMN_USERNAME TEXT,
                $USER_COLUMN_PASSWORD TEXT,
                $USER_COLUMN_NAME TEXT,
                $USER_COLUMN_SURNAME TEXT,
                $USER_COLUMN_MOBILE TEXT,
                $USER_COLUMN_EMAIL TEXT
            )
        """

        val createSurveyTable = """
    CREATE TABLE IF NOT EXISTS $SURVEY_TABLE_NAME (
        $SURVEY_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $SURVEY_COLUMN_INCOME REAL NOT NULL,
        $SURVEY_COLUMN_MAX_SAVING REAL NOT NULL,
        $SURVEY_COLUMN_MIN_SAVING REAL NOT NULL
    )
"""

       val createCategoryGoalTable ="""
    CREATE TABLE IF NOT EXISTS $CATEGORY_GOAL_TABLE_NAME (
        $GOAL_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $CATEGORY_NAME_COLUMN TEXT NOT NULL,
        $GOAL_AMOUNT_COLUMN REAL NOT NULL
    ) 
"""


        db.execSQL(createCategoryGoalTable)
        db.execSQL(createSurveyTable)
        db.execSQL(createCategoryTable)
        db.execSQL(createUserTable)
    }



    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS $CATEGORY_TABLE_NAME")
//        db.execSQL("DROP TABLE IF EXISTS $USER_TABLE_NAME")
        onCreate(db)
    }

    // --- CATEGORY METHODS ---

    fun insertCategories(name: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("name", name)
        }
        val result = db.insert("selected_categories", null, contentValues)
        return result != -1L
    }


    fun getAllCategories(): List<String> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT DISTINCT name FROM selected_categories", null)
        val list = mutableListOf<String>()
        while (cursor.moveToNext()) {
            list.add(cursor.getString(0))
        }
        cursor.close()
        return list
    }



    // --- USER METHODS ---
    fun insertUser(username: String, password: String, name: String, surname: String, mobile: String, email: String): Boolean {
        val db = this.writableDatabase

        val contentValues = ContentValues().apply {
            put(USER_COLUMN_USERNAME, username)
            put(USER_COLUMN_PASSWORD, password)
            put(USER_COLUMN_NAME, name)
            put(USER_COLUMN_SURNAME, surname)
            put(USER_COLUMN_MOBILE, mobile)
            put(USER_COLUMN_EMAIL, email)
        }
        val result = db.insert(USER_TABLE_NAME, null, contentValues)
        return result != -1L

    }



    fun userExists(username: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $USER_TABLE_NAME WHERE $USER_COLUMN_USERNAME = ?",
            arrayOf(username)
        )
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }

    fun checkLogin(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $USER_TABLE_NAME WHERE $USER_COLUMN_USERNAME = ? AND $USER_COLUMN_PASSWORD = ?",
            arrayOf(username, password)
        )
        val valid = cursor.moveToFirst()
        cursor.close()
        return valid
    }

    fun getAllUsers(): List<String> {
        val db = readableDatabase
        val users = mutableListOf<String>()
        val cursor = db.rawQuery("SELECT username FROM users", null)
        if (cursor.moveToFirst()) {
            do {
                users.add(cursor.getString(0))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return users
    }
    fun insertSurveyData(income: Double, maxSaving: Double, minSaving: Double): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(SURVEY_COLUMN_INCOME, income)
            put(SURVEY_COLUMN_MAX_SAVING, maxSaving)
            put(SURVEY_COLUMN_MIN_SAVING, minSaving)
        }
        val result = db.insert(SURVEY_TABLE_NAME, null, contentValues)
        return result != -1L
    }

    fun getLatestSurveyData(): Triple<Double, Double, Double>? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT $SURVEY_COLUMN_INCOME, $SURVEY_COLUMN_MAX_SAVING, $SURVEY_COLUMN_MIN_SAVING FROM $SURVEY_TABLE_NAME ORDER BY $SURVEY_COLUMN_ID DESC LIMIT 1",
            null
        )
        return if (cursor.moveToFirst()) {
            val income = cursor.getDouble(0)
            val maxSaving = cursor.getDouble(1)
            val minSaving = cursor.getDouble(2)
            cursor.close()
            Triple(income, maxSaving, minSaving)
        } else {
            cursor.close()
            null
        }

    }

    fun insertCategoryGoal(categoryName: String, goalAmount: Double): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("category_name", categoryName)
            put("goal_amount", goalAmount)
        }

        val result = db.insertWithOnConflict("category_goals", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
        return result != -1L
    }

    fun getGoalAmount(categoryName: String): Double? {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT goal_amount FROM category_goals WHERE category_name = ?",
            arrayOf(categoryName)
        )

        var amount: Double? = null
        if (cursor.moveToFirst()) {
            amount = cursor.getDouble(0)
        }

        cursor.close()
        db.close()
        return amount
    }

    fun insertOrUpdateGoal(category: String, amount: String) {
        val db = writableDatabase
        val stmt = db.compileStatement("INSERT OR REPLACE INTO goals (category, amount) VALUES (?, ?)")
        stmt.bindString(1, category)
        stmt.bindString(2, amount)
        stmt.execute()
        stmt.close()
    }

    fun getGoalForCategory(category: String): String {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT amount FROM goals WHERE category = ?", arrayOf(category))
        var goal = ""
        if (cursor.moveToFirst()) {
            goal = cursor.getString(0)
        }
        cursor.close()
        return goal
    }

    fun getMaxSavingGoal(): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT max_Saving FROM survey_data", null)
        var maxGoal = 0.0

        if (cursor.moveToFirst()) {
            maxGoal = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return maxGoal
    }
    fun getCategoriesBetweenDates(startMillis: Long, endMillis: Long): List<String> {
        val db = readableDatabase
        val categories = mutableListOf<String>()

        val cursor = db.rawQuery(
            "SELECT DISTINCT category FROM survey WHERE dateMillis BETWEEN ? AND ?",
            arrayOf(startMillis.toString(), endMillis.toString())
        )

        while (cursor.moveToNext()) {
            categories.add(cursor.getString(0))
        }

        cursor.close()
        db.close()
        return categories
    }
    fun getCategoryTotalsBetweenDates(startDate: String, endDate: String): Map<String, Double> {
        val totals = mutableMapOf<String, Double>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT category, SUM(amount) as total FROM transactions WHERE date BETWEEN ? AND ? GROUP BY category",
            arrayOf(startDate, endDate)
        )

        while (cursor.moveToNext()) {
            val category = cursor.getString(cursor.getColumnIndexOrThrow("category"))
            val total = cursor.getDouble(cursor.getColumnIndexOrThrow("total"))
            totals[category] = total
        }
        cursor.close()
        return totals
    }

}





