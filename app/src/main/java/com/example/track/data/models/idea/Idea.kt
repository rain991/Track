package com.example.track.data.models.idea

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.track.data.models.currency.Currency
import java.util.Date

@Entity(
    tableName = "idea",
    foreignKeys = [ForeignKey(
        entity = Currency::class,
        parentColumns = ["ticker"],
        childColumns = ["currencyTicker"],
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.CASCADE
    )]
)
data class Idea(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "goal")
    val goal: Int,
    @ColumnInfo(name = "currencyTicker")
    val currencyTicker: String,
    @ColumnInfo(name = "startDate")
    val startDate: Date,
    @ColumnInfo(name = "endDate")
    val endDate: Date?,
    @ColumnInfo(name = "completed")
    val completed: Boolean = false,
    @ColumnInfo(name = "relatedToAllCategories")
    val relatedToAllCategories : Boolean?,
    @ColumnInfo(name = "firstRelatedCategoryId")
    val firstRelatedCategoryId : Int?,
    @ColumnInfo(name = "secondRelatedCategoryId")
    val secondRelatedCategoryId : Int?,
    @ColumnInfo(name = "thirdRelatedCategoryId")
    val thirdRelatedCategoryId : Int?
)