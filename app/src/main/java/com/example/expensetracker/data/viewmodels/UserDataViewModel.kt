package com.example.expensetracker.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.constants.BUDGET_DEFAULT
import com.example.expensetracker.data.constants.CURRENCY_DEFAULT
import com.example.expensetracker.data.constants.IS_LOGED_IN_DEFAULT
import com.example.expensetracker.data.constants.LOGIN_COUNT_DEFAULT
import com.example.expensetracker.data.constants.NAME_DEFAULT
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

data class User(
    var username: String = NAME_DEFAULT,
    var isLoggedIn: Boolean = IS_LOGED_IN_DEFAULT,
    var loginCount: Int = LOGIN_COUNT_DEFAULT,
    var budget: Int = BUDGET_DEFAULT,
    var currency: String = CURRENCY_DEFAULT
)

// NULL LOGIC SHOULD BE WROTE
class UserDataViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    val currentUser = User()

    init {
        viewModelScope.launch {
            val defferedLoginCount = viewModelScope.async { dataStoreManager.loginCountFlow.firstOrNull() }
            val defferedUserName = viewModelScope.async { dataStoreManager.nameFlow.firstOrNull() }
            val defferedBudget = viewModelScope.async { dataStoreManager.budgetFlow.firstOrNull() }
            val defferedCurrency = viewModelScope.async { dataStoreManager.currencyFlow.firstOrNull() }

            val loginCounter = defferedLoginCount.await()
            val userName = defferedUserName.await()
            val budget = defferedBudget.await()
            val currency = defferedCurrency.await()
            val isLoggedIn = loginCounter==0
            if (loginCounter!=null && loginCounter > 0 && userName != null && budget != null && currency != null){
                currentUser.budget=budget
                currentUser.currency=currency
                currentUser.isLoggedIn = isLoggedIn
                currentUser.loginCount = loginCounter
                currentUser.username=userName
            }
        }
    }
}