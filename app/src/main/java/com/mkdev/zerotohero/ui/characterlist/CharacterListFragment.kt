package com.mkdev.zerotohero.ui.characterlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.mkdev.zerotohero.base.BaseFragment
import com.mkdev.zerotohero.databinding.FragmentCharacterListBinding
import com.mkdev.zerotohero.presentation.viewmodel.CharacterListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : BaseFragment<FragmentCharacterListBinding, CharacterListViewModel>() {

    override val viewModel: CharacterListViewModel by viewModels()

    override fun getViewBinding(): FragmentCharacterListBinding =
        FragmentCharacterListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}