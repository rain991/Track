package com.savenko.track.shared.domain.models.idea

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.savenko.track.shared.domain.models.abstractLayer.Idea
import com.savenko.track.shared.domain.models.expenses.ExpenseCategory


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
    override val startDate: Long,
    override val endDate: Long?,
    val isEachMonth: Boolean?,
    val isRelatedToAllCategories: Boolean,
    val firstRelatedCategoryId: Int?,
    val secondRelatedCategoryId: Int?,
    val thirdRelatedCategoryId: Int?,
) : Idea()
