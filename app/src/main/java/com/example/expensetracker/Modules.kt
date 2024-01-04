package com.example.expensetracker

import com.example.expensetracker.data.ExpensesDAO
import com.example.expensetracker.data.ExpensesDB
import com.example.expensetracker.data.ExpensesListRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<ExpensesDAO> { ExpensesDB.getInstance(androidContext()).dao }
    single<ExpensesListRepositoryImpl> { ExpensesListRepositoryImpl(get()) }  // WARNING ExpensesListRepositoryImpl()
    }