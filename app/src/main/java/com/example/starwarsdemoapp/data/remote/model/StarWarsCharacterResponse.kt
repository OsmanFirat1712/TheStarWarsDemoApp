package com.example.starwarsdemoapp.data.remote.model

data class StarWarsCharacterResponse(
    val count: Int,
    val next: String,
    val results:List<Character>
)

data class Character(
    val name: String,
    val height: String,
)