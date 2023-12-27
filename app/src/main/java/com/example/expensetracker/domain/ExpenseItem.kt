package com.example.expensetracker.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Expenses")
data class ExpenseItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = UNDEFINED_ID,
    val name: String,
    val date: String,
    val enabled: Boolean = false,  // Warning it had no default value previously, db version was not changed, could be source of error in db
    val value: Float
) {
companion object{
    const val UNDEFINED_ID = -1
}
}
