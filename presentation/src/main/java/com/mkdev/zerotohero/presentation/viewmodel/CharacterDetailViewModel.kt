package com.mkdev.zerotohero.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.mkdev.zerotohero.domain.interactor.GetCharacterByIdUseCase
import com.mkdev.zerotohero.domain.interactor.SetCharacterBookmarkUseCase
import com.mkdev.zerotohero.domain.interactor.SetCharacterUnBookmarkUseCase
import com.mkdev.zerotohero.domain.models.Bookmark
import com.mkdev.zerotohero.domain.models.CharacterDetailUIModel
import com.mkdev.zerotohero.presentation.utils.CoroutineContextProvider
import com.mkdev.zerotohero.presentation.utils.UiAwareLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val characterByIdUseCase: GetCharacterByIdUseCase,
    private val setCharacterBookmarkUseCase: SetCharacterBookmarkUseCase,
    private val setCharaUnBookmarkUseCase: SetCharacterUnBookmarkUseCase
) : BaseViewModel(contextProvider) {

    private val _characterDetail = UiAwareLiveData<CharacterDetailUIModel>()
    val characterDetail: LiveData<CharacterDetailUIModel> = _characterDetail

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _characterDetail.postValue(CharacterDetailUIModel.Error(exception.message ?: "Error"))
    }

    fun getCharacterDetail(characterId: Long) {
        launchCoroutineIO {
            loadCharacterDetail(characterId)
        }
    }

    private suspend fun loadCharacterDetail(characterId: Long) {
        _characterDetail.postValue(CharacterDetailUIModel.Loading)
        characterByIdUseCase(characterId).collect {
            _characterDetail.postValue(CharacterDetailUIModel.Success(it))
        }
    }

    fun setBookmarkCharacter(characterId: Long) {
        launchCoroutineIO {
            setCharacterBookmarkUseCase(characterId).collect {
                if (it == 1) {
                    _characterDetail.postValue(
                        CharacterDetailUIModel.BookmarkStatus(
                            Bookmark.BOOKMARK,
                            true
                        )
                    )
                } else {
                    _characterDetail.postValue(
                        CharacterDetailUIModel.BookmarkStatus(
                            Bookmark.BOOKMARK,
                            false
                        )
                    )
                }
            }
        }
    }

    fun setUnBookmarkCharacter(characterId: Long) {
        launchCoroutineIO {
            setCharaUnBookmarkUseCase(characterId).collect {
                if (it == 1) {
                    _characterDetail.postValue(
                        CharacterDetailUIModel.BookmarkStatus(
                            Bookmark.UN_BOOKMARK,
                            true
                        )
                    )
                } else {
                    _characterDetail.postValue(
                        CharacterDetailUIModel.BookmarkStatus(
                            Bookmark.UN_BOOKMARK,
                            false
                        )
                    )
                }
            }
        }
    }
}