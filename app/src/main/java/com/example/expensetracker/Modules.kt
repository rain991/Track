package com.example.expensetracker

import com.example.expensetracker.data.database.ExpensesDAO
import com.example.expensetracker.data.database.ExpensesDB
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<ExpensesDAO> { ExpensesDB.getInstance(androidContext()).dao }
    single<ExpensesListRepositoryImpl> { ExpensesListRepositoryImpl(get()) }  // WARNING ExpensesListRepositoryImpl()
    }