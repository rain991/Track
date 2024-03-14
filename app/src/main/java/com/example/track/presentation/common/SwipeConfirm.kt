package com.example.track.presentation.common

import androidx.compose.ui.graphics.Color


val GreenColor = Color(0xFF2FD286)

enum class ConfirmationState {
    Default, Confirmed
}

//@OptIn(ExperimentalWearMaterialApi::class)
//@Composable
//fun ConfirmationButton(modifier: Modifier = Modifier) {
//    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
//    val acceptButtonAvailable = false // WARNING IT IS FAKE, CHANGE REAL VALUE IN VIEWMODEL
//    val width = 350.dp
//    val dragSize = 50.dp
//    val swipeableState = rememberSwipeableState(ConfirmationState.Default)
//    val sizePx = with(LocalDensity.current) { (width - dragSize).toPx() }
//    // val anchors = mapOf(0f to ConfirmationState.Default, sizePx to ConfirmationState.Confirmed)
//    val progress by remember {
//        derivedStateOf {
//            if (swipeableState.offset.value == 0f) 0f else swipeableState.offset.value / sizePx
//        }
//    }
//
//    Box(
//        modifier = modifier
//            .width(width)
//            .swipeable(
//                state = swipeableState,
//                anchors = if (acceptButtonAvailable) {
//                    mapOf(
//                        0f to ConfirmationState.Default,
//                        sizePx to ConfirmationState.Confirmed
//                    )
//                } else {
//                    mapOf(0f to ConfirmationState.Default)
//                },
//                thresholds = { _, _ -> FractionalThreshold(0.5f) },
//                orientation = Orientation.Horizontal
//            )
//            .background(GreenColor, RoundedCornerShape(dragSize))
//    ) {
//        Column(
//            Modifier
//                .align(Alignment.Center)
//                .alpha(1f - progress),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text("Add Expense", color = Color.White, fontSize = 18.sp)
//            Text("Swipe to confirm", color = Color.White, fontSize = 12.sp)
//        }
//
//        DraggableControl(
//            modifier = Modifier
//                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
//                .size(dragSize),
//            progress = progress
//        )
//    }
//
//}
//
//@Composable
//private fun DraggableControl(
//    modifier: Modifier,
//    progress: Float
//) {
//    val addExpensesItemUseCase = koinInject<AddExpensesItemUseCase>()
//    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
//    val expense = bottomSheetViewModel.inputExpense.collectAsState()
//    val note = bottomSheetViewModel.note.collectAsState()
//    val date = bottomSheetViewModel.datePicked.collectAsState()
//    val category = bottomSheetViewModel.categoryPicked.collectAsState()
//    Box(
//        modifier
//            .padding(4.dp)
//            .shadow(elevation = 2.dp, CircleShape, clip = false)
//            .background(Color.White, CircleShape),
//        contentAlignment = Alignment.Center
//    ) {
//        val isConfirmed by remember {
//            derivedStateOf { progress >= 0.8f }
//        }
//        LaunchedEffect(isConfirmed) {
//            Log.d("MyLog", "Coroutine launched")
//            if (isConfirmed && expense.value != null && category.value != null) {
//                Log.d("MyLog", "In checked state")
//                addExpensesItemUseCase.addExpensesItem(
//                    ExpenseItem(
//                        note = note.value,
//                        date = convertLocalDateToDate(date.value),
//                        value = expense.value!!,
//                        categoryId = category.value!!.categoryId,
//                        currencyTicker = CURRENCY_DEFAULT.ticker
//                    )
//                )
//                bottomSheetViewModel.setBottomSheetExpanded(false)
//            }
//        }
//        Crossfade(targetState = isConfirmed, label = "") {
//            if (it && expense.value != null && category.value != null) {
//                Icon(
//                    imageVector = Icons.Filled.Done,
//                    contentDescription = null,
//                    tint = GreenColor
//                )
//            } else {
//                Icon(
//                    imageVector = Icons.Filled.ArrowForward,
//                    contentDescription = null,
//                    tint = GreenColor
//                )
//            }
//        }
//    }
//}