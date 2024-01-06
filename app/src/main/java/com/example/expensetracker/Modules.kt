package com.example.expensetracker

import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.database.ExpensesDAO
import com.example.expensetracker.data.database.ExpensesDB
import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.domain.usecases.categoriesusecases.AddCategoryUseCase
import com.example.expensetracker.domain.usecases.categoriesusecases.DeleteCategoryUseCase
import com.example.expensetracker.domain.usecases.categoriesusecases.EditCategoryUseCase
import com.example.expensetracker.domain.usecases.categoriesusecases.GetCategoryListUseCase
import com.example.expensetracker.domain.usecases.categoriesusecases.GetCategoryUseCase
import com.example.expensetracker.domain.usecases.expenseusecases.AddExpensesItemUseCase
import com.example.expensetracker.domain.usecases.expenseusecases.DeleteExpenseItemUseCase
import com.example.expensetracker.domain.usecases.expenseusecases.EditExpenseItemUseCase
import com.example.expensetracker.domain.usecases.expenseusecases.GetExpensesItemUseCase
import com.example.expensetracker.domain.usecases.expenseusecases.GetExpensesListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single<ExpensesDAO> { ExpensesDB.getInstance(androidContext()).dao }
    single<ExpensesListRepositoryImpl> { ExpensesListRepositoryImpl(get()) }

    single<ExpenseCategoryDao>{ExpensesDB.getInstance(androidContext()).categoryDao}
    single<CategoriesListRepositoryImpl>{ CategoriesListRepositoryImpl(get()) }
}

val domainModule = module{
    factory<AddExpensesItemUseCase> { AddExpensesItemUseCase(get()) }
    factory<DeleteExpenseItemUseCase>{DeleteExpenseItemUseCase(get())}
    factory<EditExpenseItemUseCase>{EditExpenseItemUseCase(get())}
    factory<GetExpensesItemUseCase>{ GetExpensesItemUseCase(get()) }
    factory<GetExpensesListUseCase>{ GetExpensesListUseCase(get()) }

    factory<AddCategoryUseCase>{ AddCategoryUseCase(get()) }
    factory<DeleteCategoryUseCase>{DeleteCategoryUseCase(get())}
    factory<EditCategoryUseCase>{EditCategoryUseCase(get())}
    factory<GetCategoryUseCase>{GetCategoryUseCase(get())}
    factory<GetCategoryListUseCase>{GetCategoryListUseCase(get())}
}