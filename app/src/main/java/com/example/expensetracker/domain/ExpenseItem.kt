package com.example.expensetracker.domain

data class ExpenseItem(
    val name: String,
    val date: String,
    val enabled: Boolean,
    val value: Float,
    var id: Int = UNDEFINED_ID
) {
companion object{
    const val UNDEFINED_ID = -1
}
}
