package com.savenko.track.shared

import com.savenko.track.shared.data.core.ChartDataProvider
import com.savenko.track.shared.data.core.CurrenciesRatesHandler
import com.savenko.track.shared.data.core.FinancialCardNotesProvider
import com.savenko.track.shared.data.core.PersonalStatsProvider
import com.savenko.track.shared.data.implementations.charts.ChartsRepositoryImpl
import com.savenko.track.shared.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.shared.data.implementations.currencies.CurrencyListRepositoryImpl
import com.savenko.track.shared.data.implementations.expenses.expenseCategories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.shared.data.implementations.expenses.expenseItem.ExpenseItemRepositoryImpl
import com.savenko.track.shared.data.implementations.expenses.expenseItem.ExpensesCoreRepositoryImpl
import com.savenko.track.shared.data.implementations.expenses.expenseItem.ExpensesListRepositoryImpl
import com.savenko.track.shared.data.implementations.ideas.BudgetIdeaCardRepositoryImpl
import com.savenko.track.shared.data.implementations.ideas.IdeaItemRepositoryImpl
import com.savenko.track.shared.data.implementations.ideas.IdeaListRepositoryImpl
import com.savenko.track.shared.data.implementations.incomes.incomeCategories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.shared.data.implementations.incomes.incomeItem.IncomeCoreRepositoryImpl
import com.savenko.track.shared.data.implementations.incomes.incomeItem.IncomeItemRepositoryImpl
import com.savenko.track.shared.data.implementations.incomes.incomeItem.IncomeListRepositoryImpl
import com.savenko.track.shared.domain.repository.charts.ChartsRepository
import com.savenko.track.shared.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.shared.domain.repository.currencies.CurrencyListRepository
import com.savenko.track.shared.domain.repository.expenses.ExpenseItemRepository
import com.savenko.track.shared.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.shared.domain.repository.expenses.ExpensesListRepository
import com.savenko.track.shared.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.shared.domain.repository.ideas.objectsRepository.IdeaItemRepository
import com.savenko.track.shared.domain.repository.ideas.objectsRepository.IdeaListRepository
import com.savenko.track.shared.domain.repository.ideas.uiProviders.BudgetIdeaCardRepository
import com.savenko.track.shared.domain.repository.incomes.IncomeCoreRepository
import com.savenko.track.shared.domain.repository.incomes.IncomeItemRepository
import com.savenko.track.shared.domain.repository.incomes.IncomeListRepository
import com.savenko.track.shared.domain.repository.incomes.categories.IncomesCategoriesListRepository
import com.savenko.track.shared.domain.usecases.crud.categoriesRelated.CreateCategoryUseCase
import com.savenko.track.shared.domain.usecases.crud.categoriesRelated.DeleteCategoryUseCase
import com.savenko.track.shared.domain.usecases.crud.expenseRelated.AddExpenseItemUseCase
import com.savenko.track.shared.domain.usecases.crud.financials.DeleteFinancialItemUseCase
import com.savenko.track.shared.domain.usecases.crud.ideasRelated.CreateIdeaUseCase
import com.savenko.track.shared.domain.usecases.crud.incomeRelated.AddIncomeItemUseCase
import com.savenko.track.shared.domain.usecases.crud.userRelated.UpdateUserDataUseCase
import com.savenko.track.shared.domain.usecases.userData.financialEntities.nonSpecified.GetUserExpensesUseCase
import com.savenko.track.shared.domain.usecases.userData.financialEntities.nonSpecified.GetUserIncomesUseCase
import com.savenko.track.shared.domain.usecases.userData.financialEntities.specified.GetDesiredExpensesUseCase
import com.savenko.track.shared.domain.usecases.userData.financialEntities.specified.GetDesiredFinancialEntitiesUseCase
import com.savenko.track.shared.domain.usecases.userData.financialEntities.specified.GetDesiredIncomesUseCase
import com.savenko.track.shared.domain.usecases.userData.financialEntities.specified.GetPeriodSummaryUseCase
import com.savenko.track.shared.domain.usecases.userData.ideas.GetIdeaCompletedValueUseCase
import com.savenko.track.shared.domain.usecases.userData.ideas.GetIdeasListUseCase
import com.savenko.track.shared.domain.usecases.userData.ideas.GetUnfinishedIdeasUseCase
import com.savenko.track.shared.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCase
import com.savenko.track.shared.presentation.other.uiText.DatabaseStringResourcesProvider
import org.koin.dsl.module

val coreModule = module {
    single<CurrenciesRatesHandler> { CurrenciesRatesHandler(get(), get()) }
    single<FinancialCardNotesProvider> { FinancialCardNotesProvider(get(), get()) }
    single<ChartDataProvider> { ChartDataProvider(get(), get(), get(), get()) }
    single<PersonalStatsProvider> { PersonalStatsProvider(get(), get(), get()) }
    single<DatabaseStringResourcesProvider> { DatabaseStringResourcesProvider() }
}

val appModule = module {
    // Expense related
    single<ExpenseItemRepository> { ExpenseItemRepositoryImpl(get()) }
    single<ExpensesCoreRepository> { ExpensesCoreRepositoryImpl(get(), get(), get()) }
    single<ExpensesListRepository> { ExpensesListRepositoryImpl(get()) }
    single<ExpensesCategoriesListRepository> { ExpensesCategoriesListRepositoryImpl(get()) }

    // Income related
    single<IncomeItemRepository> { IncomeItemRepositoryImpl(get()) }
    single<IncomeCoreRepository> { IncomeCoreRepositoryImpl(get(), get(), get()) }
    single<IncomeListRepository> { IncomeListRepositoryImpl(get()) }
    single<IncomesCategoriesListRepository> { IncomesCategoriesListRepositoryImpl(get()) }

    // Currencies
    single<CurrencyListRepository> { CurrencyListRepositoryImpl(get()) }
    single<CurrenciesPreferenceRepository> { CurrenciesPreferenceRepositoryImpl(get(), get()) }

    // Ideas
    single<IdeaItemRepository> { IdeaItemRepositoryImpl(get(), get(), get()) }
    single<IdeaListRepository> { IdeaListRepositoryImpl(get(), get(), get(), get(), get(), get()) }
    single<BudgetIdeaCardRepository> { BudgetIdeaCardRepositoryImpl(get(), get()) }

    // Statistic related
    single<ChartsRepository> { ChartsRepositoryImpl(get(), get(), get(), get()) }
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
    factory<GetIdeasListUseCase> { GetIdeasListUseCase(get()) }
    factory<GetIdeaCompletedValueUseCase> { GetIdeaCompletedValueUseCase(get()) }
    factory<ChangeCurrenciesPreferenceUseCase> { ChangeCurrenciesPreferenceUseCase(get(), get(), get(), get()) }
    factory<GetPeriodSummaryUseCase> { GetPeriodSummaryUseCase(get(), get()) }
}
