package com.example.expensetracker.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_categories")
data class ExpenseCategory(
    @PrimaryKey(autoGenerate = true)
    var categoryId: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "colorId")
    val colorId: Int
)