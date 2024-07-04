package com.savenko.track.data.viewmodels.mainScreen.feed

import androidx.lifecycle.ViewModel
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.domain.usecases.crud.ideasRelated.CreateIdeaUseCase
import com.savenko.track.presentation.other.composableTypes.IdeaSelectorTypes
import com.savenko.track.presentation.screens.states.core.common.NewIdeaDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.util.Date

class NewIdeaDialogViewModel(
    private val createIdeaUseCase: CreateIdeaUseCase
) : ViewModel() {
    private val _isNewIdeaDialogVisible = MutableStateFlow(false)
    val isNewIdeaDialogVisible = _isNewIdeaDialogVisible.asStateFlow()
    private val _newIdeaDialogState = MutableStateFlow(
        NewIdeaDialogState(
            goal = 0f,
            typeSelected = IdeaSelectorTypes.Savings,
            isDateDialogVisible = false,
            relatedToAllCategories = null,
            eachMonth = null,
            endDate = null,
            selectedCategory1 = null,
            selectedCategory2 = null,
            selectedCategory3 = null,
            includedInBudget = true,
            label = null
        )
    )
    val newIdeaDialogState = _newIdeaDialogState.asStateFlow()
    suspend fun addNewIdea() {
        lateinit var idea: Idea
        when (newIdeaDialogState.value.typeSelected) {
            IdeaSelectorTypes.Savings -> {
                if (newIdeaDialogState.value.includedInBudget != null && newIdeaDialogState.value.goal > 0 && newIdeaDialogState.value.label != null) {
                    idea = Savings(
                        goal = newIdeaDialogState.value.goal,
                        completed = false,
                        startDate = convertLocalDateToDate(LocalDate.now()),
                        endDate = newIdeaDialogState.value.endDate,
                        label = newIdeaDialogState.value.label ?: "",
                        value = 0f
                    )
                } else {
                    if (newIdeaDialogState.value.goal <= 0) {
                        setWarningMessage("Goal should be greater than 0")
                    } else if (newIdeaDialogState.value.label == null) {
                        setWarningMessage("Type correct saving label")
                    }
                    return
                }
            }

            IdeaSelectorTypes.IncomePlans -> {
                if (newIdeaDialogState.value.goal > 0) {
                    idea = IncomePlans(
                        goal = newIdeaDialogState.value.goal,
                        completed = false,
                        startDate = convertLocalDateToDate(LocalDate.now()),
                        endDate = newIdeaDialogState.value.endDate
                    )
                } else {
                    setWarningMessage("Goal should be greater than 0")
                    return
                }
            }

            IdeaSelectorTypes.ExpenseLimit -> {
                if (newIdeaDialogState.value.relatedToAllCategories != null && newIdeaDialogState.value.goal > 0 && (newIdeaDialogState.value.eachMonth != null || newIdeaDialogState.value.endDate != null) && (newIdeaDialogState.value.relatedToAllCategories == true || newIdeaDialogState.value.selectedCategory1 != null || newIdeaDialogState.value.selectedCategory2 != null || newIdeaDialogState.value.selectedCategory3 != null)) {
                    idea = ExpenseLimits(
                        goal = newIdeaDialogState.value.goal,
                        completed = false,
                        startDate = if (newIdeaDialogState.value.eachMonth == true) {
                            getStartOfMonthDate(convertLocalDateToDate(LocalDate.now()))
                        } else {
                            convertLocalDateToDate(LocalDate.now())
                        },
                        endDate = newIdeaDialogState.value.endDate,
                        isEachMonth = newIdeaDialogState.value.eachMonth,
                        isRelatedToAllCategories = newIdeaDialogState.value.relatedToAllCategories!!,
                        firstRelatedCategoryId = newIdeaDialogState.value.selectedCategory1?.categoryId,
                        secondRelatedCategoryId = newIdeaDialogState.value.selectedCategory2?.categoryId,
                        thirdRelatedCategoryId = newIdeaDialogState.value.selectedCategory3?.categoryId
                    )
                } else {
                    if (newIdeaDialogState.value.goal <= 0) {
                        setWarningMessage("Goal should be greater than 0")
                    } else if (newIdeaDialogState.value.eachMonth != null || newIdeaDialogState.value.endDate != null) {
                        setWarningMessage("Incorrect date")
                    }
                    return
                }
            }
        }
        createIdeaUseCase(idea)
        setIsNewIdeaDialogVisible(false)
        setGoal(0.0f)
        setLabel(null)
        setEndDate(null)
    }


    fun setIsNewIdeaDialogVisible(value: Boolean) {
        _isNewIdeaDialogVisible.value = value
        if (value) {
            setTypeSelected(IdeaSelectorTypes.Savings)
            setEndDate(null)
        }
    }

    fun setGoal(value: Float) {
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(goal = value)
    }

    fun setTypeSelected(value: IdeaSelectorTypes) {
        if (value is IdeaSelectorTypes.ExpenseLimit) {
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(typeSelected = value)
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(
                relatedToAllCategories = true,
                selectedCategory1 = null,
                selectedCategory2 = null,
                selectedCategory3 = null,
                eachMonth = true,
                endDate = null
            )
        }
        if (value is IdeaSelectorTypes.IncomePlans) {
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(typeSelected = value)
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(endDate = null)
        }
        if (value is IdeaSelectorTypes.Savings) {
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(typeSelected = value)
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(endDate = null)
        }
    }

    fun setIsDatePickerDialogVisible(value: Boolean) {
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(isDateDialogVisible = value)
    }

    fun setSelectedToAllCategories(value: Boolean) {
        if (value) {
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(relatedToAllCategories = true)
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory1 = null)
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory2 = null)
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory3 = null)
        } else {
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(relatedToAllCategories = false)
        }
    }

    fun setEachMonth(value: Boolean) {
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(eachMonth = value)
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(endDate = null)
    }

    fun setEndDate(value: Date?) {
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(endDate = value)
    }

    fun setSelectedCategory(value: ExpenseCategory?) {
        if (_newIdeaDialogState.value.selectedCategory1 == null) {
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory1 = value)
            return
        }
        if (newIdeaDialogState.value.selectedCategory2 == null) {
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory2 = value)
            return
        }
        if (newIdeaDialogState.value.selectedCategory3 == null) {
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(selectedCategory3 = value)
            return
        }
        setWarningMessage("You have already selected 3 categories")
    }

    fun setWarningMessage(value: String) {
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(warningMessage = value)
    }

    fun setLabel(label: String?) {
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(label = label)
    }

}