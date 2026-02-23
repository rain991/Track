package com.savenko.track.shared.domain.models.abstractLayer

abstract class CategoryEntity {
    abstract val categoryId: Int
    abstract val note: String
    abstract val colorId: String
    abstract fun isDefault(): Boolean
}