package com.example.track.data.viewmodels.statistics

import androidx.lifecycle.ViewModel
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
    val statistcsScreenState = _statisticsScreenState.asStateFlow()
     fun requestDataForScreenState(){

    }

    init{

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

    private fun setHasEnoughContent(value : Boolean){
        _statisticsScreenState.value = statistcsScreenState.value.copy(hasEnoughContent = value)
    }
    private fun setFirstSlotMessage(value : String){
        _statisticsScreenState.value = statistcsScreenState.value.copy(firstSlotMessage = value)
    }
    private fun setSecondSlotMainMessage(value : String){
        _statisticsScreenState.value = statistcsScreenState.value.copy(secondSlotMainMessage = value)
    }
    private fun setSecondSlotAdditionalMessage(value : String){
        _statisticsScreenState.value = statistcsScreenState.value.copy(secondSlotAdditionalMessage = value)
    }
    private fun setChartBottomAxesLabels(value : List<String>){
        _statisticsScreenState.value = statistcsScreenState.value.copy(chartBottomAxesLabels = value)
    }
    private fun setChartData(value : List<Float>){
        _statisticsScreenState.value = statistcsScreenState.value.copy(chartData = value)
    }
    private fun setFourthSlotMessage(value : String){
        _statisticsScreenState.value = statistcsScreenState.value.copy(fourthSlotMessage = value)
    }
}