package com.example.expensetracker.data.models.Expenses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date


@Entity(
    tableName = "Expenses",
    foreignKeys = [ForeignKey(
        entity = ExpenseCategory::class,
        parentColumns = ["categoryId"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class ExpenseItem(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
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