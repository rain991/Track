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
import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.incomes.IncomeCategory
import com.example.track.data.models.other.CategoryEntity
import com.example.track.presentation.states.screenRelated.StatisticsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.random.Random

class StatisticsViewModel(
    private val chartsRepositoryImpl: ChartsRepositoryImpl,
    private val notesRepositoryImpl: NotesRepositoryImpl
) : ViewModel() {
    private val _statisticsScreenState = MutableStateFlow(
        StatisticsScreenState(
            hasEnoughContent = false,
            firstSlotMessage = "",
            secondSlotMainMessage = "",
            secondSlotAdditionalMessage = "",
            chartBottomAxesLabels = listOf<LocalDate>(),
            chartData = listOf<Float>(),
            fourthSlotMainMessage = "",
            fourthSlotAdditionalMessage = ""
        )
    )
    val statisticsScreenState = _statisticsScreenState.asStateFlow()
    init {
        viewModelScope.launch(Dispatchers.IO) {
            requestDataForFirstSlotScreenState()
        }
        viewModelScope.launch(Dispatchers.IO) {
            requestDataForSecondSlotScreenState()
        }
        viewModelScope.launch (Dispatchers.IO){
            requestDataForThirdSlotScreenState()
        }
        viewModelScope.launch(Dispatchers.IO) {
            requestDataForFourthSlotScreenState()
        }
    }
    private suspend fun requestDataForFirstSlotScreenState() {
        val requestedMapOfResults = mutableMapOf<String, Number?>()
        requestedMapOfResults["requestBiggestExpenseMonthly"] = notesRepositoryImpl.requestBiggestExpenseMonthly()
        requestedMapOfResults["requestBiggestExpenseAnualy"] = (notesRepositoryImpl.requestBiggestExpenseAnualy())
        requestedMapOfResults["requestBiggestIncomeAnualy"] = (notesRepositoryImpl.requestBiggestIncomeAnualy())
        requestedMapOfResults["requestLoginCounts"] = (notesRepositoryImpl.requestLoginCounts())
        requestedMapOfResults["requestIdeasCount"] = (notesRepositoryImpl.requestIdeasCount())
        val filteredResMap = requestedMapOfResults.filterValues { it != null && it.toFloat() > 0f }
        val filteredResMapSize = filteredResMap.size
        if (filteredResMapSize == 0) return
        setHasEnoughContent(true)
        val randomIndex = (0..filteredResMapSize).shuffled().first() - 1
        val resultValue = filteredResMap.entries.elementAt(randomIndex)
        setFirstSlotMessage(getMessageForCurrentSlotEntry(resultValueKey = resultValue.key, value = resultValue.value!!))
    }

    private suspend fun requestDataForSecondSlotScreenState() {
        val requestedMapOfResults = mutableMapOf<String, Number?>()
        requestedMapOfResults["requestBiggestIncomeMonthly"] = (notesRepositoryImpl.requestBiggestIncomeMonthly())
        requestedMapOfResults["requestCountOfExpensesMonthly"] = (notesRepositoryImpl.requestCountOfExpensesMonthly())
        requestedMapOfResults["requestCountOfIncomesMonthly"] = (notesRepositoryImpl.requestCountOfIncomesMonthly())
        requestedMapOfResults["requestCountOfExpensesWeekly"] = (notesRepositoryImpl.requestCountOfExpensesWeekly())
        requestedMapOfResults["requestCountOfIncomesWeekly"] = (notesRepositoryImpl.requestCountOfIncomesWeekly())
        requestedMapOfResults["requestCountOfExpensesAnualy"] = (notesRepositoryImpl.requestCountOfExpensesAnualy())
        requestedMapOfResults["requestCountOfIncomeAnualy"] = (notesRepositoryImpl.requestCountOfIncomeAnualy())
        val filteredResMap = requestedMapOfResults.filterValues { it != null && it.toFloat() > 0f }
        val filteredResMapSize = filteredResMap.size
        if (filteredResMapSize == 0) return
        setHasEnoughContent(true)
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

    private suspend fun requestDataForThirdSlotScreenState() {
        val countOfOptionSets = 2  // count of different list
        val listOfMonthOptions = listOf(
            chartsRepositoryImpl.requestCurrentMonthExpensesDateDesc(),
            chartsRepositoryImpl.requestCurrentMonthIncomesDateDesc()
        )

        val listOfYearOptions = listOf(
            chartsRepositoryImpl.requestCurrentYearExpensesDateDesc(),
            chartsRepositoryImpl.requestCurrentYearIncomesDateDesc()
        )
        val currentDataSetIdentifier = Random.nextInt(0, countOfOptionSets)
        val listOfFinancialEntity = when (currentDataSetIdentifier) {
            0 -> {
                listOfMonthOptions[Random.nextInt(0, listOfMonthOptions.size)]
            }

            1 -> {
                listOfYearOptions[Random.nextInt(0, listOfYearOptions.size)]
            }

            else -> {
                emptyList()
            }
        }
        val listOfValues = listOfFinancialEntity.map { // warning currency rate
            it.value
        }
        val chartBottomAxesLabels = when (currentDataSetIdentifier) {
            0 -> {
                getMonthlyChartsAxesLabels()
            }

            1 -> {
                getAnualyChartsAxesLabels()
            }

            else -> {
                emptyList<LocalDate>()
            }
        }
        setChartData(listOfValues)
        setChartBottomAxesLabels(chartBottomAxesLabels)
    }

    private suspend fun requestDataForFourthSlotScreenState() { //low scalability
        val requestedMapOfResults = mutableMapOf<String, Number?>()
        requestedMapOfResults["requestSumOfExpensesMonthly"] = notesRepositoryImpl.requestSumOfExpensesMonthly()
        requestedMapOfResults["requestSumOfIncomesMonthly"] = notesRepositoryImpl.requestSumOfIncomesMonthly()
        val currentMonthExpenseDistribution = chartsRepositoryImpl.requestCurrentMonthExpenseCategoriesDistribution()
        val currentMonthIncomeDistribution = chartsRepositoryImpl.requestCurrentMonthIncomeCategoriesDistribution()
        val sortedExpenseDistribution = currentMonthExpenseDistribution.toList().sortedByDescending { it.second }.toMap()
        val sortedIncomeDistribution = currentMonthIncomeDistribution.toList().sortedByDescending { it.second }.toMap()
        val filteredResMap = requestedMapOfResults.filterValues { it != null && it.toFloat() > 0f }
        val filteredResMapSize = filteredResMap.size
        if (filteredResMapSize == 0) return
        setHasEnoughContent(true)
        if (filteredResMapSize == 1) {
            if (Random.nextBoolean()) {
                val resultValue = filteredResMap.entries.elementAt(0)
                setFourthSlotMainMessage(getMessageForCurrentSlotEntry(resultValue.key, resultValue.value!!))
            } else {
                if (Random.nextBoolean()) {
                    setFourthSlotMainMessage(getMessageForCurrentCategoriesDistribution(sortedExpenseDistribution))
                } else {
                    setFourthSlotMainMessage(getMessageForCurrentCategoriesDistribution(sortedIncomeDistribution))
                }
            }
            return
        } else {
            if (Random.nextBoolean()) {
                val resultValue = filteredResMap.entries.elementAt(0)
                setFourthSlotMainMessage(getMessageForCurrentSlotEntry(resultValue.key, resultValue.value!!))
                if (Random.nextBoolean()) {
                    setFourthSlotAdditionalMessage(getMessageForCurrentCategoriesDistribution(sortedExpenseDistribution))
                } else {
                    setFourthSlotAdditionalMessage(getMessageForCurrentCategoriesDistribution(sortedIncomeDistribution))
                }
            } else {

                if (Random.nextBoolean()) {
                    setFourthSlotMainMessage(getMessageForCurrentCategoriesDistribution(sortedExpenseDistribution))
                } else {
                    setFourthSlotMainMessage(getMessageForCurrentCategoriesDistribution(sortedIncomeDistribution))
                }
                val resultValue = filteredResMap.entries.elementAt(0)
                setFourthSlotAdditionalMessage(getMessageForCurrentSlotEntry(resultValue.key, resultValue.value!!))
            }
            return
        }
    }




    private fun getMessageForCurrentSlotEntry(resultValueKey: String, value: Number): String {
        return when (resultValueKey) {
            // First Slot
            "requestBiggestExpenseAnualy" -> ("Biggest expense this year is $value")
            "requestBiggestIncomeAnualy" -> ("Biggest income this year is $value ")
            "requestLoginCounts" -> ("You have opened app $value times")
            "requestIdeasCount" -> ("You have already created $value ideas")
            "requestBiggestExpenseMonthly" -> ("Biggest expense this month is $value")
            //Second slot
            "requestBiggestIncomeMonthly" -> ("Biggest income this month is $value")
            "requestCountOfExpensesMonthly" -> ("You did $value expenses in current month")
            "requestCountOfIncomesMonthly" -> ("You did $value incomes in current month")
            "requestCountOfExpensesWeekly" -> ("You did $value expenses this week")
            "requestCountOfIncomesWeekly" -> ("You did $value incomes this week")
            "requestCountOfExpensesAnualy" -> ("In this year you made $value expenses")
            "requestCountOfIncomeAnualy" -> ("In this year you made $value incomes")
            //Fourth slot
            "requestSumOfExpensesMonthly" -> ("Your current month sum of expense is $value")
            "requestSumOfIncomesMonthly" -> ("Your current month sum of incomes is $value")
            else -> {
                ""
            }
        }
    }

    private fun getMessageForCurrentCategoriesDistribution(sortedMapDesc: Map<out CategoryEntity, Int>): String {
        val listOfPairs = sortedMapDesc.toList()
        val topThree = listOfPairs.take(3)
        return when (sortedMapDesc.keys.first()) {
            is ExpenseCategory -> ("Most used expense categories : ${topThree[0].first.note}, ${topThree[1].first.note}, ${topThree[2].first.note}")
            is IncomeCategory -> ("Most used income categories : ${topThree[0].first.note}, ${topThree[1].first.note}, ${topThree[2].first.note}")
            else -> {
                ""
            }
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

    private fun setChartBottomAxesLabels(value: List<LocalDate>) {
        _statisticsScreenState.value = statisticsScreenState.value.copy(chartBottomAxesLabels = value)
    }

    private fun setChartData(value: List<Float>) {
        _statisticsScreenState.value = statisticsScreenState.value.copy(chartData = value)
    }

    private fun setFourthSlotMainMessage(value: String) {
        _statisticsScreenState.value = statisticsScreenState.value.copy(fourthSlotMainMessage = value)
    }

    private fun setFourthSlotAdditionalMessage(value: String) {
        _statisticsScreenState.value = statisticsScreenState.value.copy(fourthSlotAdditionalMessage = value)
    }
}