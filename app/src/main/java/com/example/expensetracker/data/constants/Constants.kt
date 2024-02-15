package com.example.expensetracker.data.constants

import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import kotlin.random.Random

// Other constants (objects of class as Currency) in DataStoreManager

const val NAME_DEFAULT: String = "User"
const val LOGIN_COUNT_DEFAULT = 0
const val NEEDS_LOGIN : Boolean = true
const val BUDGET_DEFAULT : Int = 0
const val CURRENCY_DEFAULT : String = "USD"

val DEFAULT_CATEGORIES = listOf<ExpenseCategory>(
    ExpenseCategory(categoryId = 1, note = "Groceries", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 2, note = "Utilities", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 3, note = "Housing", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 4, note = "Transportation", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 5, note = "Dining Out", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 6, note = "Entertainment", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 7, note = "Healthcare", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 8, note = "Clothing and Accessories", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 9, note = "Personal Care", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 10, note = "Technology and Electronics", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 11, note = "Savings and Investments", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 12, note = "Unique", colorId = Random.nextLong(0, Long.MAX_VALUE))
)
