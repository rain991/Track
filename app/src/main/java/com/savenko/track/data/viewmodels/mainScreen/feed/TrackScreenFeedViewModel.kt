package com.savenko.track.data.viewmodels.mainScreen.feed

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.models.abstractLayer.createCompletedInstance
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaItemRepository
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaListRepository
import com.savenko.track.domain.usecases.userData.ideas.GetUnfinishedIdeasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TrackScreenFeedViewModel(
    private val getUnfinishedIdeasUseCase: GetUnfinishedIdeasUseCase,
    private val ideaListRepositoryImpl: IdeaListRepository,
    private val ideaItemRepositoryImpl: IdeaItemRepository
) : ViewModel() {
    private val _ideaList = mutableStateListOf<Idea>()
    val ideaList: List<Idea> = _ideaList
    private val _cardIndex = MutableStateFlow(0)
    val cardIndex = _cardIndex.asStateFlow()
    private val _maxPagerIndex = MutableStateFlow(ideaList.size + 1)
    val maxPagerIndex = _maxPagerIndex.asStateFlow()

    init {
        viewModelScope.launch {
            getUnfinishedIdeasUseCase().collect { allIdeas ->
                _ideaList.clear()
                _ideaList.addAll(allIdeas)
                checkListOfIdeasCompletionState()
                setMaxPagerIndex(_ideaList.size + 1)
            }
        }
    }

    suspend fun checkListOfIdeasCompletionState() {
        val ideasToUpdate = mutableListOf<Idea>()
        _ideaList.forEach { currentIdea ->
            if (!currentIdea.completed) {
                val completionValue = getIdeaCompletionValue(currentIdea)
                if (completionValue >= currentIdea.goal) {
                    ideasToUpdate.add(currentIdea)
                }
            }
        }
        _ideaList.removeAll(ideasToUpdate.toSet())
        ideasToUpdate.forEach { idea ->
            ideaItemRepositoryImpl.updateIdea(idea.createCompletedInstance())
        }
    }


    suspend fun getCompletionValue(idea: Idea): StateFlow<Float> {
        return ideaListRepositoryImpl.getIdeaCompletedValue(idea)
            .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = 0.0f)
    }

    private suspend fun getIdeaCompletionValue(idea: Idea): Float {
        return ideaListRepositoryImpl.getIdeaCompletedValue(idea).first()
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
