package com.example.services

import com.example.model.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET

interface WebService {
    @GET("pokedex.json")

    suspend fun getPokemons(): Response<PokemonResponse>
}