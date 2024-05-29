package com.example.track

import com.example.track.data.core.ChartHandler
import com.example.track.data.core.CurrenciesRatesHandler
import com.example.track.data.core.NotesHandler
import com.example.track.data.database.currenciesRelated.CurrenciesPreferenceDao
import com.example.track.data.database.currenciesRelated.CurrencyDao
import com.example.track.data.database.db.ExpensesDB
import com.example.track.data.database.expensesRelated.ExpenseCategoryDao
import com.example.track.data.database.expensesRelated.ExpenseItemsDAO
import com.example.track.data.database.ideaRelated.ExpenseLimitsDao
import com.example.track.data.database.ideaRelated.IncomePlansDao
import com.example.track.data.database.ideaRelated.SavingsDao
import com.example.track.data.database.incomeRelated.IncomeCategoryDao
import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.data.implementations.charts.ChartsRepositoryImpl
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.currencies.CurrencyListRepositoryImpl
import com.example.track.data.implementations.expenses.ExpenseItemRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesCoreRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.implementations.ideas.BudgetIdeaCardRepositoryImpl
import com.example.track.data.implementations.ideas.ExpenseLimitsCardRepositoryImpl
import com.example.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.example.track.data.implementations.ideas.IncomePlanCardRepositoryImpl
import com.example.track.data.implementations.ideas.SavingsCardRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeCoreRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeItemRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeListRepositoryImpl
import com.example.track.data.implementations.incomes.categories.IncomesCategoriesListRepositoryImpl
import com.example.track.data.implementations.notes.NotesRepositoryImpl
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.other.workers.CurrenciesRatesWorker
import com.example.track.data.viewmodels.common.BottomSheetViewModel
import com.example.track.data.viewmodels.login.LoginViewModel
import com.example.track.data.viewmodels.mainScreen.AddToSavingIdeaDialogViewModel
import com.example.track.data.viewmodels.mainScreen.BudgetIdeaCardViewModel
import com.example.track.data.viewmodels.mainScreen.ExpenseAndIncomeLazyColumnViewModel
import com.example.track.data.viewmodels.mainScreen.TrackScreenFeedViewModel
import com.example.track.data.viewmodels.mainScreen.TrackScreenInfoCardsViewModel
import com.example.track.data.viewmodels.settingsScreen.CurrenciesSettingsViewModel
import com.example.track.data.viewmodels.statistics.StatisticsViewModel
import com.example.track.domain.usecases.expensesRelated.categoriesusecases.AddCategoryUseCase
import com.example.track.domain.usecases.expensesRelated.categoriesusecases.DeleteCategoryUseCase
import com.example.track.domain.usecases.expensesRelated.categoriesusecases.EditCategoryUseCase
import com.example.track.domain.usecases.expensesRelated.expenseusecases.AddExpensesItemUseCase
import com.example.track.domain.usecases.expensesRelated.expenseusecases.DeleteExpenseItemUseCase
import com.example.track.domain.usecases.expensesRelated.expenseusecases.EditExpenseItemUseCase
import com.example.track.domain.usecases.expensesRelated.expenseusecases.GetExpensesListUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerFactoryModule = module {
    worker { CurrenciesRatesWorker(get(), get(), get()) }
}

val settingsModule = module {
    single<DataStoreManager> { DataStoreManager(get()) }
}

val coreModule = module {
    single<CurrenciesRatesHandler> { CurrenciesRatesHandler(get(), get()) }
    single<NotesHandler> { NotesHandler(get(), get(), get(), get()) }
    single<ChartHandler> { ChartHandler() }
}

