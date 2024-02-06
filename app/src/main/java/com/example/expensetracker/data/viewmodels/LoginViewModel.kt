package com.example.expensetracker.data.viewmodels

import androidx.lifecycle.ViewModel
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.constants.BUDGET_DEFAULT
import com.example.expensetracker.data.constants.CURRENCY_DEFAULT
import com.example.expensetracker.data.constants.NAME_DEFAULT
import com.example.expensetracker.data.models.USD
import com.example.expensetracker.data.models.findCurrencyByTicker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext


class LoginViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {
    private var _currencyStateFlow = MutableStateFlow(CURRENCY_DEFAULT)
    val currencyStateFlow = _currencyStateFlow.asStateFlow()

    private var _firstNameStateFlow = MutableStateFlow(NAME_DEFAULT)
    val firstNameStateFlow = _firstNameStateFlow.asStateFlow()

    private var _incomeStateFlow = MutableStateFlow(BUDGET_DEFAULT)
    val incomeStateFlow = _incomeStateFlow.asStateFlow()

    suspend fun addToDataStore(dispatcher : CoroutineDispatcher = Dispatchers.IO) {
        if (_incomeStateFlow.value != BUDGET_DEFAULT && _firstNameStateFlow.value != NAME_DEFAULT){
            withContext(dispatcher){
                dataStoreManager.setBudget(_incomeStateFlow.value)
                dataStoreManager.setName(_firstNameStateFlow.value)
                dataStoreManager.setCurrency(findCurrencyByTicker(_currencyStateFlow.value) ?: USD)
            }

        }
    }

    fun setCurrencyStateFlow(currency: String) {
        _currencyStateFlow.update { currency }
    }

    fun setFirstNameStateFlow(firstName: String) {
        _firstNameStateFlow.update { firstName }
    }

    fun setIncomeStateFlow(income: Int) {
        _incomeStateFlow.update { income }
    }


}
