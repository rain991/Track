package com.savenko.track.shared.data.database.db

import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers

actual fun getRoomDatabase(builder: RoomDatabase.Builder<ExpensesDB>): ExpensesDB {
    return builder
        .setQueryCoroutineContext(Dispatchers.Default)
        .build()
}
