package com.savenko.track.shared.data.database.db

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.savenko.track.shared.data.other.constants.DB_PATH

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<ExpensesDB> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(DB_NAME)

    return Room.databaseBuilder<ExpensesDB>(
        context = appContext,
        name = dbFile.absolutePath,
    )
        .createFromAsset(DB_PATH)
        .addMigrations(ExpensesDB.MIGRATION_1_2)
}
