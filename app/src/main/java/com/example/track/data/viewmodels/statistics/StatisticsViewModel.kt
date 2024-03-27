package com.example.track.data.viewmodels.statistics

import androidx.lifecycle.ViewModel
import com.example.track.data.implementations.charts.ChartsRepositoryImpl
import com.example.track.data.implementations.notes.NotesRepositoryImpl
import java.time.LocalDate

class StatisticsViewModel(private val chartsRepositoryImpl: ChartsRepositoryImpl, private val notesRepositoryImpl: NotesRepositoryImpl) : ViewModel(){










    private fun getMonthlyChartsAxesLabels() : List<LocalDate>{

    }
}