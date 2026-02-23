package com.savenko.track.shared

import com.savenko.track.shared.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.shared.data.viewmodels.common.TrackScreenManagerViewModel
import com.savenko.track.shared.data.viewmodels.login.LoginScreenViewModel
import com.savenko.track.shared.data.viewmodels.mainScreen.feed.AddToSavingIdeaDialogViewModel
import com.savenko.track.shared.data.viewmodels.mainScreen.feed.BudgetIdeaCardViewModel
import com.savenko.track.shared.data.viewmodels.mainScreen.feed.NewIdeaDialogViewModel
import com.savenko.track.shared.data.viewmodels.mainScreen.feed.TrackScreenFeedViewModel
import com.savenko.track.shared.data.viewmodels.mainScreen.feedCards.TrackScreenInfoCardsViewModel
import com.savenko.track.shared.data.viewmodels.mainScreen.lazyColumn.FinancialsLazyColumnViewModel
import com.savenko.track.shared.data.viewmodels.settingsScreen.additionalPreferences.AdditionalPreferencesSettingsViewModel
import com.savenko.track.shared.data.viewmodels.settingsScreen.category.CategoriesSettingsScreenViewModel
import com.savenko.track.shared.data.viewmodels.settingsScreen.category.NewCategoryViewModel
import com.savenko.track.shared.data.viewmodels.settingsScreen.currencies.CurrenciesSettingsViewModel
import com.savenko.track.shared.data.viewmodels.settingsScreen.ideas.IdeasSettingsScreenViewModel
import com.savenko.track.shared.data.viewmodels.settingsScreen.personal.PersonalSettingsScreenViewmodel
import com.savenko.track.shared.data.viewmodels.settingsScreen.personal.PersonalStatsViewModel
import com.savenko.track.shared.data.viewmodels.settingsScreen.themePreferences.ThemePreferenceSettingsViewModel
import com.savenko.track.shared.data.viewmodels.statistics.StatisticChartViewModel
import com.savenko.track.shared.data.viewmodels.statistics.StatisticInfoCardsViewModel
import com.savenko.track.shared.data.viewmodels.statistics.StatisticLazyColumnViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

actual val viewModelModule = module {
    // Login related
    viewModel { LoginScreenViewModel(get(), get(), get(), get()) }

    // Settings related
    viewModel { CurrenciesSettingsViewModel(get(), get(), get(), get(), get()) }
    viewModel { IdeasSettingsScreenViewModel(get(), get(), get()) }
    viewModel { ThemePreferenceSettingsViewModel(get(), get()) }
    viewModel { CategoriesSettingsScreenViewModel(get(), get(), get(), get()) }
    viewModel { NewCategoryViewModel(get(), get(), get()) }
    viewModel { PersonalSettingsScreenViewmodel(get(), get(), get()) }
    viewModel { PersonalStatsViewModel(get(), get()) }
    viewModel { AdditionalPreferencesSettingsViewModel(get(), get(), get(), get()) }

    // Track main screen related
    viewModel { FinancialsLazyColumnViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }

    // Statistics related
    viewModel { StatisticChartViewModel(get(), get(), get()) }
    viewModel { StatisticLazyColumnViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { StatisticInfoCardsViewModel(get(), get()) }

    // Bottom sheets
    viewModel { BottomSheetViewModel(get(), get(), get(), get(), get(), get()) }

    // Feed related
    viewModel { TrackScreenFeedViewModel(get(), get(), get()) }
    viewModel { TrackScreenInfoCardsViewModel(get(), get(), get()) }

    // Ideas related
    viewModel { BudgetIdeaCardViewModel(get(), get()) }

    // Dialogs related
    viewModel { AddToSavingIdeaDialogViewModel(get(), get(), get()) }
    viewModel { NewIdeaDialogViewModel(get()) }

    // Other
    viewModel { TrackScreenManagerViewModel(get()) }
}
