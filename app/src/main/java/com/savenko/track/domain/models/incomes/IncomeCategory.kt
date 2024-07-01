package com.savenko.track.domain.models.incomes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.savenko.track.data.other.constants.DEFAULT_INCOME_CATEGORIES_MAX_INDEX
import com.savenko.track.domain.models.abstractLayer.CategoryEntity

@Entity(tableName = "income_categories")
data class IncomeCategory(
    @PrimaryKey(autoGenerate = true)
    override var categoryId: Int = 0,
    @ColumnInfo(name = "note")
    override val note: String,
    @ColumnInfo(name = "colorId")
    override val colorId: String
) : CategoryEntity() {
    override fun isDefault(): Boolean {
        return this.categoryId in 0..DEFAULT_INCOME_CATEGORIES_MAX_INDEX
    }
}
