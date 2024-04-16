package com.example.starwarsdemoapp.data.remote.api

import com.example.starwarsdemoapp.data.remote.model.StarWarsCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StarWarsApi {

    @GET("people/")
    suspend fun getAllCharacters(@Query("page") page: Int):StarWarsCharacterResponse
}