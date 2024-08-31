package com.savenko.track.presentation.components.bottomSheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.presentation.components.customComponents.CategoryChip
import org.koin.androidx.compose.koinViewModel

@Composable
fun BottomSheetCategoriesGrid(categoryList: List<CategoryEntity>) {
    val lazyHorizontalState = rememberLazyStaggeredGridState()
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val bottomSheetViewState = bottomSheetViewModel.bottomSheetViewState.collectAsState()
    val selected = bottomSheetViewState.value.categoryPicked
    Column(modifier = Modifier.fillMaxWidth()) {
        LazyHorizontalStaggeredGrid(
            modifier = Modifier.heightIn(min = 48.dp, max = 180.dp),
            rows = StaggeredGridCells.FixedSize(40.dp),
            state = lazyHorizontalState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalItemSpacing = 8.dp, contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(count = categoryList.size) { index ->
                val item = categoryList[index]
                CategoryChip(
                    category = item,
                    isSelected = (selected == item), borderColor = if (selected == item) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        Color.Transparent
                    },
                    onSelect = { bottomSheetViewModel.setCategoryPicked(item) })
            }
        }
    }
}