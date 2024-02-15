package com.example.expensetracker.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.expensetracker.data.viewmodels.MainScreenFeedViewModel
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import java.lang.reflect.Modifier

@Composable
fun MainScreenFeed() {
    val listState = rememberLazyListState()
    val mainScreenFeedViewModel  = koinViewModel<MainScreenFeedViewModel>()

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentIndex.value = (currentIndex.value + 1) % cardList.size
            listState.animateScrollToItem(0)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = listState
    ) {
        items(cardList.size) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                backgroundColor = Color.Gray
            ) {
                Text(
                    text = cardList[index].content,
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
        }
    }
}
