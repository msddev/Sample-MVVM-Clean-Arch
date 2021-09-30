package com.mkdev.zerotohero.remote.api

import com.mkdev.zerotohero.remote.models.CharacterResponseModel
import com.mkdev.zerotohero.remote.models.CharacterModel
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {

    @GET("character")
    suspend fun getCharacters(): CharacterResponseModel

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Long): CharacterModel
}
