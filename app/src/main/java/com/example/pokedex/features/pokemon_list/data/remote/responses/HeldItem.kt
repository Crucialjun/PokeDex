package com.example.pokedex.features.pokemon_list.data.remote.responses

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)