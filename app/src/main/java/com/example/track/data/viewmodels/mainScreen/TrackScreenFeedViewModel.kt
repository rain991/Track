package com.example.track.data.viewmodels.mainScreen

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.example.track.domain.models.abstractLayer.Idea
import com.example.track.domain.models.abstractLayer.createCompletedInstance
import com.example.track.domain.models.idea.ExpenseLimits
import com.example.track.domain.models.idea.IncomePlans
import com.example.track.domain.models.idea.Savings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class TrackScreenFeedViewModel(private val ideaListRepositoryImpl: IdeaListRepositoryImpl) : ViewModel() {
    private val _ideaList = mutableStateListOf<Idea>()
    val ideaList: List<Idea> = _ideaList

    private val _cardIndex = MutableStateFlow(0)
    val cardIndex = _cardIndex.asStateFlow()
    private val _maxPagerIndex = MutableStateFlow(ideaList.size + 1)
    val maxPagerIndex = _maxPagerIndex.asStateFlow()
    init {
        viewModelScope.launch {
            ideaListRepositoryImpl.getIncomesPlansList().collect { newIncomePlans ->
                val currentIncomePlans = ideaList.filterIsInstance<IncomePlans>()
                _ideaList.removeAll(currentIncomePlans)
                _ideaList.addAll(newIncomePlans.filter { !it.completed })
                checkListOfIdeasCompletitionState(_ideaList)
                setMaxPagerIndex(ideaList.size + 1)
            }
        }
        viewModelScope.launch {
            ideaListRepositoryImpl.getSavingsList().collect { newSavings ->
                val currentSavings = ideaList.filterIsInstance<Savings>()
                _ideaList.removeAll(currentSavings)
                _ideaList.addAll(newSavings.filter { !it.completed })
                checkListOfIdeasCompletitionState(_ideaList)
                setMaxPagerIndex(ideaList.size + 1)
            }
        }
        viewModelScope.launch {
            ideaListRepositoryImpl.getExpenseLimitsList().collect { newExpenseLimits ->
                val currentExpenseLimits = ideaList.filterIsInstance<ExpenseLimits>()
                _ideaList.removeAll(currentExpenseLimits)
                _ideaList.addAll(newExpenseLimits.filter { !it.completed })
                checkListOfIdeasCompletitionState(_ideaList)
                setMaxPagerIndex(ideaList.size + 1)
            }
        }
    }


    suspend fun checkListOfIdeasCompletitionState(ideasList: List<Idea>) {
        ideasList.forEach { currentIdea ->
            if (!currentIdea.completed) {
                val completionValue = getCompletionValue(currentIdea)
                if (completionValue.value >= currentIdea.goal) {
                    val modifiedIdea = currentIdea.createCompletedInstance()
                    Log.d("Mylog", "checkListOfIdeasCompletitionState: currentIdea : $currentIdea")
                    Log.d("Mylog", "checkListOfIdeasCompletitionState: modified idea : $modifiedIdea")
                    ideaListRepositoryImpl.updateIdea(modifiedIdea)
                    _ideaList.remove(currentIdea)
                }
            } else {
                _ideaList.remove(currentIdea)
            }
        }
    }

    suspend fun getCompletionValue(idea: Idea): StateFlow<Float> {
        return ideaListRepositoryImpl.getCompletionValue(idea).stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = 0.0f)
    }
    fun incrementCardIndex() {
        if (_cardIndex.value < ideaList.size + 1) _cardIndex.update { _cardIndex.value + 1 } else setCardIndex(0)
    }

    private fun setMaxPagerIndex(value: Int) {
        _maxPagerIndex.value = value
    }

    private fun setCardIndex(index: Int) {
        _cardIndex.value = index
    }
}
