    package com.example.expensetracker.data.implementations

    import com.example.expensetracker.data.database.ExpensesDAO
    import com.example.expensetracker.data.models.ExpenseItem
    import com.example.expensetracker.domain.repository.ExpensesListRepository
    import kotlinx.coroutines.Dispatchers
    import kotlinx.coroutines.coroutineScope
    import kotlinx.coroutines.withContext
    import java.time.LocalDate
    import kotlin.random.Random

    class ExpensesListRepositoryImpl(private val expensesDao: ExpensesDAO) : ExpensesListRepository {
        private var expensesList = mutableListOf<ExpenseItem>()
        override suspend fun setExpensesList(expensesDAO: ExpensesDAO) {
             expensesList = expensesDAO.getAll()
        }

        override fun sortExpensesItemsDateAsc() {
            expensesList = expensesList.sortedBy { it.date }.toMutableList()
        }

        override fun sortExpensesItemsDateDesc() {
            expensesList = expensesList.sortedByDescending { it.date }.toMutableList()
        }


        override suspend fun addExpensesItem(currentExpensesItem: ExpenseItem) {
            if (expensesList.none { it.id == currentExpensesItem.id }) {
                expensesList.add(currentExpensesItem)
                withContext(Dispatchers.IO) {
                    expensesDao.insertItem(currentExpensesItem)
                }
            } else {
                currentExpensesItem.id++
                expensesList.add(currentExpensesItem)
                withContext(Dispatchers.IO) {
                    expensesDao.insertItem(currentExpensesItem)
                }
            }
        }

        override fun getExpensesList(): MutableList<ExpenseItem> {
            return expensesList // gets a copy of list (changed: now returns original)
        }

        override fun getExpensesItem(expensesItemId: Int): ExpenseItem? {
            if (expensesList.find { it.id == expensesItemId } == null) {
                return null
            } else {
                return expensesList.find { it.id == expensesItemId }!! // WARNING !! call, to be checked afterwards
            }
        }


        override suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem) {
            expensesList.remove(currentExpenseItem)
            coroutineScope { expensesDao.deleteItem(currentExpenseItem) }
        }

        override suspend fun editExpenseItem(newExpenseItem: ExpenseItem) {
            val olderExpense = getExpensesItem(newExpenseItem.id)
            if (olderExpense != null) {
                expensesList.remove(olderExpense)
                addExpensesItem(newExpenseItem)
                coroutineScope {
                    expensesDao.deleteItem(olderExpense)
                    expensesDao.insertItem(newExpenseItem)
                }
            }
        }


        // All random elements will be deleted soon
        fun generateRandomDate(): LocalDate {
            val randomDays = Random.nextLong(180)
            return LocalDate.now().minusDays(randomDays)
        }

        fun generateRandomExpenseObject(): ExpenseItem {
            return ExpenseItem(
                id = Random.nextInt(50000) + 5000,
                name = "",
                date = generateRandomDate().toString(),
                enabled = false,
                value = (Random.nextInt(5000) + 5).toFloat(),
                categoryId = 1// 5-5005
            )
        }
    }