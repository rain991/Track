package com.example.expensetracker.data.models.expenses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// to implement changes of default_categories colors, should be defined migration proccess (?)
@Entity(tableName = "expense_categories")
data class ExpenseCategory(
    @PrimaryKey(autoGenerate = true)
    var categoryId: Int = 0,

    @ColumnInfo(name = "note")
    val note: String,

    @ColumnInfo(name = "colorId")
    val colorId: Long
)