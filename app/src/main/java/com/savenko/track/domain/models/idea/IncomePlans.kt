package com.savenko.track.domain.models.idea

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.savenko.track.domain.models.abstractLayer.Idea
import java.util.Date
@Entity(tableName = "incomePlans")
    data class IncomePlans(
        @PrimaryKey(autoGenerate = true)
        override val id: Int = 0,
        override val goal: Float,
        override val completed: Boolean,
        override val startDate: Date,
        override val endDate: Date?
    ) : Idea()
