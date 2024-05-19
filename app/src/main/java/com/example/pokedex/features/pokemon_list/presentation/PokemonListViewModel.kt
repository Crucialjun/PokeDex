package com.example.pokedex.features.pokemon_list.presentation

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.Color

import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.palette.graphics.Palette
import com.example.pokedex.features.pokemon_list.data.repository.PokemonRepository


import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel(){

    fun calcDominantColor(drawable: Drawable, onFinish: (Color) -> Unit){
        val bitmap = drawable.toBitmap().copy(android.graphics.Bitmap.Config.ARGB_8888, true)

        Palette.from(bitmap).generate{ palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))

            }
        }


    }
}