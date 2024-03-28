package com.example.track.data.viewmodels.statistics

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.converters.convertDateToLocalDate
import com.example.track.data.converters.convertLocalDateToDate
import com.example.track.data.converters.getEndOfTheMonth
import com.example.track.data.converters.getEndOfYearDate
import com.example.track.data.converters.getStartOfMonthDate
import com.example.track.data.converters.getStartOfYearDate
import com.example.track.data.implementations.charts.ChartsRepositoryImpl
import com.example.track.data.implementations.notes.NotesRepositoryImpl
import com.example.track.presentation.states.screenRelated.StatisticsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class StatisticsViewModel(
    private val chartsRepositoryImpl: ChartsRepositoryImpl,
    private val notesRepositoryImpl: NotesRepositoryImpl,
    private val applicationContext: Context
) :
    ViewModel() {
    private val _statisticsScreenState = MutableStateFlow(
        StatisticsScreenState(
            hasEnoughContent = false,
            firstSlotMessage = "",
            secondSlotMainMessage = "",
            secondSlotAdditionalMessage = "",
            chartBottomAxesLabels = listOf<String>(),
            chartData = listOf<Float>(),
            fourthSlotMainMessage = "",
            fourthSlotAdditionalMessage = ""
        )
    )
    val statisticsScreenState = _statisticsScreenState.asStateFlow()
    private suspend fun requestDataForFirstSlotScreenState() {
        val requestedMapOfResults = mutableMapOf<String, Number?>()
        requestedMapOfResults["requestBiggestExpenseMonthly"] = notesRepositoryImpl.requestBiggestExpenseMonthly()
        requestedMapOfResults["requestBiggestIncomeMonthly"] = (notesRepositoryImpl.requestBiggestIncomeMonthly())
        requestedMapOfResults["requestBiggestExpenseAnualy"] = (notesRepositoryImpl.requestBiggestExpenseAnualy())
        requestedMapOfResults["requestBiggestIncomeAnualy"] = (notesRepositoryImpl.requestBiggestIncomeAnualy())
        requestedMapOfResults["requestLoginCounts"] = (notesRepositoryImpl.requestLoginCounts())
        requestedMapOfResults["requestIdeasCount"] = (notesRepositoryImpl.requestIdeasCount())
        val filteredResMap = requestedMapOfResults.filterValues { it != null && it.toFloat() > 0f }
        val filteredResMapSize = filteredResMap.size
        if (filteredResMapSize == 0) return
        val randomIndex = (0..filteredResMapSize).shuffled().first() - 1
        val resultValue = filteredResMap.entries.elementAt(randomIndex)
        setFirstSlotMessage(getMessageForCurrentSlotEntry(resultValueKey = resultValue.key, value = resultValue.value!!))
    }

    private fun requestDataForSecondSlotScreenState() {
        val requestedMapOfResults = mutableMapOf<String, Number?>()
        requestedMapOfResults["requestSumOfExpensesMonthly"] = notesRepositoryImpl.requestSumOfExpensesMonthly()
        requestedMapOfResults["requestSumOfIncomesMonthly"] = (notesRepositoryImpl.requestSumOfIncomesMonthly())
        requestedMapOfResults["requestCountOfExpensesMonthly"] = (notesRepositoryImpl.requestCountOfExpensesMonthly())
        requestedMapOfResults["requestCountOfIncomesMonthly"] = (notesRepositoryImpl.requestCountOfIncomesMonthly())
        requestedMapOfResults["requestCountOfExpensesWeekly"] = (notesRepositoryImpl.requestCountOfExpensesWeekly())
        requestedMapOfResults["requestCountOfIncomesWeekly"] = (notesRepositoryImpl.requestCountOfIncomesWeekly())
        requestedMapOfResults["requestCountOfExpensesAnualy"] = (notesRepositoryImpl.requestCountOfExpensesAnualy())
        requestedMapOfResults["requestCountOfIncomeAnualy"] = (notesRepositoryImpl.requestCountOfIncomeAnualy())
        val filteredResMap = requestedMapOfResults.filterValues { it != null && it.toFloat() > 0f }
        val filteredResMapSize = filteredResMap.size
        if (filteredResMapSize == 0) return
        if (filteredResMapSize == 1) {
            val resultValue = filteredResMap.entries.elementAt(0)
            setSecondSlotMainMessage(getMessageForCurrentSlotEntry(resultValue.key, resultValue.value!!))
            return
        } else {
            val randomIndex = (0..filteredResMapSize).shuffled().first() - 1
            val mainMessageResult = filteredResMap.entries.elementAt(randomIndex)
            setSecondSlotMainMessage(getMessageForCurrentSlotEntry(mainMessageResult.key, mainMessageResult.value!!))
            val additionalMap = filteredResMap.filter { it != mainMessageResult }
            val additionalRandomIndex = (0..additionalMap.size).shuffled().first() - 1
            val additionalMessageResult = filteredResMap.entries.elementAt(additionalRandomIndex)
            setSecondSlotAdditionalMessage(getMessageForCurrentSlotEntry(additionalMessageResult.key, additionalMessageResult.value!!))
            return
        }
    }

    private fun requestDataForFourthSlotScreenState() {
        val requestedMapOfResults = mutableMapOf<String, Number?>()
        requestedMapOfResults["requestSumOfExpensesMonthly"] = notesRepositoryImpl.requestSumOfExpensesMonthly()
        requestedMapOfResults["requestSumOfIncomesMonthly"] = (notesRepositoryImpl.requestSumOfIncomesMonthly())
        requestedMapOfResults["requestCountOfExpensesMonthly"] = (notesRepositoryImpl.requestCountOfExpensesMonthly())
        requestedMapOfResults["requestCountOfIncomesMonthly"] = (notesRepositoryImpl.requestCountOfIncomesMonthly())
        requestedMapOfResults["requestCountOfExpensesWeekly"] = (notesRepositoryImpl.requestCountOfExpensesWeekly())
        requestedMapOfResults["requestCountOfIncomesWeekly"] = (notesRepositoryImpl.requestCountOfIncomesWeekly())
        requestedMapOfResults["requestCountOfExpensesAnualy"] = (notesRepositoryImpl.requestCountOfExpensesAnualy())
        requestedMapOfResults["requestCountOfIncomeAnualy"] = (notesRepositoryImpl.requestCountOfIncomeAnualy())
        val filteredResMap = requestedMapOfResults.filterValues { it != null && it.toFloat() > 0f }
        val filteredResMapSize = filteredResMap.size
        if (filteredResMapSize == 0) return
        if (filteredResMapSize == 1) {
            val resultValue = filteredResMap.entries.elementAt(0)
            setSecondSlotMainMessage(getMessageForCurrentSlotEntry(resultValue.key, resultValue.value!!))
            return
        } else {
            val randomIndex = (0..filteredResMapSize).shuffled().first() - 1
            val mainMessageResult = filteredResMap.entries.elementAt(randomIndex)
            setSecondSlotMainMessage(getMessageForCurrentSlotEntry(mainMessageResult.key, mainMessageResult.value!!))
            val additionalMap = filteredResMap.filter { it != mainMessageResult }
            val additionalRandomIndex = (0..additionalMap.size).shuffled().first() - 1
            val additionalMessageResult = filteredResMap.entries.elementAt(additionalRandomIndex)
            setSecondSlotAdditionalMessage(getMessageForCurrentSlotEntry(additionalMessageResult.key, additionalMessageResult.value!!))
            return
        }
    }


    init {
        requestDataForSecondSlotScreenState()
        viewModelScope.launch {
            requestDataForFirstSlotScreenState()
        }
    }

    private fun getMessageForCurrentSlotEntry(resultValueKey: String, value: Number): String {
       return when(resultValueKey) {
            // First Slot
            "requestBiggestExpenseAnualy" ->  ("Biggest expense this year is $value")
            "requestBiggestIncomeAnualy" ->  ("Biggest income this year is $value ")
            "requestLoginCounts" ->  ("You have opened app $value times")
            "requestIdeasCount" ->  ("You have already created $value ideas")
           "requestBiggestExpenseMonthly" -> ("Biggest expense this month is $value")

            //Second slot
           "requestBiggestIncomeMonthly" ->  ("Biggest income this month is $value")
            "requestCountOfExpensesMonthly" ->  ("You did $value expenses in current month")
            "requestCountOfIncomesMonthly" ->  ("You did $value incomes in current month")
            "requestCountOfExpensesWeekly" ->  ("You did $value expenses this week")
            "requestCountOfIncomesWeekly" ->  ("You did $value incomes this week")
            "requestCountOfExpensesAnualy" ->  ("In this year you made $value expenses")
            "requestCountOfIncomeAnualy" ->  ("In this year you made $value incomes")
           //Third Slot



           //Fourth slot
           "requestSumOfExpensesMonthly" ->  ("Your current month sum of expense is $value")
           "requestSumOfIncomesMonthly" ->  ("Your current month sum of incomes is $value")



//           suspend fun requestCurrentMonthExpensesDesc(): List<ExpenseItem>
//           suspend fun requestCurrentMonthIncomesDesc(): List<IncomeItem>
//           suspend fun requestCurrentYearExpensesDesc(): List<ExpenseItem>
//           suspend fun requestCurrentYearIncomesDesc(): List<IncomeItem>
//           suspend fun requestCurrentMonthExpenseCategoriesDistribution(): Map<ExpenseCategory, Int>
//           suspend fun requestCurrentMonthIncomeCategoriesDistribution(): Map<IncomeCategory, Int>
           else -> {""}
       }
    }

    private fun getMonthlyChartsAxesLabels(): List<LocalDate> {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        val resultList = mutableListOf<LocalDate>()
        resultList.add(convertDateToLocalDate(getStartOfMonthDate(todayDate)))
        resultList.add(convertDateToLocalDate(getEndOfTheMonth(todayDate)))
        resultList.add(LocalDate.now())
        return resultList
    }

    private fun getAnualyChartsAxesLabels(): List<LocalDate> {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        val resultList = mutableListOf<LocalDate>()
        resultList.add(convertDateToLocalDate(getStartOfYearDate(todayDate)))
        resultList.add(convertDateToLocalDate(getEndOfYearDate(todayDate)))
        resultList.add(LocalDate.now())
        return resultList
    }

    private fun setHasEnoughContent(value: Boolean) {
        _statisticsScreenState.value = statisticsScreenState.value.copy(hasEnoughContent = value)
    }

    private fun setFirstSlotMessage(value: String) {
        _statisticsScreenState.value = statisticsScreenState.value.copy(firstSlotMessage = value)
    }

    private fun setSecondSlotMainMessage(value: String) {
        _statisticsScreenState.value = statisticsScreenState.value.copy(secondSlotMainMessage = value)
    }

    private fun setSecondSlotAdditionalMessage(value: String) {
        _statisticsScreenState.value = statisticsScreenState.value.copy(secondSlotAdditionalMessage = value)
    }

    private fun setChartBottomAxesLabels(value: List<String>) {
        _statisticsScreenState.value = statisticsScreenState.value.copy(chartBottomAxesLabels = value)
    }

    private fun setChartData(value: List<Float>) {
        _statisticsScreenState.value = statisticsScreenState.value.copy(chartData = value)
    }

    private fun setFourthSlotMessage(value: String) {
        _statisticsScreenState.value = statisticsScreenState.value.copy(fourthSlotMessage = value)
    }
}