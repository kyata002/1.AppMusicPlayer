package com.example.a1appmusicplayer.songList

import com.example.a1appmusicplayer.BaseView
import com.example.a1appmusicplayer.model.Song

interface SongListView : BaseView {
    fun showLoading()

    fun stopLoading()

    fun updateSongState(song: Song, isPlaying: Boolean)

    fun onSongClick()
}