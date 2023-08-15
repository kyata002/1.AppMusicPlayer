package com.example.a1appmusicplayer.model

object TimeUtil {
    fun timeMillisToTime(duration: Long): String {
        val minutes = duration / 60000
        val seconds = duration % 60000 / 1000
        return String.format("%02d:%02d", minutes, seconds)
    }
}