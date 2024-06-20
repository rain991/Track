package com.savenko.track.data.implementations.incomes

import com.savenko.track.data.database.incomeRelated.IncomeDao
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.incomes.IncomeItemRepository
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class IncomeItemRepositoryImpl(private val incomeDao: IncomeDao) : IncomeItemRepository {
    override suspend fun getIncomesItem(incomeItemId: Int): IncomeItem? {
        return incomeDao.findIncomeById(incomeItemId)
    }

    override suspend fun addIncomeItem(currentIncomeItem: IncomeItem, context: CoroutineContext) {
        withContext(context) {
            incomeDao.insert(currentIncomeItem)
        }
    }

    override suspend fun deleteIncomeItem(currentIncomeItem: IncomeItem, context: CoroutineContext) {
        withContext(context) {
            incomeDao.delete(currentIncomeItem)
        }
    }

    override suspend fun editIncomeItem(newIncomeItem: IncomeItem, context: CoroutineContext) {
        withContext(context) {
            incomeDao.update(newIncomeItem)
        }
    }

}