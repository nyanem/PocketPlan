//package com.example.pocketplan
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//
//@Dao
//interface UserDao {
//    @Insert(onConflict = OnConflictStrategy.ABORT)
//    suspend fun insertUser(user: User)
//
//    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
//    suspend fun getUser(username: String, password: String): User?
//
//    @Query("SELECT * FROM users WHERE username = :username")
//    suspend fun getUserByUsername(username: String): User?
//}
