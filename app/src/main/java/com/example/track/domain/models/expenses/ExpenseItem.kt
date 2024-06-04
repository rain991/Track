package com.example.track.domain.models.expenses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.track.domain.models.currency.Currency
import com.example.track.domain.models.abstractLayer.FinancialEntity
import java.util.Date


@Entity(
    tableName = "expenses",
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
    override val id: Int = 0,
    @ColumnInfo(name = "currencyTicker")
    override val currencyTicker: String,
    @ColumnInfo(name = "value")
    override val value: Float,
    @ColumnInfo(name = "note")
    override val note: String,
    @ColumnInfo(name = "date")
    override val date: Date,
    @ColumnInfo(name = "disabled")
    override val disabled: Boolean = false,
    @ColumnInfo(name = "categoryId")
    override val categoryId: Int
) : FinancialEntity()