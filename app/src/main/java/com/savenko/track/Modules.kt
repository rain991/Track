package com.savenko.track

import com.savenko.track.data.core.ChartDataProvider
import com.savenko.track.data.core.CurrenciesRatesHandler
import com.savenko.track.data.core.FinancialCardNotesProvider
import com.savenko.track.data.core.PersonalStatsProvider
import com.savenko.track.data.core.WorkManagerHelper
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
import com.savenko.track.data.implementations.expenses.expenseCategories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.expenses.expenseItem.ExpenseItemRepositoryImpl
import com.savenko.track.data.implementations.expenses.expenseItem.ExpensesCoreRepositoryImpl
import com.savenko.track.data.implementations.expenses.expenseItem.ExpensesListRepositoryImpl
import com.savenko.track.data.implementations.ideas.BudgetIdeaCardRepositoryImpl
import com.savenko.track.data.implementations.ideas.IdeaItemRepositoryImpl
import com.savenko.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeCategories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeItem.IncomeCoreRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeItem.IncomeItemRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeItem.IncomeListRepositoryImpl
import com.savenko.track.data.implementations.notes.NotesRepositoryImpl
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.data.other.workers.CurrenciesRatesWorker
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.data.viewmodels.common.TrackScreenManagerViewModel
import com.savenko.track.data.viewmodels.login.LoginViewModel
import com.savenko.track.data.viewmodels.mainScreen.feed.AddToSavingIdeaDialogViewModel
import com.savenko.track.data.viewmodels.mainScreen.feed.BudgetIdeaCardViewModel
import com.savenko.track.data.viewmodels.mainScreen.feed.NewIdeaDialogViewModel
import com.savenko.track.data.viewmodels.mainScreen.feed.TrackScreenFeedViewModel
import com.savenko.track.data.viewmodels.mainScreen.feedCards.TrackScreenInfoCardsViewModel
import com.savenko.track.data.viewmodels.mainScreen.lazyColumn.FinancialsLazyColumnViewModel
import com.savenko.track.data.viewmodels.settingsScreen.additionalPreferences.AdditionalPreferencesSettingsViewModel
import com.savenko.track.data.viewmodels.settingsScreen.category.CategoriesSettingsScreenViewModel
import com.savenko.track.data.viewmodels.settingsScreen.category.NewCategoryViewModel
import com.savenko.track.data.viewmodels.settingsScreen.currencies.CurrenciesSettingsViewModel
import com.savenko.track.data.viewmodels.settingsScreen.ideas.IdeasSettingsScreenViewModel
import com.savenko.track.data.viewmodels.settingsScreen.personal.PersonalSettingsScreenViewmodel
import com.savenko.track.data.viewmodels.settingsScreen.personal.PersonalStatsViewModel
import com.savenko.track.data.viewmodels.settingsScreen.themePreferences.ThemePreferenceSettingsViewModel
import com.savenko.track.data.viewmodels.statistics.StatisticChartViewModel
import com.savenko.track.data.viewmodels.statistics.StatisticLazyColumnViewModel
import com.savenko.track.domain.repository.charts.ChartsRepository
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.currencies.CurrencyListRepository
import com.savenko.track.domain.repository.expenses.ExpenseItemRepository
import com.savenko.track.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaItemRepository
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaListRepository
import com.savenko.track.domain.repository.ideas.uiProviders.BudgetIdeaCardRepository
import com.savenko.track.domain.repository.incomes.IncomeCoreRepository
import com.savenko.track.domain.repository.incomes.IncomeItemRepository
import com.savenko.track.domain.repository.incomes.IncomeListRepository
import com.savenko.track.domain.repository.incomes.categories.IncomesCategoriesListRepository
import com.savenko.track.domain.repository.notes.NotesRepository
import com.savenko.track.domain.usecases.crud.categoriesRelated.CreateCategoryUseCase
import com.savenko.track.domain.usecases.crud.categoriesRelated.DeleteCategoryUseCase
import com.savenko.track.domain.usecases.crud.expenseRelated.AddExpenseItemUseCase
import com.savenko.track.domain.usecases.crud.financials.DeleteFinancialItemUseCase
import com.savenko.track.domain.usecases.crud.ideasRelated.CreateIdeaUseCase
import com.savenko.track.domain.usecases.crud.incomeRelated.AddIncomeItemUseCase
import com.savenko.track.domain.usecases.crud.userRelated.UpdateUserDataUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified.GetUserExpensesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified.GetUserIncomesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredExpensesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredFinancialEntitiesUseCase
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetDesiredIncomesUseCase
import com.savenko.track.domain.usecases.userData.ideas.specified.GetUnfinishedIdeasUseCase
import com.savenko.track.domain.usecases.userData.other.ChangePreferableCurrencyUseCase
import com.savenko.track.presentation.UiText.DatabaseStringResourcesProvider
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
    single<WorkManagerHelper> { WorkManagerHelper(get(), get()) }
    single<DatabaseStringResourcesProvider> { DatabaseStringResourcesProvider(get()) }
}

