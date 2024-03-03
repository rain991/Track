package com.example.expensetracker.data.models.Expenses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.expensetracker.data.models.currency.Currency
import java.util.Date


@Entity(
    tableName = "Expenses",
    foreignKeys = [ForeignKey(
        entity = ExpenseCategory::class,
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
data class ExpenseItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "currencyTicker")
    val currencyTicker: String,
    @ColumnInfo(name = "value")
    val value: Float,
    @ColumnInfo(name = "note")
    val note: String,
    @ColumnInfo(name = "date")
    val date: Date,
    @ColumnInfo(name = "enabled")
    val enabled: Boolean = false,
    @ColumnInfo(name = "categoryId")
    val categoryId: Int
)