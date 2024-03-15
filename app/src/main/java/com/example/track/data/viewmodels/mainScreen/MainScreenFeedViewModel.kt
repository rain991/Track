package com.example.track.data.viewmodels.mainScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.idea.Idea
import com.example.track.presentation.states.IdeaSelectorTypes
import com.example.track.presentation.states.NewIdeaDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date


class MainScreenFeedViewModel(private val ideaListRepositoryImpl: IdeaListRepositoryImpl) : ViewModel() {
    private val _ideaList = mutableStateListOf<Idea>()
    val ideaList : List<Idea> = _ideaList
    private val _isNewIdeaDialogVisible = MutableStateFlow(false)
    val isNewIdeaDialogVisible = _isNewIdeaDialogVisible.asStateFlow()
    private val _newIdeaDialogState = MutableStateFlow(NewIdeaDialogState(
        goal = 0f,
        typeSelected = IdeaSelectorTypes.Savings,
        isDateDialogVisible = false,
        relatedToAllCategories = null,
        eachMonth = null,
        endDate = null,
        selectedCategory1 = null,
        selectedCategory2 = null,
        selectedCategory3 = null
    ))
    val newIdeaDialogState = _newIdeaDialogState.asStateFlow()
    private val _cardIndex = MutableStateFlow(0)
    val cardIndex = _cardIndex.asStateFlow()
    private val _maxPagerIndex = MutableStateFlow(ideaList.size+1)
    val maxPagerIndex = _maxPagerIndex.asStateFlow()
    init {
        viewModelScope.launch {
//            ideaListRepositoryImpl.getIdeasList().collect {
//                _ideaList.clear()
//                _ideaList.addAll(it)
//            }
        }
    }
    fun addNewIdea(){
      //  ideaListRepositoryImpl.addIdea(Idea())
    }
    fun setIsNewIdeaDialogVisible(value : Boolean){
        _isNewIdeaDialogVisible.value = value
    }
    fun setGoal(value : Float){
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(goal = value)
    }
    fun setTypeSelected(value : IdeaSelectorTypes){
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(typeSelected = value)
    }
    fun setIsDatePickerDialogVisible(value: Boolean){
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(isDateDialogVisible = value)
    }
    fun setSelectedToAllCategories(value: Boolean){
        if(value){
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(relatedToAllCategories = true)
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory1 = null)
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory2 = null)
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory3 = null)
        }else{
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(relatedToAllCategories = false)
        }
    }
    fun setEachMonth(value: Boolean){
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(eachMonth = value)
    }
    fun setEndDate(value: Date?){
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(endDate = value)
    }
    fun setSelectedCategory1(value : ExpenseCategory?){
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory1 = value)
    }
    fun setSelectedCategory2(value : ExpenseCategory?){
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory2 = value)
    }
    fun setSelectedCategory3(value : ExpenseCategory?){
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory3 = value)
    }
    fun incrementCardIndex() {
        if (_cardIndex.value < ideaList.size+2) _cardIndex.update { _cardIndex.value + 1 } else setCardIndex(0)
    }
    private fun setCardIndex(index: Int) {
        _cardIndex.value = index
    }
}
