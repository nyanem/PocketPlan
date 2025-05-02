package com.example.pocketplan

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val username: String,
    val password: String,
    val name: String,
    val surname: String,
    val mobile: String,
    val email: String
)

