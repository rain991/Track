package com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.additionalPreferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.components.customComponents.CategoryChip

@Composable
fun AdditionalPreferencesCategoriesGrid(
    listOfCategories: List<CategoryEntity>,
    selectedCategory: CategoryEntity?,
    onCategorySelect: (CategoryEntity) -> Unit
) {
    val lazyHorizontalState = rememberLazyStaggeredGridState()
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyHorizontalStaggeredGrid(
            modifier = Modifier.heightIn(min = 48.dp, max = 180.dp),
            rows = StaggeredGridCells.FixedSize(40.dp),
            state = lazyHorizontalState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalItemSpacing = 8.dp, contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(count = listOfCategories.size) { index ->
                val item = listOfCategories[index]
                CategoryChip(modifier = Modifier
                    .wrapContentHeight(),
                    category = item,
                    isSelected = (selectedCategory == item),
                    borderColor = if (item == selectedCategory) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        null
                    },
                    onSelect = { onCategorySelect(item) })
            }
        }
    }
}