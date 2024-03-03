package com.example.expensetracker

import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.database.CurrencyDao
import com.example.expensetracker.data.database.ExpenseCategoryDao
import com.example.expensetracker.data.database.ExpenseItemsDAO
import com.example.expensetracker.data.database.ExpensesDB
import com.example.expensetracker.data.database.IdeaDao
import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.implementations.CurrencyListRepositoryImpl
import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.implementations.IdeaListRepositoryImpl
import com.example.expensetracker.data.viewmodels.common.BottomSheetViewModel
import com.example.expensetracker.data.viewmodels.login.LoginViewModel
import com.example.expensetracker.data.viewmodels.mainScreen.ExpensesLazyColumnViewModel
import com.example.expensetracker.data.viewmodels.mainScreen.MainScreenFeedViewModel
import com.example.expensetracker.data.viewmodels.settingsScreen.SettingsViewModel
import com.example.expensetracker.data.workers.CurrenciesRatesWorker
import com.example.expensetracker.domain.usecases.categoriesusecases.AddCategoryUseCase
import com.example.expensetracker.domain.usecases.categoriesusecases.DeleteCategoryUseCase
import com.example.expensetracker.domain.usecases.categoriesusecases.EditCategoryUseCase
import com.example.expensetracker.domain.usecases.categoriesusecases.GetCategoryListUseCase
import com.example.expensetracker.domain.usecases.expenseusecases.AddExpensesItemUseCase
import com.example.expensetracker.domain.usecases.expenseusecases.DeleteExpenseItemUseCase
import com.example.expensetracker.domain.usecases.expenseusecases.EditExpenseItemUseCase
import com.example.expensetracker.domain.usecases.expenseusecases.GetExpensesItemUseCase
import com.example.expensetracker.domain.usecases.expenseusecases.GetExpensesListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val appModule = module {
    single<ExpenseItemsDAO> { ExpensesDB.getInstance(androidContext()).expenseItemsDao }
    single<ExpensesListRepositoryImpl> { ExpensesListRepositoryImpl(get()) }

    single<ExpenseCategoryDao> { ExpensesDB.getInstance(androidContext()).categoryDao }
    single<CategoriesListRepositoryImpl> { CategoriesListRepositoryImpl(get()) }

    single<CurrencyDao> { ExpensesDB.getInstance(androidContext()).currencyDao }
    single<CurrencyListRepositoryImpl> { CurrencyListRepositoryImpl(get()) }

    single<IdeaDao> { ExpensesDB.getInstance(androidContext()).ideaDao }
    single<IdeaListRepositoryImpl> { IdeaListRepositoryImpl(get()) }
}

val workerFactoryModule = module {
    worker { CurrenciesRatesWorker(get(), get(), get()) }
}

val domainModule = module {
    factory<AddExpensesItemUseCase> { AddExpensesItemUseCase(get()) }
    factory<DeleteExpenseItemUseCase> { DeleteExpenseItemUseCase(get()) }
    factory<EditExpenseItemUseCase> { EditExpenseItemUseCase(get()) }
    factory<GetExpensesItemUseCase> { GetExpensesItemUseCase(get()) }
    factory<GetExpensesListUseCase> { GetExpensesListUseCase(get()) }

    factory<AddCategoryUseCase> { AddCategoryUseCase(get()) }
    factory<DeleteCategoryUseCase> { DeleteCategoryUseCase(get()) }
    factory<EditCategoryUseCase> { EditCategoryUseCase(get()) }
    factory<GetCategoryListUseCase> { GetCategoryListUseCase() }
}

val settingsModule = module {
    single<DataStoreManager> { DataStoreManager(get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { BottomSheetViewModel(get(), get(), get(), get()) }
    viewModel { ExpensesLazyColumnViewModel(get(), get()) }
    viewModel { MainScreenFeedViewModel(get()) }
    viewModel { SettingsViewModel(get(), get()) }
}