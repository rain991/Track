package com.savenko.track.data.viewmodels.settingsScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.presentation.states.screenRelated.IdeasListSettingsScreenState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IdeasSettingsScreenViewModel(private val ideaListRepositoryImpl: IdeaListRepositoryImpl) : ViewModel() {
    private val _listOfAllIdeas = mutableStateListOf<Idea>()
    val listOfAllIdeas: List<Idea> = _listOfAllIdeas
    private val _screenState = MutableStateFlow(
        IdeasListSettingsScreenState(listOfSelectedIdeas = _listOfAllIdeas, isSortedDateDescending = true, isShowingCompletedIdeas = true)
    )
    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            initializeValues()
            _screenState.update { _screenState.value.copy(listOfSelectedIdeas = _listOfAllIdeas) }
            sortListDescending()
        }
    }

    suspend fun initializeValues() {
        viewModelScope.launch {
            async {
                ideaListRepositoryImpl.getIncomesPlansList().collect { newIncomePlans ->
                    val currentIncomePlans = _listOfAllIdeas.filterIsInstance<IncomePlans>()
                    _listOfAllIdeas.removeAll(currentIncomePlans)
                    _listOfAllIdeas.addAll(newIncomePlans)
                }
            }
            async {
                ideaListRepositoryImpl.getSavingsList().collect { newSavings ->
                    val currentSavings = _listOfAllIdeas.filterIsInstance<Savings>()
                    _listOfAllIdeas.removeAll(currentSavings)
                    _listOfAllIdeas.addAll(newSavings)
                }
            }
            async {
                ideaListRepositoryImpl.getExpenseLimitsList().collect { newExpenseLimits ->
                    val currentExpenseLimits = _listOfAllIdeas.filterIsInstance<ExpenseLimits>()
                    _listOfAllIdeas.removeAll(currentExpenseLimits)
                    _listOfAllIdeas.addAll(newExpenseLimits)
                }
            }
        }
    }
    suspend fun getCompletionValue(idea: Idea): StateFlow<Float> {
        return ideaListRepositoryImpl.getCompletionValue(idea).stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = 0.0f)
    }
    fun setIsSortedDateDescending(value: Boolean) {
        _screenState.value = _screenState.value.copy(isSortedDateDescending = value)
    }
    fun setIsShowingCompletedIdeas(value: Boolean) {
        _screenState.value = _screenState.value.copy(isShowingCompletedIdeas = value)
    }

     private fun sortListDescending() {
        val newIdeasList = _screenState.value.listOfSelectedIdeas.sortedByDescending { it.startDate }
        _screenState.value = _screenState.value.copy(listOfSelectedIdeas = newIdeasList, isSortedDateDescending = true)
    }
}