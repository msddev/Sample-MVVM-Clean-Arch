package com.mkdev.zerotohero.presentation.viewmodel

import androidx.lifecycle.LiveData
import com.mkdev.zerotohero.domain.models.CharacterDetailUIModel
import com.mkdev.zerotohero.presentation.utils.CoroutineContextProvider
import com.mkdev.zerotohero.presentation.utils.UiAwareLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    contextProvider: CoroutineContextProvider
) : BaseViewModel(contextProvider) {

    private val _characterDetail = UiAwareLiveData<CharacterDetailUIModel>()
    val characterDetail: LiveData<CharacterDetailUIModel> = _characterDetail

    override val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        _characterDetail.postValue(CharacterDetailUIModel.Error(exception.message ?: "Error"))
    }
}