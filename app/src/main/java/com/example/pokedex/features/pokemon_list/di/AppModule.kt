package com.example.pokedex.features.pokemon_list.di


import com.example.pokedex.features.pokemon_list.data.remote.PokeApi
import com.example.pokedex.features.pokemon_list.data.repository.PokemonRepository
import com.example.pokedex.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun  providePokeApi(): PokeApi {
        return Retrofit.Builder()
            .addConverterFactory(
                GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PokeApi::class.java)
    }


    fun provideNetworkService() {

    }



    fun providePokemonRepository(
        api: PokeApi
    ) = PokemonRepository(api)
}