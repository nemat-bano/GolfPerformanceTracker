package com.sample.ui.xml.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.sample.golfperformancetracker.ui.R
import com.sample.golfperformancetracker.ui.databinding.FragmentPlayerDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class PlayerDetailFragment : Fragment() {

    private var _binding: FragmentPlayerDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlayerDetailViewModel by viewModels()

    private val shotAdapter = ShotAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayerDetailBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val playerId = requireArguments().getString("playerId").orEmpty()

        setupToolbar()
        setupRecyclerView()
        observeUiState()

        viewModel.loadPlayer(playerId)
    }

    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.player_detail)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        binding.shotsRecyclerView.adapter = shotAdapter
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->

                    binding.progressBar.isVisible = state.isLoading

                    binding.errorTextView.isVisible = state.error != null
                    binding.errorTextView.text = state.error

                    val player = state.player

                    if (player != null) {
                        binding.playerNameTextView.text = player.name

                        binding.playerClubTextView.text =
                            getString(R.string.club, player.club)

                        binding.playerAverageSpeedTextView.text =
                            getString(R.string.average_speed, player.averageSpeed)

                        binding.playerAverageDistanceTextView.text =
                            getString(R.string.average_distance, player.averageDistance)
                    }

                    binding.noShotsTextView.isVisible =
                        state.shots.isEmpty() && !state.isLoading

                    binding.shotsTitleTextView.isVisible =
                        state.shots.isNotEmpty()

                    shotAdapter.submitList(state.shots)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
