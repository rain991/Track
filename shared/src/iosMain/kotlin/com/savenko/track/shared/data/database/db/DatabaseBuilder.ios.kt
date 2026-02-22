package com.savenko.track.shared.data.database.db

import androidx.room.Room
import androidx.room.RoomDatabase
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<ExpensesDB> {
    val dbFilePath = NSHomeDirectory() + "/Documents/" + DB_NAME
    return Room.databaseBuilder<ExpensesDB>(name = dbFilePath)
        .addMigrations(ExpensesDB.MIGRATION_1_2)
}
