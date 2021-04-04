package com.diego.android.kitchen.exoplayertoy

import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util.SDK_INT
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var player: SimpleExoPlayer
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Our player can hog a lot of resources including memory, CPU, network connections and
     * hardware codecs. Many of these resources are in short supply, particularly for hardware
     * codecs where there may only be one. It's important that you release those resources for
     * other apps to use when you're not using them, such as when your app is put
     * into the background.
     *
     * Put another way, your player's lifecycle should be tied to the lifecycle of your app.
     * To implement this, you need to override the four
     * methods of PlayerActivity: onStart, onResume, onPause, and onStop.
     *
     * Initialize the player in the onStart or onResume callback depending on the API level.
     */
    override fun onStart() {
        super.onStart()
        if (SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUI()
        if (SDK_INT < 24) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun initializePlayer() {
        // Create an instance of the simple Exoplayer and bind it to the view where the video
        // will be played.
        player = SimpleExoPlayer.Builder(this).build()
        playerView.setPlayer(player)
        // Now that we have a player we need to create a media item to play.
        val mediaItem: MediaItem = MediaItem.fromUri(getString(R.string.media_url_mp3))
        player.setMediaItem(mediaItem)
        // Provide the state to the player
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();
    }

    private fun releasePlayer() {
        playWhenReady = player.playWhenReady
        playbackPosition = player.currentPosition
        currentWindow = player.currentWindowIndex
        player.release()
    }

    private fun hideSystemUI() {
        window.setDecorFitsSystemWindows(false)
        val controller = window.insetsController
        with(controller) {
            this?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
            this?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

}