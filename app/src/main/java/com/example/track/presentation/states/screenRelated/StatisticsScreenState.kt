package com.example.track.presentation.states.screenRelated

import java.util.Date

data class StatisticsScreenState(
    val hasEnoughContent: Boolean,
    val firstSlotMessage: String,
    val secondSlotMainMessage: String,
    val secondSlotAdditionalMessage: String,
    val chartData: List<Pair<Float, Date>>,
    val fourthSlotMainMessage: String,
    val fourthSlotAdditionalMessage : String
)