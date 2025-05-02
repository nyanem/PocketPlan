package com.example.pocketplan
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String

//    val goalAmount: Double,
//    val spentAmount: Double
)