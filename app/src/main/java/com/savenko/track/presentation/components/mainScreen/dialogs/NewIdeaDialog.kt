package com.savenko.track.presentation.components.mainScreen.dialogs


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.savenko.track.R
import com.savenko.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.viewmodels.mainScreen.NewIdeaDialogViewModel
import com.savenko.track.presentation.components.mainScreen.dialogs.dialogComponents.ExpenseLimitsDialogInputs
import com.savenko.track.presentation.components.mainScreen.dialogs.dialogComponents.IdeaInputField
import com.savenko.track.presentation.components.mainScreen.dialogs.dialogComponents.IncomePlanDialogInputs
import com.savenko.track.presentation.components.mainScreen.dialogs.dialogComponents.SavingsDialogInputs
import com.savenko.track.presentation.states.componentRelated.IdeaSelectorTypes
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

/*  Contains components related to creating a new idea dialog: SavingsDialogInputs, ExpenseLimitsDialogInputs, IncomePlanDialogInputs,
    IdeaInputField, NewIdeaCategoriesGrid  */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewIdeaDialog(newIdeaDialogViewModel : NewIdeaDialogViewModel) {
    val newIdeaDialogState = newIdeaDialogViewModel.newIdeaDialogState.collectAsState()
    val currenciesPreferenceRepositoryImpl = koinInject<CurrenciesPreferenceRepositoryImpl>()
    val coroutineScope = rememberCoroutineScope()
    val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().collectAsState(initial = CURRENCY_DEFAULT)
    Dialog(
        onDismissRequest = { newIdeaDialogViewModel.setIsNewIdeaDialogVisible(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
        ) {
            if (newIdeaDialogState.value.warningMessage != "") {
                Toast.makeText(LocalContext.current, newIdeaDialogState.value.warningMessage, Toast.LENGTH_SHORT).show()
                newIdeaDialogViewModel.setWarningMessage("")
            }
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.add_idea),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                val options = listOf(IdeaSelectorTypes.Savings, IdeaSelectorTypes.ExpenseLimit, IdeaSelectorTypes.IncomePlans)
                Row {
                    SingleChoiceSegmentedButtonRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 4.dp, end = 12.dp)
                    ) {
                        options.forEachIndexed { index, label ->
                            SegmentedButton(
                                modifier = Modifier.safeContentPadding(),
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                                onClick = { newIdeaDialogViewModel.setTypeSelected(label) },
                                selected = newIdeaDialogState.value.typeSelected == label
                            ) {
                                if (label == IdeaSelectorTypes.ExpenseLimit) {
                                    Text(
                                        text = stringResource(id = label.nameId),
                                        maxLines = 1,
                                        style = MaterialTheme.typography.labelSmall,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                } else {
                                    Text(
                                        modifier = Modifier.wrapContentWidth(),
                                        text = stringResource(id = label.nameId),
                                        maxLines = 1,
                                        style = MaterialTheme.typography.labelMedium,
                                        textAlign = TextAlign.Start
                                    )
                                }
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = when (newIdeaDialogState.value.typeSelected) {
                            IdeaSelectorTypes.ExpenseLimit -> stringResource(R.string.limit_planned)
                            IdeaSelectorTypes.IncomePlans -> stringResource(R.string.income_planned)
                            IdeaSelectorTypes.Savings -> stringResource(R.string.savings_planned)
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IdeaInputField(preferableCurrency = preferableCurrency.value)
                    Spacer(modifier = Modifier.weight(1f))
                }
                when (newIdeaDialogState.value.typeSelected) {
                    IdeaSelectorTypes.ExpenseLimit -> ExpenseLimitsDialogInputs(newIdeaDialogState = newIdeaDialogState.value)
                    IdeaSelectorTypes.IncomePlans -> IncomePlanDialogInputs(newIdeaDialogState = newIdeaDialogState.value)
                    IdeaSelectorTypes.Savings -> SavingsDialogInputs(newIdeaDialogState = newIdeaDialogState.value)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp), horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        modifier = Modifier.scale(0.8f),
                        onClick = { newIdeaDialogViewModel.setIsNewIdeaDialogVisible(false) }) {
                        Text(stringResource(id = R.string.decline))
                    }
                    FilledTonalButton(modifier = Modifier.scale(0.9f), onClick = {
                        coroutineScope.launch {
                            newIdeaDialogViewModel.addNewIdea()
                        }
                    }) {
                        Text(stringResource(id = R.string.add))
                    }
                }
            }
        }
    }
}