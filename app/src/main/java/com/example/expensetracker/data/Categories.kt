package com.example.expensetracker.data

import android.content.Context
import com.example.expensetracker.R

class CategoryContainer {
    companion object {
        fun getDefaultCategories(context: Context): List<String> {
        return context.resources.getStringArray(R.array.default_expenses).toList()
    }
    }
    private val categories: MutableList<String> = mutableListOf()

    fun addCategory(category: String) {
        categories.add(category)
    }

    fun removeCategory(category: String) {
        categories.remove(category)
    }

    fun getCategories(): List<String> {
        return categories.toList()   // copy of list returned
    }
}