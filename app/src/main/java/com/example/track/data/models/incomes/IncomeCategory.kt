package com.example.track.data.models.incomes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "income_categories")
data class IncomeCategory (
@PrimaryKey(autoGenerate = true)
var categoryId: Int = 0,
@ColumnInfo(name = "note")
val note: String,
@ColumnInfo(name = "colorId")
val colorId: String
)
