package com.savenko.track.shared.presentation.other.composableTypes.errors

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import org.jetbrains.compose.resources.StringResource


sealed class NewIdeaDialogErrors(val name: String, val error: StringResource) {
    data object IncorrectGoalValue : NewIdeaDialogErrors(name = "IncorrectGoalValue", error = Res.string.incorrect_goal_value_newIdeaDialog_error)

    data object IncorrectSavingLabel : NewIdeaDialogErrors(name = "IncorrectSavingLabel", error = Res.string.incorrect_saving_label_error)

    data object EmptyDate : NewIdeaDialogErrors(name = "EmptyDate", error = Res.string.select_correct_date_error)

    data object MaxCategoriesSelected : NewIdeaDialogErrors(name = "MaxCategoriesSelected", error = Res.string.you_have_already_selected_categories_error)

    data object SelectCategory : NewIdeaDialogErrors(name = "SelectCategory", error = Res.string.select_category_new_idea_dialog_error)
}
