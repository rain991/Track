package com.example.expensetracker.data.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.models.User
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


// NULL LOGIC SHOULD BE WROTE
class UserDataViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    val currentUser = User()

    init {
        viewModelScope.launch {
            val defferedLoginCount = viewModelScope.async { dataStoreManager.loginCountFlow.firstOrNull() }
            val defferedUserName = viewModelScope.async { dataStoreManager.nameFlow.firstOrNull() }
            val defferedBudget = viewModelScope.async { dataStoreManager.budgetFlow.firstOrNull() }
            val defferedCurrency = viewModelScope.async { dataStoreManager.currencyFlow.firstOrNull() }
                // viewModelScope.launch {dataStoreManager.budgetFlow.collect{it ->  currentUser.budget=it} }
            val loginCounter = defferedLoginCount.await()
            val userName = defferedUserName.await()
            val budget = defferedBudget.await()
            val currency = defferedCurrency.await()
            val needsLogin = loginCounter==0
            if (loginCounter!=null && loginCounter > -1 ){
                currentUser.needsLogin = needsLogin
                currentUser.loginCount = loginCounter

                if( userName != null && budget != null && currency != null){
                    currentUser.budget=budget
                    currentUser.currency=currency
                    currentUser.username=userName
                }

            }
        }
    }
}