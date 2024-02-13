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
    ExpenseCategory(categoryId = 1, name = "Groceries", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 2, name = "Utilities", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 3, name = "Housing", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 4, name = "Transportation", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 5, name = "Dining Out", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 6, name = "Entertainment", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 7, name = "Healthcare", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 8, name = "Clothing and Accessories", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 9, name = "Personal Care", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 10, name = "Technology and Electronics", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 11, name = "Savings and Investments", colorId = Random.nextLong(0, Long.MAX_VALUE)),
    ExpenseCategory(categoryId = 12, name = "Unique", colorId = Random.nextLong(0, Long.MAX_VALUE))
)
