package com.savenko.track.data.viewmodels.mainScreen.feed

import androidx.lifecycle.ViewModel
import com.savenko.track.data.other.constants.EXPENSE_LIMIT_MAX_CATEGORIES_SELECTED
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.domain.models.abstractLayer.Idea
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.domain.usecases.crud.ideasRelated.CreateIdeaUseCase
import com.savenko.track.presentation.components.dialogs.addToSavingIdeaDialog.AddToSavingDialog
import com.savenko.track.presentation.other.composableTypes.errors.NewIdeaDialogErrors
import com.savenko.track.presentation.other.composableTypes.options.IdeaSelectorTypes
import com.savenko.track.presentation.screens.states.core.common.NewIdeaDialogState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.util.Date

/**
 * Handles state of [AddToSavingDialog]
 */
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
            listOfSelectedCategories = listOf(),
            includedInBudget = true,
            label = null
        )
    )
    val newIdeaDialogState = _newIdeaDialogState.asStateFlow()

    suspend fun addNewIdea() {
        when (newIdeaDialogState.value.typeSelected) {
            IdeaSelectorTypes.Savings -> {
                addNewSavings()
            }

            IdeaSelectorTypes.IncomePlans -> {
                addNewIncomePlan()
            }

            IdeaSelectorTypes.ExpenseLimit -> {
                addNewExpenseLimit()
            }
        }
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
        if (value > 0 && _newIdeaDialogState.value.warningMessage is NewIdeaDialogErrors.IncorrectGoalValue) {
            setWarningMessage(null)
        }
    }

    fun setTypeSelected(value: IdeaSelectorTypes) {
        if (value is IdeaSelectorTypes.ExpenseLimit) {
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(typeSelected = value)
            _newIdeaDialogState.value = newIdeaDialogState.value.copy(
                relatedToAllCategories = true,
                listOfSelectedCategories = listOf(),
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
            _newIdeaDialogState.value =
                newIdeaDialogState.value.copy(listOfSelectedCategories = emptyList())
        } else {
            _newIdeaDialogState.value =
                newIdeaDialogState.value.copy(relatedToAllCategories = false)
        }
    }

    fun setEachMonth(value: Boolean) {
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(eachMonth = value)
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(endDate = null)
    }

    fun setEndDate(value: Date?) {
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(endDate = value)
    }

    fun setSelectedCategory(category: ExpenseCategory) {
        val listOfCategories = _newIdeaDialogState.value.listOfSelectedCategories
        val isAlreadyAdded = listOfCategories.filter { it == category }
        if (isAlreadyAdded.isNotEmpty()) {
            _newIdeaDialogState.value =
                _newIdeaDialogState.value.copy(listOfSelectedCategories = listOfCategories.filter { it != category })
            if (_newIdeaDialogState.value.warningMessage is NewIdeaDialogErrors.MaxCategoriesSelected) {
                _newIdeaDialogState.value = _newIdeaDialogState.value.copy(warningMessage = null)
            }
            return
        }
        if (listOfCategories.size < EXPENSE_LIMIT_MAX_CATEGORIES_SELECTED) {
            val mutableList = listOfCategories.toMutableList()
            mutableList.add(category)
            _newIdeaDialogState.value = _newIdeaDialogState.value.copy(
                listOfSelectedCategories = mutableList
            )
            if (_newIdeaDialogState.value.warningMessage is NewIdeaDialogErrors.SelectCategory) {
                setWarningMessage(null)
            }
        } else if (listOfCategories.size == EXPENSE_LIMIT_MAX_CATEGORIES_SELECTED) {
            setWarningMessage(NewIdeaDialogErrors.MaxCategoriesSelected)
        }
    }

    fun setLabel(label: String?) {
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(label = label)
        if (_newIdeaDialogState.value.label != null && _newIdeaDialogState.value.label!!.isNotEmpty() && _newIdeaDialogState.value.warningMessage is NewIdeaDialogErrors.IncorrectSavingLabel) {
            setWarningMessage(null)
        }
    }

    private suspend fun addNewSavings() {
        if (newIdeaDialogState.value.includedInBudget != null && newIdeaDialogState.value.goal > 0 && newIdeaDialogState.value.label != null && newIdeaDialogState.value.label!!.isNotEmpty()) {
            addNewIdea(
                Savings(
                    goal = newIdeaDialogState.value.goal,
                    completed = false,
                    startDate = convertLocalDateToDate(LocalDate.now()),
                    endDate = newIdeaDialogState.value.endDate,
                    label = newIdeaDialogState.value.label ?: "",
                    value = 0f
                )
            )
        } else {
            if (newIdeaDialogState.value.goal <= 0) {
                setWarningMessage(NewIdeaDialogErrors.IncorrectGoalValue)
            } else if (newIdeaDialogState.value.label == null || newIdeaDialogState.value.label!!.isNotEmpty()) {
                setWarningMessage(NewIdeaDialogErrors.IncorrectSavingLabel)
            }
            return
        }
    }

    private suspend fun addNewIncomePlan() {
        if (newIdeaDialogState.value.goal > 0) {
            addNewIdea(
                IncomePlans(
                    goal = newIdeaDialogState.value.goal,
                    completed = false,
                    startDate = convertLocalDateToDate(LocalDate.now()),
                    endDate = newIdeaDialogState.value.endDate
                )
            )
        } else {
            setWarningMessage(NewIdeaDialogErrors.IncorrectGoalValue)
            return
        }
    }

    private suspend fun addNewExpenseLimit() {
        if (newIdeaDialogState.value.relatedToAllCategories != null && newIdeaDialogState.value.goal > 0 && (newIdeaDialogState.value.eachMonth != null || newIdeaDialogState.value.endDate != null) && (newIdeaDialogState.value.relatedToAllCategories == true || newIdeaDialogState.value.listOfSelectedCategories.isNotEmpty())) {
            addNewIdea(
                ExpenseLimits(
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
                    firstRelatedCategoryId = newIdeaDialogState.value.listOfSelectedCategories.getOrNull(
                        0
                    )?.categoryId,
                    secondRelatedCategoryId = newIdeaDialogState.value.listOfSelectedCategories.getOrNull(
                        1
                    )?.categoryId,
                    thirdRelatedCategoryId = newIdeaDialogState.value.listOfSelectedCategories.getOrNull(
                        2
                    )?.categoryId
                )
            )
        } else {
            when {
                newIdeaDialogState.value.goal <= 0 -> {
                    setWarningMessage(NewIdeaDialogErrors.IncorrectGoalValue)
                }
                newIdeaDialogState.value.eachMonth == null && newIdeaDialogState.value.endDate == null -> {
                    setWarningMessage(NewIdeaDialogErrors.EmptyDate)
                }
                newIdeaDialogState.value.relatedToAllCategories == false && newIdeaDialogState.value.listOfSelectedCategories.isEmpty() -> {
                    setWarningMessage(NewIdeaDialogErrors.SelectCategory)
                }
                else -> return
            }
        }
    }

    private suspend fun addNewIdea(idea: Idea) {
        createIdeaUseCase(idea)
        setGoal(0.0f)
        setLabel(null)
        setEndDate(null)
        setIsNewIdeaDialogVisible(false)
    }

    private fun setWarningMessage(value: NewIdeaDialogErrors?) {
        _newIdeaDialogState.value = newIdeaDialogState.value.copy(warningMessage = value)
    }
}