package com.example.expensetracker.data.models.idea

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.expensetracker.data.models.other.Currency
import java.util.Date

@Entity(
    tableName = "idea",
    foreignKeys = [ForeignKey(
        entity = Currency::class,
        parentColumns = ["ticker"],
        childColumns = ["currencyTicker"],
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.CASCADE
    )]
)

data class Idea(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val goal: Int,
    val currencyTicker: String,
    val startDate: Date,
    val endDate: Date?,
    val completed: Boolean = false,
    val liked: Boolean = false
)