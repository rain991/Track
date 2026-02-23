package com.savenko.track.shared.data.database.db

import androidx.room.RoomDatabase

expect fun getRoomDatabase(builder: RoomDatabase.Builder<ExpensesDB>): ExpensesDB
