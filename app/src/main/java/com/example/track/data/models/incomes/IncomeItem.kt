package com.example.track.data.models.incomes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.track.data.models.currency.Currency
import java.util.Date

@Entity(
    tableName = "incomes",
    foreignKeys = [ForeignKey(
        entity = IncomeCategory::class,
        parentColumns = ["categoryId"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    ), ForeignKey(
        entity = Currency::class,
        parentColumns = ["ticker"],
        childColumns = ["currencyTicker"],
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
    )]
)
data class IncomeItem (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "currencyTicker")
    val currencyTicker: String,
    @ColumnInfo(name = "categoryId")
    val categoryId: Int,
    @ColumnInfo(name = "value")
    val value: Float,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "enabled")
    val enabled: Boolean = false
)