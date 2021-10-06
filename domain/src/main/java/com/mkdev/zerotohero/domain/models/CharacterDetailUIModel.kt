package com.mkdev.zerotohero.domain.models

sealed class CharacterDetailUIModel : UiAwareModel() {
    object Loading : CharacterDetailUIModel()
    data class Success(val data: Character) : CharacterDetailUIModel()
    data class Error(var error: String) : CharacterDetailUIModel()
}
