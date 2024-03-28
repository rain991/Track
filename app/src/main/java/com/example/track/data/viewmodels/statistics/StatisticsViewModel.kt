package com.example.track.data.viewmodels.statistics

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

class StatisticsViewModel(private val chartsRepositoryImpl: ChartsRepositoryImpl, private val notesRepositoryImpl: NotesRepositoryImpl) :
    ViewModel() {
    private val _statisticsScreenState = MutableStateFlow(
        StatisticsScreenState(
            hasEnoughContent = false,
            firstSlotMessage = "",
            secondSlotMainMessage = "",
            secondSlotAdditionalMessage = "",
            chartBottomAxesLabels = listOf<String>(),
            chartData = listOf<Float>(),
            fourthSlotMessage = ""
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
        when (resultValue.key) {
            "requestBiggestExpenseMonthly" -> setFirstSlotMessage("Biggest expense this month is ${resultValue.value}")
            "requestBiggestIncomeMonthly" -> setFirstSlotMessage("Biggest income this month is ${resultValue.value}")
            "requestBiggestExpenseAnualy" -> setFirstSlotMessage("Biggest expense this year is ${resultValue.value}")
            "requestBiggestIncomeAnualy" -> setFirstSlotMessage("Biggest income this year is ${resultValue.value} ")
            "requestLoginCounts" -> setFirstSlotMessage("You have opened app ${resultValue.value} times")
            "requestIdeasCount" -> setFirstSlotMessage("You have already created ${resultValue.value} ideas")
        }
    }

    init {
        viewModelScope.launch {
            requestDataForFirstSlotScreenState()
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