package com.savenko.track.data.viewmodels.settingsScreen.ideas

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.ideas.objectsRepository.IdeaListRepository
import com.savenko.track.presentation.screens.states.additional.settings.ideasSettings.IdeasSettingsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IdeasSettingsScreenViewModel(
    private val ideaListRepositoryImpl: IdeaListRepository,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository
) : ViewModel() {
    private val _listOfAllIdeas = mutableStateListOf<Idea>()
    val listOfAllIdeas: List<Idea> = _listOfAllIdeas

    private val _screenState = MutableStateFlow(
        IdeasSettingsScreenState(
            listOfSelectedIdeas = _listOfAllIdeas,
            isSortedDateDescending = true,
            isShowingCompletedIdeas = true,
            preferableCurrency = CURRENCY_DEFAULT
        )
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
            launch {
                ideaListRepositoryImpl.getIncomesPlansList().collect { newIncomePlans ->
                    val currentIncomePlans = _listOfAllIdeas.filterIsInstance<IncomePlans>()
                    _listOfAllIdeas.removeAll(currentIncomePlans)
                    _listOfAllIdeas.addAll(newIncomePlans)
                }
            }
            launch {
                ideaListRepositoryImpl.getSavingsList().collect { newSavings ->
                    val currentSavings = _listOfAllIdeas.filterIsInstance<Savings>()
                    _listOfAllIdeas.removeAll(currentSavings)
                    _listOfAllIdeas.addAll(newSavings)
                }
            }
            launch {
                ideaListRepositoryImpl.getExpenseLimitsList().collect { newExpenseLimits ->
                    val currentExpenseLimits = _listOfAllIdeas.filterIsInstance<ExpenseLimits>()
                    _listOfAllIdeas.removeAll(currentExpenseLimits)
                    _listOfAllIdeas.addAll(newExpenseLimits)
                }
            }
            launch {
                currenciesPreferenceRepositoryImpl.getPreferableCurrency().collect { preferableCurrency ->
                    _screenState.update { _screenState.value.copy(preferableCurrency = preferableCurrency) }
                }
            }
        }
    }

    suspend fun getCompletionValue(idea: Idea): StateFlow<Float> {
        return ideaListRepositoryImpl.getCompletionValue(idea)
            .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = 0.0f)
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