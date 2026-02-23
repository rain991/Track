package com.savenko.track.shared.domain.models.idea

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.savenko.track.shared.domain.models.abstractLayer.Idea
@Entity(tableName = "savings")
data class Savings(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    override val goal: Float,
    override val completed: Boolean,
    override val startDate: Long,
    override val endDate: Long?,
    val label : String,
    val value : Float
) : Idea()
 