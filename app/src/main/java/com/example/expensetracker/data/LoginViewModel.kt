package com.example.expensetracker.data

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate


class LoginViewModel : ViewModel() {
    val CURRENCY_INPUT_ID = mutableIntStateOf(100)
    val BIRTHDAY_INPUT_ID = mutableIntStateOf(101)
    val FIRSTNAME_INPUT_ID = mutableIntStateOf(102)
    val INCOME_INPUT_ID = mutableIntStateOf(103)

    var currency: Currency? = null
    var birthday: LocalDate? = null
    var firstName: String? = null
    var income: Int? = null
}
// states will be here
// also all operator, saving and either functions will be here
// Using ViewModel
// private val viewModelName by viewModels<LoginViewModel>()

// IF any parameters needed to be passed in viewmodel class, so you should use viewmodelfactory