package com.example.track

import com.example.track.data.DataStoreManager
import com.example.track.data.database.ExpensesDB
import com.example.track.data.database.currenciesRelated.CurrenciesPreferenceDao
import com.example.track.data.database.currenciesRelated.CurrencyDao
import com.example.track.data.database.expensesRelated.ExpenseCategoryDao
import com.example.track.data.database.expensesRelated.ExpenseItemsDAO
import com.example.track.data.database.ideaRelated.IdeaDao
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.currencies.CurrencyListRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.example.track.data.viewmodels.common.BottomSheetViewModel
import com.example.track.data.viewmodels.login.LoginViewModel
import com.example.track.data.viewmodels.mainScreen.ExpensesLazyColumnViewModel
import com.example.track.data.viewmodels.mainScreen.MainScreenFeedViewModel
import com.example.track.data.viewmodels.settingsScreen.SettingsViewModel
import com.example.track.data.workers.CurrenciesRatesWorker
import com.example.track.domain.usecases.categoriesusecases.AddCategoryUseCase
import com.example.track.domain.usecases.categoriesusecases.DeleteCategoryUseCase
import com.example.track.domain.usecases.categoriesusecases.EditCategoryUseCase
import com.example.track.domain.usecases.categoriesusecases.GetCategoryListUseCase
import com.example.track.domain.usecases.expenseusecases.AddExpensesItemUseCase
import com.example.track.domain.usecases.expenseusecases.DeleteExpenseItemUseCase
import com.example.track.domain.usecases.expenseusecases.EditExpenseItemUseCase
import com.example.track.domain.usecases.expenseusecases.GetExpensesItemUseCase
import com.example.track.domain.usecases.expenseusecases.GetExpensesListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val appModule = module {
    single<ExpenseItemsDAO> { ExpensesDB.getInstance(androidContext()).expenseItemsDao }
    single<ExpensesListRepositoryImpl> { ExpensesListRepositoryImpl(get()) }

    single<ExpenseCategoryDao> { ExpensesDB.getInstance(androidContext()).categoryDao }
    single<ExpensesCategoriesListRepositoryImpl> { ExpensesCategoriesListRepositoryImpl(get()) }

    single<CurrencyDao> { ExpensesDB.getInstance(androidContext()).currencyDao }
    single<CurrencyListRepositoryImpl> { CurrencyListRepositoryImpl(get()) }

    single<IdeaDao> { ExpensesDB.getInstance(androidContext()).ideaDao }
    single<IdeaListRepositoryImpl> { IdeaListRepositoryImpl(get()) }

    single<CurrenciesPreferenceDao> { ExpensesDB.getInstance(androidContext()).currenciesPreferenceDao }
    single<CurrenciesPreferenceRepositoryImpl> { CurrenciesPreferenceRepositoryImpl(get()) }
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

val workerFactoryModule = module {
    worker { CurrenciesRatesWorker(get(), get(), get()) }
}

val settingsModule = module {
    single<DataStoreManager> { DataStoreManager(get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { BottomSheetViewModel(get(), get(), get()) }
    viewModel { ExpensesLazyColumnViewModel(get(), get()) }
    viewModel { MainScreenFeedViewModel(get()) }
    viewModel { SettingsViewModel(get(), get(), get()) }
}