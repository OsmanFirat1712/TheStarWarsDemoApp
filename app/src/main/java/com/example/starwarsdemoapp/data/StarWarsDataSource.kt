package com.example.starwarsdemoapp.data

import com.example.starwarsdemoapp.data.remote.NetworkResponse
import com.example.starwarsdemoapp.data.remote.StarWarsRemoteDataSource
import com.example.starwarsdemoapp.data.remote.model.Character
import com.example.starwarsdemoapp.data.remote.model.RepositoryException
import com.example.starwarsdemoapp.data.remote.model.RepositoryResponse
import com.example.starwarsdemoapp.data.remote.model.StarWarsCharacterResponse

interface StarWarsDataSource {
    /**
     * Fetches a list of characters from the data source.
     *
     * @return A RepositoryResponse which can be a Success with a list of characters objects or an Error.
     */
    suspend fun getAllCharacters(page: Int): RepositoryResponse<List<Character>>
}


/**
 * Implementation of StarWarsDataSource that retrieves beer data from a remote source.
 *
 * @property remoteDataSource The remote data source to fetch character data.
 */
class StarWarsDataSourceImpl(private val remoteDataSource: StarWarsRemoteDataSource) : StarWarsDataSource {

    override suspend fun getAllCharacters(page:Int): RepositoryResponse<List<Character>> =
        remoteDataSource.getAllCharacters(page).let { networkResponse ->
            when (networkResponse) {
                // In case of network error, wrap the throwable in a RepositoryException and return.
                is NetworkResponse.Error -> RepositoryResponse.Error(
                    RepositoryException.RemoteException(
                        networkResponse.throwable
                    )
                )

                // On success, map the data to Beer domain objects and return.
                is NetworkResponse.Success -> {
                    RepositoryResponse.Success(
                        networkResponse.data.results.map{
                          Character(
                              name = it.name,
                              height = it.height
                          )
                        }
                    )
                }
            }
        }
}