val appModule = module {
    // Expense related
    single<ExpenseItemRepositoryImpl> { ExpenseItemRepositoryImpl(get()) }
    single<ExpensesCoreRepositoryImpl> { ExpensesCoreRepositoryImpl(get(), get(), get()) }
    single<ExpensesListRepositoryImpl> { ExpensesListRepositoryImpl(get()) }
    single<ExpensesCategoriesListRepositoryImpl> { ExpensesCategoriesListRepositoryImpl(get()) }

    // Income related
    single<IncomeListRepositoryImpl> { IncomeListRepositoryImpl(get()) }
    single<IncomesCategoriesListRepositoryImpl> { IncomesCategoriesListRepositoryImpl(get()) }
    single<IncomeItemRepositoryImpl> { IncomeItemRepositoryImpl(get()) }
    single<IncomeCoreRepositoryImpl> { IncomeCoreRepositoryImpl(get(), get(), get()) }

    // Currencies
    single<CurrencyListRepositoryImpl> { CurrencyListRepositoryImpl(get()) }
    single<CurrenciesPreferenceRepositoryImpl> { CurrenciesPreferenceRepositoryImpl(get()) }

    // Ideas
    single<IdeaListRepositoryImpl> { IdeaListRepositoryImpl(get(), get(), get(), get(), get(), get()) }
    single<BudgetIdeaCardRepositoryImpl> { BudgetIdeaCardRepositoryImpl(get(), get()) }
    single<ExpenseLimitsCardRepositoryImpl> { ExpenseLimitsCardRepositoryImpl(get()) }
    single<IncomePlanCardRepositoryImpl> { IncomePlanCardRepositoryImpl(get(), get(), get(), get()) }
    single<SavingsCardRepositoryImpl> { SavingsCardRepositoryImpl(get(), get()) }

    // Statistic related
    single<ChartsRepositoryImpl> { ChartsRepositoryImpl(get(), get(), get(), get()) }
    single<NotesRepositoryImpl> { NotesRepositoryImpl(get(), get(), get(), get(), get(), get()) }
}

val databaseModule = module {
    // Dao objects
    single<CurrenciesPreferenceDao> { ExpensesDB.getInstance(androidContext()).currenciesPreferenceDao }
    single<IncomeCategoryDao> { ExpensesDB.getInstance(androidContext()).incomeCategoryDao }
    single<ExpenseItemsDAO> { ExpensesDB.getInstance(androidContext()).expenseItemsDao }
    single<ExpenseCategoryDao> { ExpensesDB.getInstance(androidContext()).categoryDao }
    single<CurrencyDao> { ExpensesDB.getInstance(androidContext()).currencyDao }
    single<SavingsDao> { ExpensesDB.getInstance(androidContext()).savingsDao }
    single<IncomeDao> { ExpensesDB.getInstance(androidContext()).incomeDao }
    single<ExpenseLimitsDao> { ExpensesDB.getInstance(androidContext()).expenseLimitsDao }
    single<IncomePlansDao> { ExpensesDB.getInstance(androidContext()).incomePlansDao }
}

val domainModule = module {
    factory<AddExpensesItemUseCase> { AddExpensesItemUseCase(get()) }
    factory<DeleteExpenseItemUseCase> { DeleteExpenseItemUseCase(get()) }
    factory<EditExpenseItemUseCase> { EditExpenseItemUseCase(get()) }
    factory<GetExpensesListUseCase> { GetExpensesListUseCase(get()) }

    factory<AddCategoryUseCase> { AddCategoryUseCase(get()) }
    factory<DeleteCategoryUseCase> { DeleteCategoryUseCase(get()) }
    factory<EditCategoryUseCase> { EditCategoryUseCase(get()) }
}

val viewModelModule = module {
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModel { BottomSheetViewModel(get(), get(), get(), get(), get()) }
    viewModel { ExpenseAndIncomeLazyColumnViewModel(get(), get(), get(), get(), get()) }
    viewModel { TrackScreenFeedViewModel(get()) }
    viewModel { CurrenciesSettingsViewModel(get(), get(), get(), get(), get()) }
    viewModel { BudgetIdeaCardViewModel(get(), get()) }
    viewModel { StatisticsViewModel(get(), get()) }
    viewModel { TrackScreenInfoCardsViewModel(get(), get(), get()) }
    viewModel { AddToSavingIdeaDialogViewModel(get(), get(), get()) }
}