package com.example.track.data.models.idea

import java.util.Date

abstract class Idea {
    abstract val id: Int
    abstract val  goal: Float
    abstract val  completed: Boolean
    abstract val  startDate: Date
    abstract val  endDate: Date?
}