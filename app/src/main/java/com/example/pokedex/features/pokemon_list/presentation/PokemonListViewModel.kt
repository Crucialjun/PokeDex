package com.example.pokedex.features.pokemon_list.presentation

import android.graphics.drawable.Drawable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.example.pokedex.features.pokemon_list.data.models.PokedexListEntry
import com.example.pokedex.features.pokemon_list.data.repository.PokemonRepository
import com.example.pokedex.util.Constants.PAGE_SIZE
import com.example.pokedex.util.Resource


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel(){

    private var currrentPage = 0

    var pokemonList = mutableStateOf<List<PokedexListEntry>>(listOf())

    var loadError = mutableStateOf("")

    var isLoading = mutableStateOf(false)

    var endReached = mutableStateOf(false)

    init {
        loadPokemonPaginated()
    }

    fun loadPokemonPaginated(){
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getPokemonList(PAGE_SIZE, currrentPage * PAGE_SIZE)

            when(result){
                is Resource.Success -> {
                    endReached.value = currrentPage * 20 >= result.data!!.count
                    val pokedexEntries = result.data.results.mapIndexed { index, entry ->
                        val number = if(entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }
                        val url = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        PokedexListEntry(entry.name.capitalize(), url, number.toInt())
                    }
                    currrentPage++
                    loadError.value = ""
                    isLoading.value = false
                    pokemonList.value += pokedexEntries
                }
                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }

                is Resource.Loading -> TODO()
            }
        }
    }

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit){
        val bitmap = drawable.toBitmap().copy(android.graphics.Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap).generate{ palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))

            }
        }


    }
}