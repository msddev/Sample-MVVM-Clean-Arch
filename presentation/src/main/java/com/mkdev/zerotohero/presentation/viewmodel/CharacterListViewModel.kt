package com.mkdev.zerotohero.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.mkdev.zerotohero.domain.interactor.GetBookMarkedCharacterListUseCase
import com.mkdev.zerotohero.domain.interactor.GetCharacterListUseCase
import com.mkdev.zerotohero.domain.models.CharacterUIModel
import com.mkdev.zerotohero.presentation.utils.CoroutineContextProvider
import com.mkdev.zerotohero.presentation.utils.UiAwareLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider,
    private val characterListUseCase: GetCharacterListUseCase,
    private val bookMarkedCharacterListUseCase: GetBookMarkedCharacterListUseCase
) : BaseViewModel(contextProvider) {

    private val _characterList = UiAwareLiveData<CharacterUIModel>()
    val characterList: LiveData<CharacterUIModel> = _characterList

    override val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            _characterList.postValue(CharacterUIModel.Error(exception.message ?: "Error"))
        }

    fun getCharacters(isFavorite: Boolean) {
        _characterList.postValue(CharacterUIModel.Loading)
        launchCoroutineIO {
            if (isFavorite) {
                loadFavoriteCharacters()
            } else {
                loadCharacters()
            }
        }
    }

    private suspend fun loadCharacters() {
        characterListUseCase(Unit).collect {
            _characterList.postValue(CharacterUIModel.Success(it))
        }
    }

    private suspend fun loadFavoriteCharacters() {
        bookMarkedCharacterListUseCase(Unit).collect {
            _characterList.postValue(CharacterUIModel.Success(it))
        }
    }
}