package com.example.track.data.viewmodels.mainScreen

import androidx.lifecycle.ViewModel
import com.example.track.domain.models.currency.Currency
import com.example.track.domain.models.idea.Savings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddToSavingIdeaDialogViewModel: ViewModel(){
    private val _currentSavings = MutableStateFlow<Savings?>(null)
    val currentSavings = _currentSavings.asStateFlow()


    fun setCurrentSaving(value : Savings?){
        _currentSavings.value = value
    }
    fun addToSaving(value : Float, currency  : Currency){



    }
}