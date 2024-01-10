package com.example.pokedex.data.remote

import com.example.pokedex.data.remote.responses.PokemonList

interface PokeApi {
    suspend fun getPokemonList(
        limit: Int,
        offset: Int
    ) : PokemonList
}