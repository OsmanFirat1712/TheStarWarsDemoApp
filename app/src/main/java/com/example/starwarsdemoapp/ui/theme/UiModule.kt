package com.example.starwarsdemoapp.ui.theme

import com.example.starwarsdemoapp.ui.theme.overview.OverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val uiModule = module {
    // Provides an instance of OverviewViewModel, injecting the required dataSource dependency.
    // The viewModel method from Koin is used to declare a ViewModel dependency,
    // ensuring that Koin handles its lifecycle appropriately.

    viewModel { OverviewViewModel(dataSource = get()) }
}