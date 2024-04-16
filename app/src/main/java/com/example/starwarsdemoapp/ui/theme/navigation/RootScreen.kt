package com.example.starwarsdemoapp.ui.theme.navigation

import androidx.compose.runtime.Composable
import com.example.starwarsdemoapp.ui.theme.overview.NavGraphs
import com.ramcosta.composedestinations.DestinationsNavHost

@Composable
fun RootScreen() {
    DestinationsNavHost(navGraph = NavGraphs.root)
}