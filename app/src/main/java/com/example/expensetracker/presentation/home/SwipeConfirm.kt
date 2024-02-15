package com.example.expensetracker.presentation.home

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.example.expensetracker.data.converters.convertLocalDateToDate
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import com.example.expensetracker.data.viewmodels.common.BottomSheetViewModel
import com.example.expensetracker.domain.usecases.expenseusecases.AddExpensesItemUseCase
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import kotlin.math.roundToInt


val GreenColor = Color(0xFF2FD286)

enum class ConfirmationState {
    Default, Confirmed
}

@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun ConfirmationButton(modifier: Modifier = Modifier) {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val acceptButtonAvailable = false // WARNING IT IS FAKE, CHANGE REAL VALUE IN VIEWMODEL
    val width = 350.dp
    val dragSize = 50.dp
    val swipeableState = rememberSwipeableState(ConfirmationState.Default)
    val sizePx = with(LocalDensity.current) { (width - dragSize).toPx() }
    // val anchors = mapOf(0f to ConfirmationState.Default, sizePx to ConfirmationState.Confirmed)
    val progress by remember {
        derivedStateOf {
            if (swipeableState.offset.value == 0f) 0f else swipeableState.offset.value / sizePx
        }
    }

    Box(
        modifier = modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = if (acceptButtonAvailable) {
                    mapOf(
                        0f to ConfirmationState.Default,
                        sizePx to ConfirmationState.Confirmed
                    )
                } else {
                    mapOf(0f to ConfirmationState.Default)
                },
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal
            )
            .background(GreenColor, RoundedCornerShape(dragSize))
    ) {
        Column(
            Modifier
                .align(Alignment.Center)
                .alpha(1f - progress),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Add Expense", color = Color.White, fontSize = 18.sp)
            Text("Swipe to confirm", color = Color.White, fontSize = 12.sp)
        }

        DraggableControl(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(dragSize),
            progress = progress
        )
    }

}

@Composable
private fun DraggableControl(
    modifier: Modifier,
    progress: Float
) {
    val addExpensesItemUseCase = koinInject<AddExpensesItemUseCase>()
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val expense = bottomSheetViewModel.inputExpense.collectAsState()
    val note = bottomSheetViewModel.note.collectAsState()
    val date = bottomSheetViewModel.datePicked.collectAsState()
    val category = bottomSheetViewModel.categoryPicked.collectAsState()
    Box(
        modifier
            .padding(4.dp)
            .shadow(elevation = 2.dp, CircleShape, clip = false)
            .background(Color.White, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        val isConfirmed by remember {
            derivedStateOf { progress >= 0.8f }
        }
        LaunchedEffect(isConfirmed) {
            Log.d("MyLog", "Coroutine launched")
            if (isConfirmed && expense.value != null && category.value != null) {
                Log.d("MyLog", "In checked state")
                addExpensesItemUseCase.addExpensesItem(
                    ExpenseItem(
                        note = note.value,
                        date = convertLocalDateToDate(date.value),
                        value = expense.value!!,
                        categoryId = category.value!!.categoryId.toInt()
                    )
                )
                bottomSheetViewModel.setBottomSheetExpanded(false)
            }
        }
        Crossfade(targetState = isConfirmed, label = "") {
            if (it && expense.value != null && category.value != null) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    tint = GreenColor
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = null,
                    tint = GreenColor
                )
            }
        }
    }
}