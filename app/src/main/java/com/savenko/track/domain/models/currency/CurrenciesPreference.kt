package com.savenko.track.domain.models.currency

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.savenko.track.data.other.constants.CURRENCIES_PREFERENCE_ID

@Entity(
    tableName = "currenciesPreference",
    foreignKeys = [ForeignKey(
        entity = Currency::class,
        parentColumns = ["ticker"],
        childColumns = ["preferableCurrency"],
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
    ),
        ForeignKey(
            entity = Currency::class,
            parentColumns = ["ticker"],
            childColumns = ["firstAdditionalCurrency"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Currency::class,
            parentColumns = ["ticker"],
            childColumns = ["secondAdditionalCurrency"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Currency::class,
            parentColumns = ["ticker"],
            childColumns = ["thirdAdditionalCurrency"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Currency::class,
            parentColumns = ["ticker"],
            childColumns = ["fourthAdditionalCurrency"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )]
)
data class CurrenciesPreference(
    @PrimaryKey(autoGenerate = false)
    val id: Int = CURRENCIES_PREFERENCE_ID,
    val preferableCurrency: String,
    val firstAdditionalCurrency: String?,
    val secondAdditionalCurrency: String?,
    val thirdAdditionalCurrency: String?,
    val fourthAdditionalCurrency: String?
)
