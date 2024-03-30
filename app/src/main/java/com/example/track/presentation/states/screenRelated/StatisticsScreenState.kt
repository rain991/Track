package com.example.track.presentation.states.screenRelated

import java.time.LocalDate

data class StatisticsScreenState(
    val hasEnoughContent: Boolean,
    val firstSlotMessage: String,
    val secondSlotMainMessage: String,
    val secondSlotAdditionalMessage: String,
    val chartBottomAxesLabels: List<LocalDate>,
    val chartData: List<Float>,
    val fourthSlotMainMessage: String,
    val fourthSlotAdditionalMessage : String
)