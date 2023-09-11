package com.lutech.testapp

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_setting_system.playButton
import kotlinx.android.synthetic.main.activity_setting_system.seekBackwardButton
import kotlinx.android.synthetic.main.activity_setting_system.seekForwardButton
import kotlinx.android.synthetic.main.activity_setting_system.timeline_text_view
import kotlinx.android.synthetic.main.activity_setting_system.visualizer
import kotlin.math.sqrt


class SettingSystemActivity : AppCompatActivity() {
    private val storagePermissionCode = 101 // You can use any unique code
    private lateinit var player: AudioPlayer

    @SuppressLint("ClickableViewAccessibility")


    override fun onStart() {
        super.onStart()
        initUI()
    }
    override fun onStop() {
        player.release()
        super.onStop()
    }


    private fun initUI(){
        visualizer.apply {
            ampNormalizer = { sqrt(it.toFloat()).toInt() }
            onStartSeeking = {
                player.pause()
            }
            onSeeking = {timeline_text_view.text = it.formatAsTime() }
            onFinishedSeeking = { time, isPlayingBefore ->
                player.seekTo(time)
                if (isPlayingBefore) {
                    player.resume()
                }
            }
            onAnimateToPositionFinished = { time, isPlaying ->
                updateTime(time, isPlaying)
                player.seekTo(time)
            }
        }
        playButton.setOnClickListener { player.togglePlay() }
        seekForwardButton.setOnClickListener { visualizer.seekOver(SEEK_OVER_AMOUNT) }
        seekBackwardButton.setOnClickListener { visualizer.seekOver(-SEEK_OVER_AMOUNT) }

        lifecycleScope.launchWhenCreated {
            val amps = player.loadAmps()
            visualizer.setWaveForm(amps, player.tickDuration)
        }

    }

    private fun updateTime(time: Long, isPlaying: Boolean) {
        timeline_text_view.text = time.formatAsTime()
        visualizer.updateTime(time, isPlaying)
    }
    companion object {
        const val SEEK_OVER_AMOUNT = 5000
    }

}



