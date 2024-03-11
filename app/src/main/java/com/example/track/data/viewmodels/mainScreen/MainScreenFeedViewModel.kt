package com.example.track.data.viewmodels.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.constants.BASIC_CARD_COUNT_IN_FEED
import com.example.track.data.converters.convertLocalDateToDate
import com.example.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.example.track.data.models.idea.Idea
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate


class MainScreenFeedViewModel(private val ideaListRepositoryImpl: IdeaListRepositoryImpl) : ViewModel() {
    var ideaList = listOf<Idea>(
        Idea(
            id = 1,
            "first",
            goal = 500,
            currencyTicker = "USD",
            startDate = convertLocalDateToDate(LocalDate.now()),
            endDate = null,
            relatedToAllCategories = null,
            completed = false,
            firstRelatedCategoryId = null,
            secondRelatedCategoryId = null,
            thirdRelatedCategoryId = null,
            currentValue = null
        ),
        Idea(
            id = 2,
            "second",
            goal = 200,
            currencyTicker = "UAH",
            startDate = convertLocalDateToDate(LocalDate.now()),
            endDate = null,
            relatedToAllCategories = null,
            completed = false,
            firstRelatedCategoryId = null,
            secondRelatedCategoryId = null,
            thirdRelatedCategoryId = null,
            currentValue = null
        )
    )
    private val _cardIndex = MutableStateFlow(0)
    val cardIndex = _cardIndex.asStateFlow()

    private val _maxPagerIndex = MutableStateFlow(ideaList.size + BASIC_CARD_COUNT_IN_FEED)
    val maxPagerIndex = _maxPagerIndex.asStateFlow()
    init {
        viewModelScope.launch {
            ideaListRepositoryImpl.getIdeasList().collect {
                ideaList = it
            }
        }
    }
    private fun setCardIndex(index: Int) {
        _cardIndex.update { index }
    }

    fun incrementCardIndex() {
        if (_cardIndex.value != _maxPagerIndex.value) _cardIndex.update { _cardIndex.value + 1 } else setCardIndex(0)
    }
}
