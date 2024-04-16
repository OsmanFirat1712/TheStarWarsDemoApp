package com.example.starwarsdemoapp.data.remote

import com.example.starwarsdemoapp.data.remote.api.StarWarsApi
import com.example.starwarsdemoapp.data.remote.model.StarWarsCharacterResponse

interface StarWarsRemoteDataSource { suspend fun getAllCharacters(page:Int): NetworkResponse<StarWarsCharacterResponse> }


class StarWarsRemoteDataSourceImpl(private val api: StarWarsApi) : StarWarsRemoteDataSource {

    override suspend fun getAllCharacters(page:Int) = safeApiCall { api.getAllCharacters(page) }

}
