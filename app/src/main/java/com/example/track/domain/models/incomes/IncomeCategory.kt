package com.example.track.domain.models.incomes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.track.domain.models.abstractLayer.CategoryEntity

@Entity(tableName = "income_categories")
data class IncomeCategory(
    @PrimaryKey(autoGenerate = true)
    override var categoryId: Int = 0,
    @ColumnInfo(name = "note")
    override val note: String,
    @ColumnInfo(name = "colorId")
    override val colorId: String
) : CategoryEntity()
