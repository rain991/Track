package com.example.track.domain.models.incomes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.track.domain.models.currency.Currency
import com.example.track.domain.models.other.FinancialEntity
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
    override val id: Int = 0,
    @ColumnInfo(name = "currencyTicker")
    override val currencyTicker: String,
    @ColumnInfo(name = "categoryId")
    override val categoryId: Int,
    @ColumnInfo(name = "value")
    override val value: Float,
    @ColumnInfo(name = "note")
    override val note: String,
    @ColumnInfo(name = "date")
    override val date: Date,
    @ColumnInfo(name = "disabled")
    override val disabled: Boolean = false
) : FinancialEntity()