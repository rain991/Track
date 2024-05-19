package com.example.track.presentation.components.mainScreen.feed.feedCards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.track.presentation.components.common.parser.getMonthResID
import com.example.track.presentation.states.componentRelated.BudgetIdeaCardState
import java.time.LocalDate

@Composable
fun TrackMainFeedCard(state: BudgetIdeaCardState) {
    Card(
        modifier = Modifier
            .height(140.dp)
            .padding(horizontal = 8.dp), shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(id = getMonthResID(localDate = LocalDate.now())) + " " + "budget",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                textAlign = TextAlign.Center
            )
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append("Planned  ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(state.budget.toString())
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(" ")
                    append(state.currencyTicker)
                }
            })

            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append("Spent  ")
                }
                withStyle(
                    style = SpanStyle(

                        fontSize = 20.sp, fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(state.currentExpensesSum.toString())
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) {
                    append(" ")
                    append(state.currencyTicker)
                }
            })
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                //.weight(1f)
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically
        ) {
            LinearProgressIndicator(
                progress = { state.budgetExpectancy },
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.6f), strokeCap = StrokeCap.Square
            )
        }
    }
}