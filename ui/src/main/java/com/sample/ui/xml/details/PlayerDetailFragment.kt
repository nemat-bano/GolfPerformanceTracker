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
import com.bumptech.glide.Glide
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

    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.player_detail)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val playerId = requireArguments().getString("playerId").orEmpty()
        setupToolbar()

        binding.shotsRecyclerView.adapter = shotAdapter

        viewModel.loadPlayer(playerId)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.player.collect { player ->
                        if (player != null) {
                            binding.playerNameTextView.text = player.name
                            binding.playerClubTextView.text = getString(R.string.club, player.club)
                            binding.playerAverageSpeedTextView.text =
                                getString(R.string.average_speed, player.averageSpeed)
                            binding.playerAverageDistanceTextView.text =
                                getString(R.string.average_distance, player.averageDistance)
                            binding.speedMetricView.setMetric(
                                label = getString(R.string.average_speed_label),
                                valueText = getString(R.string.average_speed_value, player.averageSpeed),
                                progress = (player.averageSpeed / 200.0).toFloat()
                            )

                            binding.distanceMetricView.setMetric(
                                label = getString(R.string.average_distance_label),
                                valueText = getString(R.string.average_distance_value, player.averageDistance),
                                progress = (player.averageDistance / 400.0).toFloat()
                            )
                            Glide.with(this@PlayerDetailFragment)
                                .load(player.imageUrl)
                                .placeholder(R.drawable.ic_person)
                                .error(R.drawable.ic_person)
                                .into(binding.playerImageView)
                        }
                    }
                }

                launch {
                    viewModel.shots.collect { shots ->
                        binding.shotsTitleTextView.isVisible = shots.isNotEmpty()
                        binding.noShotsTextView.isVisible = shots.isEmpty()
                        shotAdapter.submitList(shots)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
