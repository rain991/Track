package com.example.expensetracker.data.models.Expenses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expense_categories")
data class ExpenseCategory(
    @PrimaryKey(autoGenerate = true)
    var categoryId: Int = 0,

    @ColumnInfo(name = "note")
    val name: String,

    @ColumnInfo(name = "colorId")
    val colorId: Long
)