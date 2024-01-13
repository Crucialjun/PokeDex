package com.example.pokedex.di

import com.example.pokedex.data.remote.PokeApi
import com.example.pokedex.data.repository.PokemonRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    fun  providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create()
            .baseUrl("https://pokeapi.co/api/v2/")
            .build()
            .create(PokeApi::class.java)
    }

    fun provideNetworkService() {

    }

    fun providePokemonRepository(
        api:PokeApi
    ) = PokemonRepository(api)
}