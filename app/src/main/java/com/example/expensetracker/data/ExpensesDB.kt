package com.example.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.expensetracker.domain.ExpenseItem

@Database(
    entities = [ExpenseItem::class],
     version = 1
)
abstract class ExpensesDB : RoomDatabase() {
abstract val dao : ExpensesDAO
    companion object{
        fun createDataBase(context : Context) : ExpensesDB{
            return Room.databaseBuilder(context, ExpensesDB::class.java,"main.db").build()
        }
    }
}