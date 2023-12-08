package com.example.expensetracker.data

class CategoryContainer {
    companion object{
        val defaultCategories = listOf<String>(
            "Groceries",
                    "Utilities",// - Коммунальные услуги
                    "Housing",// - Жилье
                    "Transportation",// - Транспорт
                    "Dining Out",// - Питание вне дома
                    "Entertainment",// - Развлечения
                    "Healthcare",// - Здравоохранение
                    "Clothing and Accessories",// - Одежда и аксессуары
                    "Personal Care",// - Личная гигиена и уход
                    "Technology and Electronics",// - Техника и электроника
                   " Travel",// - Путешествия
                    "Savings and Investments")// - Сбережения и инвестиции
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