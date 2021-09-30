package com.mkdev.zerotohero.domain.models

sealed class CharacterUIModel :UiAwareModel(){
    object Loading : CharacterUIModel()
    data class Success(val data: List<Character>) : CharacterUIModel()
    data class Error(var error: String) : CharacterUIModel()
}
