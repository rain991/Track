package com.example.track.presentation.states.screenRelated

data class StatisticsScreenState(
    val hasEnoughContent: Boolean,
    val firstSlotMessage: String,
    val secondSlotMainMessage: String,
    val secondSlotAdditionalMessage: String,
    val chartBottomAxesLabels: List<String>,
    val chartData: List<Float>,
    val fourthSlotMessage: String
)