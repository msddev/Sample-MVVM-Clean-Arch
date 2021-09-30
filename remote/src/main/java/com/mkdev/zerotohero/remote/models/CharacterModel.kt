package com.mkdev.zerotohero.remote.models

import com.google.gson.annotations.SerializedName

data class CharacterModel(
    val created: String,
    val gender: String,
    val id: Int,
    val image: String,
    @SerializedName("location")
    val characterLocation: CharacterLocationModel,
    val name: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    var isBookMarked: Boolean = false
)

data class CharacterLocationModel(
    val name: String,
    val url: String
)
