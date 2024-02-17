package com.example.expensetracker.data.viewmodels.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.constants.BASIC_CARD_COUNT_IN_FEED
import com.example.expensetracker.data.constants.FEED_CARD_DELAY_FAST
import com.example.expensetracker.data.constants.FEED_CARD_DELAY_SLOW
import com.example.expensetracker.data.implementations.IdeaListRepositoryImpl
import com.example.expensetracker.data.models.idea.Idea
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


class MainScreenFeedViewModel(private val ideaListRepositoryImpl: IdeaListRepositoryImpl) : ViewModel() {
    var ideaList = listOf<Idea>()

    private val _cardIndex = MutableStateFlow(0)
    val cardIndex = _cardIndex.asStateFlow()

    private val _maxPagerIndex = MutableStateFlow(ideaList.lastIndex + BASIC_CARD_COUNT_IN_FEED)
    val maxPagerIndex = _maxPagerIndex.asStateFlow()
    init {
        viewModelScope.launch {
            ideaListRepositoryImpl.getIdeasList().collect{
                ideaList = it
            }
        }
    }

    private suspend fun callNextCard(dispatcher : CoroutineContext = Dispatchers.Main){
        withContext(dispatcher){
            delay(if (_cardIndex.value==0 || _cardIndex==_maxPagerIndex) FEED_CARD_DELAY_SLOW else FEED_CARD_DELAY_FAST)
            if(_cardIndex.value!=_maxPagerIndex.value) incrementCardIndex() else setCardIndex(0)
        }
    }
    private fun setCardIndex(index : Int){
        _cardIndex.update { index }
    }

    private fun incrementCardIndex(){
        _cardIndex.update { _cardIndex.value+1 }
    }
}