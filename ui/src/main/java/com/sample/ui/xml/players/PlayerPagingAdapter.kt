package com.sample.ui.xml.players

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.sample.domain.model.Player
import com.sample.golfperformancetracker.ui.R
import com.sample.golfperformancetracker.ui.databinding.ItemPlayerBinding

class PlayerPagingAdapter(
    private val onPlayerClick: (Player) -> Unit
) : PagingDataAdapter<Player, PlayerPagingAdapter.PlayerViewHolder>(
    PLAYER_DIFF_CALLBACK
) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlayerViewHolder {

        val binding = ItemPlayerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PlayerViewHolder,
        position: Int
    ) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    inner class PlayerViewHolder(
        private val binding: ItemPlayerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private var expanded = false

        fun bind(player: Player) {

            binding.player = player

            binding.playerClubTextView.text =
                binding.root.context.getString(
                    R.string.club,
                    player.club
                )

            binding.playerAverageSpeedTextView.text =
                binding.root.context.getString(
                    R.string.average_speed,
                    player.averageSpeed
                )

            binding.playerAverageDistanceTextView.text =
                binding.root.context.getString(
                    R.string.average_distance,
                    player.averageDistance
                )


            binding.playerCard.setOnClickListener {
                expanded = !expanded

                TransitionManager.beginDelayedTransition(
                    binding.playerCard,
                    AutoTransition()
                )

                binding.detailsContainer.isVisible = expanded
            }

            binding.viewDetailsButton.setOnClickListener {
                onPlayerClick(player)
            }

            binding.executePendingBindings()
        }
    }

    companion object {

        private val PLAYER_DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Player>() {

                override fun areItemsTheSame(
                    oldItem: Player,
                    newItem: Player
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Player,
                    newItem: Player
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}