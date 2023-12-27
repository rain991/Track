package com.example.expensetracker.data

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDate
import kotlin.random.Random


class LoginViewModel : ViewModel() {
    val FIRSTNAME_INPUT_ID = mutableIntStateOf(102)
    val INCOME_INPUT_ID = mutableIntStateOf(105)

    var currency: Currency? = USD
    var birthday: LocalDate? = null
    var firstName: String? = null
    var income: Int? = null
}
// IF any parameters needed to be passed in viewmodel class, should use viewmodelfactory