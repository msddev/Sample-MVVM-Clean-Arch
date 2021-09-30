package com.mkdev.zerotohero.ui.characterlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mkdev.zerotohero.R
import com.mkdev.zerotohero.base.BaseFragment
import com.mkdev.zerotohero.databinding.FragmentCharacterListBinding
import com.mkdev.zerotohero.domain.models.CharacterUIModel
import com.mkdev.zerotohero.extension.observe
import com.mkdev.zerotohero.presentation.viewmodel.CharacterListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterListFragment : BaseFragment<FragmentCharacterListBinding, CharacterListViewModel>() {

    @Inject
    lateinit var characterAdapter: CharacterAdapter

    override val viewModel: CharacterListViewModel by viewModels()

    override fun getViewBinding(): FragmentCharacterListBinding =
        FragmentCharacterListBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isFavorite =
            (findNavController().currentDestination?.label == getString(R.string.menu_favorites))
        viewModel.getCharacters(isFavorite)
        observe(viewModel.characterList, ::onViewStateChange)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCharacters.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        characterAdapter.setItemClickListener { character ->
            findNavController().navigate(
                CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(
                    character.id.toLong()
                )
            )
        }
    }

    private fun onViewStateChange(event: CharacterUIModel) {
        if (event.isRedelivered) return
        when (event) {
            is CharacterUIModel.Loading -> {
                handleLoading(true)
            }
            is CharacterUIModel.Success -> {
                handleLoading(false)
                characterAdapter.list = event.data
            }
            is CharacterUIModel.Error -> {
                handleErrorMessage(event.error)
            }
        }
    }
}