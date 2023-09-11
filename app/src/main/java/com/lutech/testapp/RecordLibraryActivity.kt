package com.lutech.testapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_record_library.record_button
import kotlinx.android.synthetic.main.activity_record_library.timeline_text_view
import kotlinx.android.synthetic.main.activity_record_library.visualizer
import kotlin.math.sqrt

class RecordLibraryActivity : AppCompatActivity() {
    private lateinit var recorder: Recorder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_library)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        checkAudioPermission(AUDIO_PERMISSION_REQUEST_CODE)

        initUI()
    }
    override fun onStart() {
        super.onStart()
        listenOnRecorderStates()
    }

    override fun onStop() {
        recorder.release()
        super.onStop()
    }
    private fun initUI(){
        record_button.setOnClickListener { recorder.toggleRecording() }
        visualizer.ampNormalizer = { sqrt(it.toFloat()).toInt() }
    }

    private fun listenOnRecorderStates() {
        recorder = Recorder.getInstance(applicationContext).init().apply {
            onStart = { record_button.icon = getDrawableCompat(R.drawable.ic_pause_audio) }
            onStop = {
                visualizer.clear()
                timeline_text_view.text = 0L.formatAsTime()
                record_button.icon = getDrawableCompat(R.drawable.ic_play_audio)
                startActivity(Intent(this@RecordLibraryActivity, SettingSystemActivity::class.java))
            }
            onAmpListener = {
                runOnUiThread {
                    if (recorder.isRecording) {
                        timeline_text_view.text = recorder.getCurrentTime().formatAsTime()
                        visualizer.addAmp(it, tickDuration)
                    }
                }
            }
        }
    }
    companion object {
        private const val AUDIO_PERMISSION_REQUEST_CODE = 1
    }
}