package com.example.a1appmusicplayer.playSong

import com.example.a1appmusicplayer.BaseView
import com.example.a1appmusicplayer.model.Song

interface PlaySongView : BaseView {
    fun updateSongState(song: Song, isPlaying: Boolean, progress: Int)

    fun showRepeat(isRepeat: Boolean)

    fun showRandom(isRandom: Boolean)
}