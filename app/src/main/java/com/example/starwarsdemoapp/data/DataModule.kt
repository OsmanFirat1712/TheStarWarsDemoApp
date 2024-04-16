package com.example.starwarsdemoapp.data

import com.example.starwarsdemoapp.data.remote.StarWarsRemoteDataSourceImpl
import com.example.starwarsdemoapp.data.remote.remoteModule
import com.google.gson.Gson
import org.koin.core.scope.get
import org.koin.dsl.module

/**
 * Defines a Koin module for setting up the data layer of the application.
 * This module configures and provides various data components using Koin.
 */

internal val dataModule = module {
    single { Gson() }

    // Provides an implementation of GitHubDataSource, injecting the remote data source dependency.
    single<StarWarsDataSource> {
        StarWarsDataSourceImpl(
            remoteDataSource = get()
        )
    }
}

/**
 * Combines multiple Koin modules into a single module.
 * This is useful for including all necessary modules for the data layer in one place.
 *
 * In this instance, it combines 'dataModule' with 'remoteModule'.
 */

internal val dataModules = dataModule + remoteModule