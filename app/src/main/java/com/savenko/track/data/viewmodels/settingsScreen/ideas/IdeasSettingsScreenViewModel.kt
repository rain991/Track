package com.savenko.track.data.viewmodels.settingsScreen.ideas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.usecases.userData.ideas.GetIdeaCompletedValueUseCase
import com.savenko.track.domain.usecases.userData.ideas.GetIdeasListUseCase
import com.savenko.track.presentation.screens.states.additional.settings.ideasSettings.IdeasSettingsScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * IdeasSettingsScreenViewModel provides list of Ideas with specific filters
 * Filters are handled via [screenState] and related functions
 */
class IdeasSettingsScreenViewModel(
    private val getIdeasListUseCase: GetIdeasListUseCase,
    private val getIdeaCompletedValueUseCase: GetIdeaCompletedValueUseCase,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository
) : ViewModel() {
    private val listOfAllIdeas = mutableListOf<Idea>()
    private val filteredAndSortedIdeas: MutableList<Idea> = mutableListOf()

    private val _screenState = MutableStateFlow(
        IdeasSettingsScreenState(
            listOfSelectedIdeas = filteredAndSortedIdeas,
            isSortedDateDescending = true,
            isShowingCompletedIdeas = true,
            preferableCurrency = CURRENCY_DEFAULT
        )
    )
    val screenState = _screenState.asStateFlow()


    init {
        viewModelScope.launch {
            initializeValues()
        }
    }

    suspend fun getIdeasCompletedValue(idea: Idea): Flow<Float> {
        return getIdeaCompletedValueUseCase(idea)
    }

    fun setIsSortedDateDescending(value: Boolean) {
        _screenState.value = _screenState.value.copy(isSortedDateDescending = value)
        updateFilteredAndSortedIdeas()
    }

    fun setIsShowingCompletedIdeas(value: Boolean) {
        _screenState.value = _screenState.value.copy(isShowingCompletedIdeas = value)
        updateFilteredAndSortedIdeas()
    }

    private suspend fun initializeValues() {
        viewModelScope.launch {
            launch {
                getIdeasListUseCase(ideaTypes = GetIdeasListUseCase.IdeasTypes.IncomePlans).collect { newIncomePlans ->
                    val currentIncomePlans = listOfAllIdeas.filterIsInstance<IncomePlans>().toSet()
                    listOfAllIdeas.removeAll(currentIncomePlans)
                    listOfAllIdeas.addAll(newIncomePlans)
                    updateFilteredAndSortedIdeas()
                }
            }
            launch {
                getIdeasListUseCase(ideaTypes = GetIdeasListUseCase.IdeasTypes.Savings).collect { newSavings ->
                    val currentSavings = listOfAllIdeas.filterIsInstance<Savings>().toSet()
                    listOfAllIdeas.removeAll(currentSavings)
                    listOfAllIdeas.addAll(newSavings)
                    updateFilteredAndSortedIdeas()
                }
            }
            launch {
                getIdeasListUseCase(ideaTypes = GetIdeasListUseCase.IdeasTypes.ExpenseLimit).collect { newExpenseLimits ->
                    val currentExpenseLimits = listOfAllIdeas.filterIsInstance<ExpenseLimits>().toSet()
                    listOfAllIdeas.removeAll(currentExpenseLimits)
                    listOfAllIdeas.addAll(newExpenseLimits)
                    updateFilteredAndSortedIdeas()
                }
            }
            launch {
                currenciesPreferenceRepositoryImpl.getPreferableCurrency().collect { preferableCurrency ->
                    _screenState.update { _screenState.value.copy(preferableCurrency = preferableCurrency) }
                }
            }
        }
    }

    private fun updateFilteredAndSortedIdeas() {
        val filteredIdeas = if (_screenState.value.isShowingCompletedIdeas) {
            listOfAllIdeas
        } else {
            listOfAllIdeas.filter { !it.completed }
        }

        val sortedIdeas = if (_screenState.value.isSortedDateDescending) {
            filteredIdeas.sortedByDescending { it.startDate }
        } else {
            filteredIdeas.sortedBy { it.startDate }
        }
        filteredAndSortedIdeas.clear()
        filteredAndSortedIdeas.addAll(sortedIdeas)
    }
}
