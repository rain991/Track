package com.example.track.domain.models.idea

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.track.domain.models.abstractLayer.Idea
import java.util.Date
@Entity(tableName = "savings")
data class Savings(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val goal: Float,
    override val completed: Boolean,
    override val startDate: Date,
    override val endDate: Date?,
    val includedInBudget : Boolean,
    val label : String,
    val value : Float
) : Idea()
