package com.example.track.domain.models.idea

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.track.domain.models.expenses.ExpenseCategory
import java.util.Date


@Entity(
    tableName = "expenseLimits", foreignKeys = [ForeignKey(
        entity = ExpenseCategory::class,
        parentColumns = ["categoryId"],
        childColumns = ["firstRelatedCategoryId"],
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
    ), ForeignKey(
        entity = ExpenseCategory::class,
        parentColumns = ["categoryId"],
        childColumns = ["secondRelatedCategoryId"],
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
    ), ForeignKey(
        entity = ExpenseCategory::class,
        parentColumns = ["categoryId"],
        childColumns = ["thirdRelatedCategoryId"],
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
    )]
)
data class ExpenseLimits(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val goal: Float,
    override val completed: Boolean,
    override val startDate: Date,
    override val endDate: Date?,
    val isEachMonth: Boolean?,
    val isRelatedToAllCategories: Boolean,
    val firstRelatedCategoryId: Int?,
    val secondRelatedCategoryId: Int?,
    val thirdRelatedCategoryId: Int?,
) : Idea()
