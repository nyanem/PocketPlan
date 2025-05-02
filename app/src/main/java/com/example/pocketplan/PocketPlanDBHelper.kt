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
        private const val DATABASE_VERSION = 1

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

//    fun getAllCategoryAmounts(): List<Pair<String, Double>> {
//        val db = this.readableDatabase
//        val cursor = db.rawQuery("SELECT name, amount FROM selected_categories", null)
//        val result = mutableListOf<Pair<String, Double>>()
//        while (cursor.moveToNext()) {
//            val name = cursor.getString(0)
//            val amount = cursor.getDouble(1)
//            result.add(name to amount)
//        }
//        cursor.close()
//        return result
//    }



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

}


