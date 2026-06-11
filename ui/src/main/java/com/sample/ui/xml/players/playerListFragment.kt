package com.sample.ui.xml.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.sample.golfperformancetracker.ui.R
import com.sample.golfperformancetracker.ui.databinding.FragmentPlayerListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayerListFragment : Fragment() {

    private var _binding: FragmentPlayerListBinding? = null
    private val binding get() = _binding!!

    private lateinit var toolbar: Toolbar

    private val viewModel: PlayerListViewModel by viewModels()

    private val playerAdapter = PlayerPagingAdapter { player ->
        findNavController().navigate(
            R.id.action_playerListFragment_to_playerDetailFragment,
            bundleOf(
                "playerId" to player.id
            )
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerListBinding.inflate(
            inflater,
            container,
            false
        )
        binding.toolbar.title = getString(R.string.app_bar_title)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.playerRecyclerView.adapter = playerAdapter

        binding.searchEditText.doAfterTextChanged { text ->
            viewModel.searchPlayers(
                text?.toString().orEmpty()
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    binding.progressBar.isVisible = state.isLoading
                    binding.errorTextView.isVisible = state.error != null
                    binding.errorTextView.text = state.error

                    playerAdapter.submitData(
                        PagingData.from(state.players)
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}