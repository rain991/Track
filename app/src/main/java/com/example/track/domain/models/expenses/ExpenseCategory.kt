package com.example.track.domain.models.expenses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.track.domain.models.abstractLayer.CategoryEntity

@Entity(tableName = "expense_categories")
data class ExpenseCategory(
    @PrimaryKey(autoGenerate = true)
    override var categoryId: Int = 0,
    @ColumnInfo(name = "note")
    override val note: String,
    @ColumnInfo(name = "colorId")
    override val colorId: String
) : CategoryEntity()