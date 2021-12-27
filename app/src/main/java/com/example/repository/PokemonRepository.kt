package com.example.repository

import com.example.services.RetrofitClient
import com.example.services.WebService

class PokemonRepository {
    private var apiService: WebService? = null

    init {
        apiService = RetrofitClient.getClient?.create(WebService::class.java)
    }

    suspend fun getPokemon() = apiService?.getPokemons()
}