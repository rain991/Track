package com.example.expensetracker.data.viewmodels.mainScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainScreenFeedViewModel : ViewModel() {
    private val _cardIndex = MutableStateFlow(0)
    val cardIndex = _cardIndex.asStateFlow()

    val maxIndex = 10
//    init {
//        viewModelScope.launch {
//            if()
//        }
//    }

    private fun setCardIndex(index : Int){
        _cardIndex.update { index }
    }
}