package com.example.expensetracker.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.expensetracker.domain.ExpenseItem

@Database(
    entities = [ExpenseItem::class],
     version = 2
)
abstract class ExpensesDB : RoomDatabase() {
abstract val dao : ExpensesDAO
    companion object {
        private var INSTANCE: ExpensesDB? = null

        fun getInstance(context: Context): ExpensesDB {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): ExpensesDB {
            return Room.databaseBuilder(
                context.applicationContext,
                ExpensesDB::class.java, "main.db"
            ).addMigrations(MIGRATION_1_2).build()
        }
        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS Expenses")
                database.execSQL("CREATE TABLE IF NOT EXISTS `Expenses` " +
                        "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                        "`name` TEXT NOT NULL, " +
                        "`date` TEXT NOT NULL, " +
                        "`enabled` INTEGER NOT NULL, " +
                        "`value` REAL NOT NULL, " +
                        "`category` TEXT NOT NULL)")
            }
        }
    }
}