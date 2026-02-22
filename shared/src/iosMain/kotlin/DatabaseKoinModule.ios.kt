package com.savenko.track.shared

import com.savenko.track.shared.data.database.currencies.CurrenciesPreferenceDao
import com.savenko.track.shared.data.database.currencies.CurrencyDao
import com.savenko.track.shared.data.database.db.ExpensesDB
import com.savenko.track.shared.data.database.db.getDatabaseBuilder
import com.savenko.track.shared.data.database.db.getRoomDatabase
import com.savenko.track.shared.data.database.expensesRelated.ExpenseCategoryDao
import com.savenko.track.shared.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.shared.data.database.ideas.ExpenseLimitsDao
import com.savenko.track.shared.data.database.ideas.IncomePlansDao
import com.savenko.track.shared.data.database.ideas.SavingsDao
import com.savenko.track.shared.data.database.incomeRelated.IncomeCategoryDao
import com.savenko.track.shared.data.database.incomeRelated.IncomeDao
import org.koin.core.module.Module
import org.koin.dsl.module

actual val databaseModule: Module = module {
    single<ExpensesDB> { getRoomDatabase(getDatabaseBuilder()) }

    single<IncomeDao> { get<ExpensesDB>().incomeDao }
    single<IncomePlansDao> { get<ExpensesDB>().incomePlansDao }
    single<IncomeCategoryDao> { get<ExpensesDB>().incomeCategoryDao }

    single<ExpenseItemsDAO> { get<ExpensesDB>().expenseItemsDao }
    single<ExpenseCategoryDao> { get<ExpensesDB>().categoryDao }
    single<ExpenseLimitsDao> { get<ExpensesDB>().expenseLimitsDao }

    single<CurrenciesPreferenceDao> { get<ExpensesDB>().currenciesPreferenceDao }
    single<CurrencyDao> { get<ExpensesDB>().currencyDao }

    single<SavingsDao> { get<ExpensesDB>().savingsDao }
}