val appModule = module {
    // Expense related
    single<ExpenseItemRepository> { ExpenseItemRepositoryImpl(get()) }
    single<ExpensesCoreRepository> { ExpensesCoreRepositoryImpl(get(), get(), get()) }
    single<ExpensesListRepository> { ExpensesListRepositoryImpl(get()) }
    single<ExpensesCategoriesListRepository> { ExpensesCategoriesListRepositoryImpl(get()) }

    // Income related
    single<IncomeListRepository> { IncomeListRepositoryImpl(get()) }
    single<IncomesCategoriesListRepository> { IncomesCategoriesListRepositoryImpl(get()) }
    single<IncomeItemRepository> { IncomeItemRepositoryImpl(get()) }
    single<IncomeCoreRepository> { IncomeCoreRepositoryImpl(get(), get(), get()) }

    // Currencies
    single<CurrencyListRepository> { CurrencyListRepositoryImpl(get()) }
    single<CurrenciesPreferenceRepository> { CurrenciesPreferenceRepositoryImpl(get()) }

    // Ideas
    single<IdeaItemRepository> { IdeaItemRepositoryImpl(get(), get(), get()) }
    single<IdeaListRepository> { IdeaListRepositoryImpl(get(), get(), get(), get(), get(), get()) }
    single<BudgetIdeaCardRepository> { BudgetIdeaCardRepositoryImpl(get(), get()) }

    // Statistic related
    single<ChartsRepository> { ChartsRepositoryImpl(get(), get(), get(), get()) }
    single<NotesRepository> { NotesRepositoryImpl(get(), get(), get(), get(), get(), get()) }
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
    factory<CreateCategoryUseCase> { CreateCategoryUseCase(get(), get()) }
    factory<CreateIdeaUseCase> { CreateIdeaUseCase(get()) }
    factory<DeleteCategoryUseCase> { DeleteCategoryUseCase(get(), get()) }
    factory<DeleteFinancialItemUseCase> { DeleteFinancialItemUseCase(get(), get()) }

    // User Data
    factory<GetUserExpensesUseCase> { GetUserExpensesUseCase(get()) }
    factory<GetUserIncomesUseCase> { GetUserIncomesUseCase(get()) }
    factory<GetDesiredIncomesUseCase> { GetDesiredIncomesUseCase(get()) }
    factory<GetDesiredExpensesUseCase> { GetDesiredExpensesUseCase(get()) }
    factory<GetDesiredFinancialEntitiesUseCase> { GetDesiredFinancialEntitiesUseCase(get(), get()) }
    factory<GetUnfinishedIdeasUseCase> { GetUnfinishedIdeasUseCase(get()) }
    factory<ChangePreferableCurrencyUseCase> { ChangePreferableCurrencyUseCase(get(), get(), get(), get()) }
}

val viewModelModule = module {
    // Login related
    viewModel { LoginViewModel(get(), get(), get(), get()) }

    // Settings related
    viewModel { CurrenciesSettingsViewModel(get(), get(), get(), get(), get()) }
    viewModel { IdeasSettingsScreenViewModel(get(), get()) }
    viewModel { ThemePreferenceSettingsViewModel(get(), get()) }
    viewModel { CategoriesSettingsScreenViewModel(get(), get(), get(),get()) }
    viewModel { NewCategoryViewModel(get(), get(), get()) }
    viewModel { PersonalSettingsScreenViewmodel(get(), get(), get()) }
    viewModel { PersonalStatsViewModel(get(), get()) }
    viewModel { AdditionalPreferencesSettingsViewModel(get(), get(), get(), get()) }

    // Track main screen related
    viewModel { FinancialsLazyColumnViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }

    // Statistics related
    viewModel { StatisticChartViewModel(get(), get(), get()) }
    viewModel { StatisticLazyColumnViewModel(get(), get(), get(), get(), get(), get()) }

    // Bottom sheets
    viewModel { BottomSheetViewModel(get(), get(), get(), get(), get(), get()) }

    // Feed related
    viewModel { TrackScreenFeedViewModel(get(), get(), get()) }
    viewModel { TrackScreenInfoCardsViewModel(get(), get(), get()) }

    // Ideas related
    viewModel { BudgetIdeaCardViewModel(get(), get()) }

    // Dialogs related
    viewModel { AddToSavingIdeaDialogViewModel(get(), get(), get()) }
    viewModel { NewIdeaDialogViewModel(get()) }

    // Other
    viewModel { TrackScreenManagerViewModel(get(), get()) }
}