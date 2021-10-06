package com.mkdev.zerotohero.domain.models

sealed class CharacterDetailUIModel : UiAwareModel() {
    object Loading : CharacterDetailUIModel()
    data class Success(val data: Character) : CharacterDetailUIModel()
    data class Error(var error: String) : CharacterDetailUIModel()
    data class BookmarkStatus(val bookmark: Bookmark, val status: Boolean) :
        CharacterDetailUIModel()
}

enum class Bookmark {
    BOOKMARK,
    UN_BOOKMARK
}
