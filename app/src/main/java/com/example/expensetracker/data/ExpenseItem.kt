package com.example.expensetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Expenses")
data class ExpenseItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = UNDEFINED_ID,
    val name: String,
    val date: String,
    val enabled: Boolean = false,
    val categoryId: Int,
    val value: Float
) {
    companion object {
        const val UNDEFINED_ID = -1
    }
}
