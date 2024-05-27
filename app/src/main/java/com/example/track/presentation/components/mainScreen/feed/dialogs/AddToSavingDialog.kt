package com.example.track.presentation.components.mainScreen.feed.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.track.data.viewmodels.mainScreen.AddToSavingIdeaDialogViewModel


@Composable
fun AddToSavingDialog(addToSavingIdeaDialogViewModel: AddToSavingIdeaDialogViewModel) {
    Dialog(
        onDismissRequest = { addToSavingIdeaDialogViewModel.setCurrentSaving(null) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.96f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
        ) {

        }
    }


}
