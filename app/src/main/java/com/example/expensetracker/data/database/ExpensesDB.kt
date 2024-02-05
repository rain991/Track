package com.example.expensetracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.expensetracker.data.converters.Converters
import com.example.expensetracker.data.models.ExpenseCategory
import com.example.expensetracker.data.models.ExpenseItem

@Database(
    entities = [ExpenseItem::class, ExpenseCategory::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ExpensesDB : RoomDatabase() {
    abstract val expensesDao: ExpensesDAO
    abstract val categoryDao: ExpenseCategoryDao

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
            ).build()
        }

//        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//            override fun migrate(database: SupportSQLiteDatabase) {
//                database.execSQL("DROP TABLE IF EXISTS Expenses")
//                database.execSQL(
//                    "CREATE TABLE IF NOT EXISTS `Expenses` " +
//                            "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                            "`note` TEXT NOT NULL, " +
//                            "`date` TEXT NOT NULL, " +
//                            "`enabled` INTEGER NOT NULL, " +
//                            "`value` REAL NOT NULL, " +
//                            "`category_id` INTEGER NOT NULL DEFAULT 0)"
//                )  // prob foreign key
//            }
//        }
//        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                db.execSQL(
//                    "CREATE TABLE IF NOT EXISTS 'expense_categories' " +
//                            "('category_id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                            "`note` TEXT NOT NULL, " +
//                            "`colorId` INTEGER NOT NULL)"
//                )
//
//                // Create a temporary 'Expenses_new' table with the updated schema
//                db.execSQL(
//                    "CREATE TABLE IF NOT EXISTS `Expenses_new` " +
//                            "(`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
//                            "`note` TEXT NOT NULL, " +
//                            "`date` TEXT NOT NULL, " +
//                            "`enabled` INTEGER NOT NULL, " +
//                            "`value` REAL NOT NULL, " +
//                            "`categoryId` INTEGER NOT NULL DEFAULT 0)"
//                )
//
//                // Copy data from the existing 'Expenses' table to the new 'Expenses_new' table
//                db.execSQL("INSERT INTO Expenses_new (id, note, date, enabled, value, categoryId) SELECT id, note, date, enabled, value, categoryId FROM Expenses")
//
//                // Drop the existing 'Expenses' table
//                db.execSQL("DROP TABLE IF EXISTS Expenses")
//
//                // Rename the 'Expenses_new' table to 'Expenses'
//                db.execSQL("ALTER TABLE Expenses_new RENAME TO Expenses")
//            }
//
//        }
    }
}

