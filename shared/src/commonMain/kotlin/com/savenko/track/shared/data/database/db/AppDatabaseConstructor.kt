package com.savenko.track.shared.data.database.db

import androidx.room.RoomDatabaseConstructor

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<ExpensesDB> {
    override fun initialize(): ExpensesDB
}