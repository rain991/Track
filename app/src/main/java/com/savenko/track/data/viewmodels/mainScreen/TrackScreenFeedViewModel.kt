package com.savenko.track.data.viewmodels.mainScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.implementations.ideas.IdeaItemRepositoryImpl
import com.savenko.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.models.abstractLayer.createCompletedInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TrackScreenFeedViewModel(
    private val ideaListRepositoryImpl: IdeaListRepositoryImpl,
    private val ideaItemRepositoryImpl: IdeaItemRepositoryImpl
) : ViewModel() {
    private val _ideaList = mutableStateListOf<Idea>()
    val ideaList: List<Idea> = _ideaList
    private val _cardIndex = MutableStateFlow(0)
    val cardIndex = _cardIndex.asStateFlow()
    private val _maxPagerIndex = MutableStateFlow(ideaList.size + 1)
    val maxPagerIndex = _maxPagerIndex.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                ideaListRepositoryImpl.getIncomesPlansList(),
                ideaListRepositoryImpl.getSavingsList(),
                ideaListRepositoryImpl.getExpenseLimitsList()
            ) { incomePlans, savings, expenseLimits ->
                val allIdeas = mutableListOf<Idea>()
                allIdeas.addAll(incomePlans.filter { !it.completed })
                allIdeas.addAll(savings.filter { !it.completed })
                allIdeas.addAll(expenseLimits.filter { !it.completed })
                allIdeas
            }.collect { allIdeas ->
                _ideaList.clear()
                _ideaList.addAll(allIdeas)
                checkListOfIdeasCompletitionState()
                setMaxPagerIndex(_ideaList.size + 1)
            }
        }
    }

    suspend fun checkListOfIdeasCompletitionState() {
        val ideasToUpdate = mutableListOf<Idea>()
        _ideaList.forEach { currentIdea ->
            if (!currentIdea.completed) {
                val completionValue = getIdeaCompletionValue(currentIdea)
                if (completionValue >= currentIdea.goal) {
                    ideasToUpdate.add(currentIdea)
                }
            }
        }

        _ideaList.removeAll(ideasToUpdate)
        ideasToUpdate.forEach { idea ->
            ideaItemRepositoryImpl.updateIdea(idea.createCompletedInstance())
        }
    }


    suspend fun getCompletionValue(idea: Idea): StateFlow<Float> {
        return ideaListRepositoryImpl.getCompletionValue(idea).stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = 0.0f)
    }

    private suspend fun getIdeaCompletionValue(idea: Idea): Float {
        return ideaListRepositoryImpl.getCompletionValue(idea).first()
    }

    fun incrementCardIndex() {
        if (_cardIndex.value < ideaList.size + 1) {
            setCardIndex(_cardIndex.value + 1)
        } else setCardIndex(0)
    }

    private fun setMaxPagerIndex(value: Int) {
        _maxPagerIndex.value = value
    }

    fun setCardIndex(index: Int) {
        _cardIndex.value = index
    }
}
