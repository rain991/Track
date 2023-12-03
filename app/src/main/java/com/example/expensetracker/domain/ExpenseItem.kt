package com.example.expensetracker.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Expenses")
data class ExpenseItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = UNDEFINED_ID,
    val name: String,
    val date: String,
    val enabled: Boolean,
    val value: Float
) {
companion object{
    const val UNDEFINED_ID = -1
}
}
