package com.savenko.track

import com.savenko.track.data.core.ChartDataProvider
import com.savenko.track.data.core.CurrenciesRatesHandler
import com.savenko.track.data.core.FinancialCardNotesProvider
import com.savenko.track.data.core.PersonalStatsProvider
import com.savenko.track.data.database.currenciesRelated.CurrenciesPreferenceDao
import com.savenko.track.data.database.currenciesRelated.CurrencyDao
import com.savenko.track.data.database.db.ExpensesDB
import com.savenko.track.data.database.expensesRelated.ExpenseCategoryDao
import com.savenko.track.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.data.database.ideaRelated.ExpenseLimitsDao
import com.savenko.track.data.database.ideaRelated.IncomePlansDao
import com.savenko.track.data.database.ideaRelated.SavingsDao
import com.savenko.track.data.database.incomeRelated.IncomeCategoryDao
import com.savenko.track.data.database.incomeRelated.IncomeDao
import com.savenko.track.data.implementations.charts.ChartsRepositoryImpl
import com.savenko.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.data.implementations.currencies.CurrencyListRepositoryImpl
import com.savenko.track.data.implementations.expenses.ExpenseItemRepositoryImpl
import com.savenko.track.data.implementations.expenses.ExpensesCoreRepositoryImpl
import com.savenko.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.savenko.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.ideas.BudgetIdeaCardRepositoryImpl
import com.savenko.track.data.implementations.ideas.IdeaItemRepositoryImpl
import com.savenko.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.savenko.track.data.implementations.incomes.IncomeCoreRepositoryImpl
import com.savenko.track.data.implementations.incomes.IncomeItemRepositoryImpl
import com.savenko.track.data.implementations.incomes.IncomeListRepositoryImpl
import com.savenko.track.data.implementations.incomes.categories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.notes.NotesRepositoryImpl
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.data.other.workers.CurrenciesRatesWorker
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.data.viewmodels.common.TrackScreenManagerViewModel
import com.savenko.track.data.viewmodels.login.LoginViewModel
import com.savenko.track.data.viewmodels.mainScreen.AddToSavingIdeaDialogViewModel
import com.savenko.track.data.viewmodels.mainScreen.BudgetIdeaCardViewModel
import com.savenko.track.data.viewmodels.mainScreen.ExpenseAndIncomeLazyColumnViewModel
import com.savenko.track.data.viewmodels.mainScreen.NewIdeaDialogViewModel
import com.savenko.track.data.viewmodels.mainScreen.TrackScreenFeedViewModel
import com.savenko.track.data.viewmodels.mainScreen.TrackScreenInfoCardsViewModel
import com.savenko.track.data.viewmodels.settingsScreen.CategoriesSettingsScreenViewModel
import com.savenko.track.data.viewmodels.settingsScreen.CurrenciesSettingsViewModel
import com.savenko.track.data.viewmodels.settingsScreen.IdeasSettingsScreenViewModel
import com.savenko.track.data.viewmodels.settingsScreen.NewCategoryViewModel
import com.savenko.track.data.viewmodels.settingsScreen.PersonalSettingsScreenViewmodel
import com.savenko.track.data.viewmodels.settingsScreen.PersonalStatsViewModel
import com.savenko.track.data.viewmodels.settingsScreen.ThemePreferenceSettingsViewModel
import com.savenko.track.data.viewmodels.statistics.StatisticChartViewModel
import com.savenko.track.data.viewmodels.statistics.StatisticLazyColumnViewModel
import com.savenko.track.domain.usecases.crud.categoriesRelated.AddCategoryUseCase
import com.savenko.track.domain.usecases.crud.categoriesRelated.DeleteCategoryUseCase
import com.savenko.track.domain.usecases.crud.expenseRelated.AddExpenseItemUseCase
import com.savenko.track.domain.usecases.crud.ideasRelated.CreateIdeaUseCase
import com.savenko.track.domain.usecases.crud.incomeRelated.AddIncomeItemUseCase
import com.savenko.track.domain.usecases.crud.userRelated.UpdateUserDataUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified.GetUserExpensesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified.GetUserIncomesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredExpensesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredFinancialEntitiesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredIncomesUseCase
import com.savenko.track.domain.usecases.userData.ideas.specified.GetUnfinishedIdeasUseCase
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
    single<ChartDataProvider> { ChartDataProvider(get(), get(), get(), get()) }
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
    single<IdeaItemRepositoryImpl> { IdeaItemRepositoryImpl(get(), get(), get()) }
    single<IdeaListRepositoryImpl> {
        IdeaListRepositoryImpl(get(), get(), get(), get(), get(), get())
    }
    single<BudgetIdeaCardRepositoryImpl> { BudgetIdeaCardRepositoryImpl(get(), get()) }

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
    // CRUD
    factory<UpdateUserDataUseCase> { UpdateUserDataUseCase(get()) }
    factory<AddExpenseItemUseCase> { AddExpenseItemUseCase(get()) }
    factory<AddIncomeItemUseCase> { AddIncomeItemUseCase(get()) }
    factory<AddCategoryUseCase> { AddCategoryUseCase(get(), get()) }
    factory<DeleteCategoryUseCase> { DeleteCategoryUseCase(get(), get()) }
    factory<CreateIdeaUseCase> { CreateIdeaUseCase(get()) }

    // User Data
    factory<GetUserExpensesUseCase> { GetUserExpensesUseCase(get()) }
    factory<GetUserIncomesUseCase> { GetUserIncomesUseCase(get()) }
    factory<GetDesiredIncomesUseCase> { GetDesiredIncomesUseCase(get()) }
    factory<GetDesiredExpensesUseCase> { GetDesiredExpensesUseCase(get()) }
    factory<GetDesiredFinancialEntitiesUseCase> { GetDesiredFinancialEntitiesUseCase(get(), get()) }
    factory<GetUnfinishedIdeasUseCase> { GetUnfinishedIdeasUseCase(get()) }
}

val viewModelModule = module {
    // Login related
    viewModel { LoginViewModel(get(), get(), get(), get()) }

    // Settings related
    viewModel { CurrenciesSettingsViewModel(get(), get(), get(), get(), get()) }
    viewModel { IdeasSettingsScreenViewModel(get()) }
    viewModel { ThemePreferenceSettingsViewModel(get(), get()) }
    viewModel { CategoriesSettingsScreenViewModel(get(), get(), get()) }
    viewModel { NewCategoryViewModel(get()) }
    viewModel { PersonalSettingsScreenViewmodel(get(), get(), get()) }
    viewModel { PersonalStatsViewModel(get(), get()) }

    // Track main screen related
    viewModel { ExpenseAndIncomeLazyColumnViewModel(get(), get(), get(), get(), get()) }

    // Statistics related
    viewModel { StatisticChartViewModel(get(), get()) }
    viewModel { StatisticLazyColumnViewModel(get(), get(), get(), get(), get(), get()) }

    // Bottom sheets
    viewModel { BottomSheetViewModel(get(), get(), get(), get(), get()) }

    // Feed related
    viewModel { TrackScreenFeedViewModel(get(), get(), get()) }
    viewModel { TrackScreenInfoCardsViewModel(get(), get(), get()) }

    // Ideas related
    viewModel { BudgetIdeaCardViewModel(get(), get()) }

    // Dialogs related
    viewModel { AddToSavingIdeaDialogViewModel(get(), get(), get()) }
    viewModel { NewIdeaDialogViewModel(get()) }

    // Other
    viewModel { TrackScreenManagerViewModel() }
}