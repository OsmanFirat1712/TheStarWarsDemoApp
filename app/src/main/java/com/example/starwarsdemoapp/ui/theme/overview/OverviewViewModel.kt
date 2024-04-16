package com.example.starwarsdemoapp.ui.theme.overview

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsdemoapp.data.StarWarsDataSource
import com.example.starwarsdemoapp.data.remote.model.Character
import com.example.starwarsdemoapp.data.remote.model.RepositoryResponse
import com.example.starwarsdemoapp.data.remote.model.StarWarsCharacterResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OverviewViewModel(val dataSource: StarWarsDataSource) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()
    val indexFlow = snapshotFlow { IndexState.index.value }
    var index = 1

    data class State(
        val characterList: List<Character> = emptyList(),
        val dataState: DataState = DataState.NONE,
        val shouldLoadData: Boolean = false,
    )

    init {

        viewModelScope.launch {
            indexFlow.debounce(400).onEach { it ->
                if (it) {
                    _state.update { state ->
                        state.copy(shouldLoadData = true)
                    }
                    index++
                    loadCharacters(index)
                } else {
                    _state.update { state ->
                        state.copy(shouldLoadData = false)
                    }
                }
            }.launchIn(this)
        }
        loadCharacters(1)
    }


    fun loadCharacters(index: Int) {
        viewModelScope.launch {
            when (_state.value.shouldLoadData) {
                true -> _state.update { it.copy(dataState = DataState.SUCCESS) }
                false -> _state.update { it.copy(dataState = DataState.LOADING) }
            }

            dataSource.getAllCharacters(page = index).let { response ->
                when (response) {
                    is RepositoryResponse.Error -> _state.update { it.copy(dataState = DataState.ERROR) }
                    is RepositoryResponse.Success -> _state.update {
                        if (_state.value.shouldLoadData) {
                            _state.getAndUpdate { existingItems ->
                                val newList = existingItems.characterList.plus(response.data)
                                State(newList)
                            }
                        }
                        when (it.characterList.isEmpty()) {
                            true -> it.copy(
                                characterList = response.data,
                                dataState = DataState.SUCCESS,
                            )
                            false -> it.copy(dataState = DataState.SUCCESS)

                        }
                    }
                }
            }
        }
    }
}


enum class DataState {
    SUCCESS, LOADING, ERROR, EMPTY, NONE
}