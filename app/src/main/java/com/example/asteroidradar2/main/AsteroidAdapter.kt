package com.example.asteroidradar2.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.asteroidradar2.R
import com.example.asteroidradar2.databinding.AsteroidListItemBinding
import com.example.asteroidradar2.domain.Asteroid

class AsteroidAdapter (val onClickListener: OnClickListener): ListAdapter<Asteroid,AsteroidAdapter.AsteroidViewHolder>(DiffCallback) {

    class AsteroidViewHolder(var binding:AsteroidListItemBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(asteroid: Asteroid){
            binding.asteroid=asteroid
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        var inflater= LayoutInflater.from(parent.context)
        var view= AsteroidListItemBinding.inflate(inflater, parent, false)
        return AsteroidViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        var asteroid= getItem(position)
        holder.itemView.setOnClickListener{
            onClickListener.onClick(asteroid)
        }
        holder.bind(asteroid)

    }


    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }

    }


    class OnClickListener(val clickListener: (asteroid:Asteroid) -> Unit){
        fun onClick(asteroid: Asteroid)= clickListener(asteroid)
    }

}
