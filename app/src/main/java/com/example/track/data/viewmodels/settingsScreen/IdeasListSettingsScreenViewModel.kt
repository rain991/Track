package com.example.track.data.viewmodels.settingsScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.example.track.domain.models.abstractLayer.Idea
import com.example.track.domain.models.idea.ExpenseLimits
import com.example.track.domain.models.idea.IncomePlans
import com.example.track.domain.models.idea.Savings
import com.example.track.presentation.states.screenRelated.IdeasListSettingsScreenState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IdeasListSettingsScreenViewModel(private val ideaListRepositoryImpl: IdeaListRepositoryImpl) : ViewModel() {
    private val _listOfAllIdeas = mutableStateListOf<Idea>()
    val listOfAllIdeas : List<Idea> = _listOfAllIdeas
    private val _screenState = MutableStateFlow(
        IdeasListSettingsScreenState(listOfSelectedIdeas = _listOfAllIdeas, isSortedDateDescending = true, isShowingCompletedIdeas = true)
    )
    val screenState = _screenState.asStateFlow()
    init {
        viewModelScope.launch {
            initializeValues()
            _screenState.update{_screenState.value.copy(listOfSelectedIdeas = _listOfAllIdeas)}
            sortListDescending()
        }
    }

    suspend fun initializeValues() {
        viewModelScope.launch {
            async{
                ideaListRepositoryImpl.getIncomesPlansList().collect { newIncomePlans ->
                    val currentIncomePlans = _listOfAllIdeas.filterIsInstance<IncomePlans>()
                    _listOfAllIdeas.removeAll(currentIncomePlans)
                    _listOfAllIdeas.addAll(newIncomePlans)
                }
            }
            async{
                ideaListRepositoryImpl.getSavingsList().collect { newSavings ->
                    val currentSavings = _listOfAllIdeas.filterIsInstance<Savings>()
                    _listOfAllIdeas.removeAll(currentSavings)
                    _listOfAllIdeas.addAll(newSavings)
                }
            }
            async{
                ideaListRepositoryImpl.getExpenseLimitsList().collect { newExpenseLimits ->
                    val currentExpenseLimits = _listOfAllIdeas.filterIsInstance<ExpenseLimits>()
                    _listOfAllIdeas.removeAll(currentExpenseLimits)
                    _listOfAllIdeas.addAll(newExpenseLimits)
                }
            }
        }
    }

    fun setIsSortedDateDescending(value: Boolean) {
        if (value) {
            sortListDescending()
        } else {
            sortListAscending()
        }
    }
    suspend fun getCompletionValue(idea: Idea): StateFlow<Float> {
        return ideaListRepositoryImpl.getCompletionValue(idea).stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = 0.0f)
    }

    fun setIsShowingCompletedIdeas(value: Boolean) {
        if (value) {
            addCompletedIdeas()
        } else {
            removeCompletedIdeas()
        }
    }

    private fun sortListDescending() {
        val newIdeasList = _screenState.value.listOfSelectedIdeas.sortedByDescending { it.startDate }
        _screenState.value = _screenState.value.copy(listOfSelectedIdeas = newIdeasList, isSortedDateDescending = true)
    }

    private fun sortListAscending() {
        val newIdeasList = _screenState.value.listOfSelectedIdeas.sortedBy { it.startDate }
        _screenState.value = _screenState.value.copy(listOfSelectedIdeas = newIdeasList, isSortedDateDescending = false)
    }

    private fun removeCompletedIdeas() {
        val newIdeasList = _screenState.value.listOfSelectedIdeas.filter { idea: Idea -> !idea.completed }
        _screenState.value = _screenState.value.copy(listOfSelectedIdeas = newIdeasList, isShowingCompletedIdeas = false)
    }

    private fun addCompletedIdeas() {
        val newIdeasList: List<Idea> = _listOfAllIdeas
        _screenState.value = _screenState.value.copy(listOfSelectedIdeas = newIdeasList, isSortedDateDescending = true)
    }
}