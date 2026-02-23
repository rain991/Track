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
import org.koin.dsl.module

actual val viewModelModule = module {
// Login related
    single { LoginScreenViewModel(get(), get(), get(), get()) }

    // Settings related
    single { CurrenciesSettingsViewModel(get(), get(), get(), get(), get()) }
    single { IdeasSettingsScreenViewModel(get(), get(), get()) }
    single { ThemePreferenceSettingsViewModel(get(), get()) }
    single { CategoriesSettingsScreenViewModel(get(), get(), get(), get()) }
    single { NewCategoryViewModel(get(), get(), get()) }
    single { PersonalSettingsScreenViewmodel(get(), get(), get()) }
    single { PersonalStatsViewModel(get(), get()) }
    single { AdditionalPreferencesSettingsViewModel(get(), get(), get(), get()) }

    // Track main screen related
    single { FinancialsLazyColumnViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }

    // Statistics related
    single { StatisticChartViewModel(get(), get(), get()) }
    single { StatisticLazyColumnViewModel(get(), get(), get(), get(), get(), get()) }
    single { StatisticInfoCardsViewModel(get(), get()) }

    // Bottom sheets
    single { BottomSheetViewModel(get(), get(), get(), get(), get(), get()) }

    // Feed related
    single { TrackScreenFeedViewModel(get(), get(), get()) }
    single { TrackScreenInfoCardsViewModel(get(), get(), get()) }

    // Ideas related
    single { BudgetIdeaCardViewModel(get(), get()) }

    // Dialogs related
    single { AddToSavingIdeaDialogViewModel(get(), get(), get()) }
    single { NewIdeaDialogViewModel(get()) }

    // Other
    single { TrackScreenManagerViewModel(get()) }
}