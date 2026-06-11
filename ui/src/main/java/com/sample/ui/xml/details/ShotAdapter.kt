package com.sample.ui.xml.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sample.domain.model.Shot

import com.sample.golfperformancetracker.ui.R
import com.sample.golfperformancetracker.ui.databinding.ItemShotBinding
class ShotAdapter : ListAdapter<Shot, ShotAdapter.ShotViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShotViewHolder {
        val binding = ItemShotBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ShotViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ShotViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class ShotViewHolder(
        private val binding: ItemShotBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(shot: Shot) {
            val context = binding.root.context

            binding.clubUsedTextView.text =
                context.getString(R.string.club_used, shot.clubUsed)

            binding.ballSpeedTextView.text =
                context.getString(R.string.ball_speed, shot.ballSpeed)

            binding.launchAngleTextView.text =
                context.getString(R.string.launch_angle, shot.launchAngle)

            binding.spinRateTextView.text =
                context.getString(R.string.spin_rate, shot.spinRate)

            binding.distanceTextView.text =
                context.getString(R.string.distance, shot.distance)
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Shot>() {
                override fun areItemsTheSame(
                    oldItem: Shot,
                    newItem: Shot
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Shot,
                    newItem: Shot
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
