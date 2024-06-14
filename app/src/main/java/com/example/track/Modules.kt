package com.example.track

import com.example.track.data.core.ChartDataProvider
import com.example.track.data.core.CurrenciesRatesHandler
import com.example.track.data.core.FinancialCardNotesProvider
import com.example.track.data.core.PersonalStatsProvider
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
import com.example.track.data.viewmodels.common.TrackScreenManagerViewModel
import com.example.track.data.viewmodels.login.LoginViewModel
import com.example.track.data.viewmodels.mainScreen.AddToSavingIdeaDialogViewModel
import com.example.track.data.viewmodels.mainScreen.BudgetIdeaCardViewModel
import com.example.track.data.viewmodels.mainScreen.ExpenseAndIncomeLazyColumnViewModel
import com.example.track.data.viewmodels.mainScreen.NewIdeaDialogViewModel
import com.example.track.data.viewmodels.mainScreen.TrackScreenFeedViewModel
import com.example.track.data.viewmodels.mainScreen.TrackScreenInfoCardsViewModel
import com.example.track.data.viewmodels.settingsScreen.CategoriesSettingsScreenViewModel
import com.example.track.data.viewmodels.settingsScreen.CurrenciesSettingsViewModel
import com.example.track.data.viewmodels.settingsScreen.IdeasListSettingsScreenViewModel
import com.example.track.data.viewmodels.settingsScreen.NewCategoryViewModel
import com.example.track.data.viewmodels.settingsScreen.PersonalSettingsScreenViewmodel
import com.example.track.data.viewmodels.settingsScreen.PersonalStatsViewModel
import com.example.track.data.viewmodels.settingsScreen.ThemePreferenceSettingsViewModel
import com.example.track.data.viewmodels.statistics.StatisticChartViewModel
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
    single<FinancialCardNotesProvider> { FinancialCardNotesProvider(get(), get()) }
    single<ChartDataProvider> { ChartDataProvider(get(), get(), get(),get()) }
    single<PersonalStatsProvider> { PersonalStatsProvider(get(), get(), get()) }
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
    // Income related
    single<IncomeDao> { ExpensesDB.getInstance(androidContext()).incomeDao }
    single<IncomePlansDao> { ExpensesDB.getInstance(androidContext()).incomePlansDao }
    single<IncomeCategoryDao> { ExpensesDB.getInstance(androidContext()).incomeCategoryDao }

    // Expense related
    single<ExpenseItemsDAO> { ExpensesDB.getInstance(androidContext()).expenseItemsDao }
    single<ExpenseCategoryDao> { ExpensesDB.getInstance(androidContext()).categoryDao }
    single<ExpenseLimitsDao> { ExpensesDB.getInstance(androidContext()).expenseLimitsDao }

    // Currencies
    single<CurrenciesPreferenceDao> { ExpensesDB.getInstance(androidContext()).currenciesPreferenceDao }
    single<CurrencyDao> { ExpensesDB.getInstance(androidContext()).currencyDao }

    // Savings idea
    single<SavingsDao> { ExpensesDB.getInstance(androidContext()).savingsDao }
}

val domainModule = module {
    /* Use cases will be reworked soon */
    factory<AddExpensesItemUseCase> { AddExpensesItemUseCase(get()) }
    factory<DeleteExpenseItemUseCase> { DeleteExpenseItemUseCase(get()) }
    factory<EditExpenseItemUseCase> { EditExpenseItemUseCase(get()) }
    factory<GetExpensesListUseCase> { GetExpensesListUseCase(get()) }

    factory<AddCategoryUseCase> { AddCategoryUseCase(get()) }
    factory<DeleteCategoryUseCase> { DeleteCategoryUseCase(get()) }
    factory<EditCategoryUseCase> { EditCategoryUseCase(get()) }
}

val viewModelModule = module {
    // Login related
    viewModel { LoginViewModel(get(), get(), get()) }

    // Settings related
    viewModel { CurrenciesSettingsViewModel(get(), get(), get(), get(), get()) }
    viewModel { IdeasListSettingsScreenViewModel(get()) }
    viewModel { ThemePreferenceSettingsViewModel(get()) }
    viewModel { CategoriesSettingsScreenViewModel(get(), get()) }
    viewModel { NewCategoryViewModel(get(), get()) }
    viewModel { PersonalSettingsScreenViewmodel(get(), get()) }
    viewModel { PersonalStatsViewModel(get(), get()) }

    // Track main screen related
    viewModel { ExpenseAndIncomeLazyColumnViewModel(get(), get(), get(), get(), get()) }

    // Statistics related
    viewModel { StatisticsViewModel(get(), get()) }
    viewModel { StatisticChartViewModel(get(), get()) }

    // Bottom sheets
    viewModel { BottomSheetViewModel(get(), get(), get(), get(), get()) }

    // Feed related
    viewModel { TrackScreenFeedViewModel(get()) }
    viewModel { TrackScreenInfoCardsViewModel(get(), get(), get()) }

    // Ideas related
    viewModel { BudgetIdeaCardViewModel(get(), get()) }

    // Dialogs related
    viewModel { AddToSavingIdeaDialogViewModel(get(), get(), get()) }
    viewModel { NewIdeaDialogViewModel(get()) }

    // Other
    viewModel { TrackScreenManagerViewModel() }
}