package com.savenko.track.presentation.other.composableTypes.errors

import com.savenko.track.R

sealed class NewIdeaDialogErrors(val name: String, val error: Int) {
    data object IncorrectGoalValue : NewIdeaDialogErrors(name = "IncorrectGoalValue", error = R.string.incorrect_goal_value_newIdeaDialog_error)

    data object IncorrectSavingLabel : NewIdeaDialogErrors(name = "IncorrectSavingLabel", error = R.string.incorrect_saving_label_error)

    data object EmptyDate : NewIdeaDialogErrors(name = "EmptyDate", error = R.string.select_correct_date_error)

    data object MaxCategoriesSelected : NewIdeaDialogErrors(name = "MaxCategoriesSelected", error = R.string.you_have_already_selected_categories_error)

    data object SelectCategory : NewIdeaDialogErrors(name = "SelectCategory", error = R.string.select_category_new_idea_dialog_error)
}