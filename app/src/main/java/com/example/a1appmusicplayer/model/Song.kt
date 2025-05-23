package com.example.a1appmusicplayer.model

import androidx.recyclerview.widget.DiffUtil

data class Song(
    val id: String,
    val name: String,
    val author: String,
    val duration: Long
)

class SongItemCallback : DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
        return oldItem.name == newItem.name
    }
}