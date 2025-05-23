package com.example.a1appmusicplayer.songList

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Patterns
import android.util.SparseArray
import com.example.a1appmusicplayer.BasePresenter
import com.example.a1appmusicplayer.R
import com.example.a1appmusicplayer.model.Song
import com.example.a1appmusicplayer.player.PlayerService
import kotlinx.coroutines.*

class SongListPresenter constructor(view: SongListView) : BasePresenter<SongListView>(view) {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job())

    private lateinit var player: PlayerService

    private lateinit var adapter: SongListAdapter
    private val filteredSongList: SparseArray<Song> = SparseArray()

    fun setPlayerManager(player: PlayerService) {
        this.player = player

        loadSongList()
    }

    fun setAdapter(adapter: SongListAdapter) {
        this.adapter = adapter
    }

    fun fetchSongState() {
        player.getSong()?.let {
            view.updateSongState(it, player.isPlaying())
        }
    }

    fun filterSong(key: String) {
        scope.launch {
            filteredSongList.clear()

            val list = mutableListOf<Song>()
            player.getSongList().forEachIndexed { index, song ->
                if (song.name.contains(key, true) || song.author.contains(key, true)) {
                    filteredSongList.put(index, song)
                    list.add(song)
                }
            }

            withContext(Dispatchers.Main) {
                adapter.submitList(list)
            }
        }
    }

    fun onSongPlay() {
        if (!player.isPlaying()) {
            player.play()
        } else {
            player.pause()
        }
    }

    fun onSongClick(index: Int) {
        view.onSongClick()

        val position = filteredSongList.keyAt(index)
        playSong(position)
    }

    fun downloadSong(url: String) {
        if (Patterns.WEB_URL.matcher(url).matches() && isSupport(url)) {
            val uri: Uri = Uri.parse(url)

            val downloadManager: DownloadManager =
                view.context().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

            val request: DownloadManager.Request = DownloadManager.Request(uri)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_MUSIC,
                uri.lastPathSegment
            )
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            downloadManager.enqueue(request)
        } else {
            view.showToast(view.context().getString(R.string.unsupported_format))
        }
    }

    private fun loadSongList() {
        scope.launch {
            view.showLoading()

            player.readSong()
            player.getSongList().forEachIndexed { index, song -> filteredSongList.put(index, song) }

            withContext(Dispatchers.Main) {
                view.stopLoading()
                adapter.submitList(player.getSongList())
                fetchSongState()
            }
        }
    }

    private fun playSong(position: Int) {
        player.play(position)
    }

    private fun isSupport(extension: String): Boolean {
        return extension.endsWith("mp3") || extension.endsWith("wav")
                || extension.endsWith("ogg") || extension.endsWith("flac")
    }
}