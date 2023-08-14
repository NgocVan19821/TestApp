package com.lutech.testapp

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.btnPlay
import kotlinx.android.synthetic.main.activity_main.btnRecord
import kotlinx.android.synthetic.main.activity_main.btnStop
import kotlinx.android.synthetic.main.activity_main.btnStopPlay
import kotlinx.android.synthetic.main.activity_main.idTVstatus
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private var mRecorder = MediaRecorder()
    private var mPlayer = MediaPlayer ()
    val REQUEST_AUDIO_PERMISSION_CODE = 1
    private var mFileName: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnStop.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        btnPlay.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        btnStopPlay.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        btnRecord.setOnClickListener {
            startRecording()

        }
        btnStop.setOnClickListener {
            pauseRecording()

        }
        btnPlay.setOnClickListener {
            playAudio()
        }
        btnStopPlay.setOnClickListener {
            pausePlaying()
        }

        }
    private fun startRecording() {
        // check permission method is used to check
        // that the user has granted permission
        // to record and store the audio.
        if (CheckPermissions()) {

            // setbackgroundcolor method will change
            // the background color of text view.
            btnStop.setBackgroundColor(resources.getColor(android.R.color.holo_purple))
            btnRecord.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
            btnPlay.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
            btnStopPlay.setBackgroundColor(resources.getColor(android.R.color.darker_gray))

            // we are here initializing our filename variable
            // with the path of the recorded audio file.
            mFileName = Environment.getExternalStorageDirectory().absolutePath
            mFileName += "/AudioRecording.3gp"

            // below method is used to initialize
            // the media recorder class
            mRecorder = MediaRecorder()

            // below method is used to set the audio
            // source which we are using a mic.
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC)

            // below method is used to set
            // the output format of the audio.
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)

            // below method is used to set the
            // audio encoder for our recorded audio.
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

            // below method is used to set the
            // output file location for our recorded audio
            mRecorder.setOutputFile(mFileName)
            try {
                // below method will prepare
                // our audio recorder class
                mRecorder.prepare()
            } catch (e: IOException) {
                Log.e("TAG", "prepare() failed")
            }
            // start method will start
            // the audio recording.
            mRecorder.start()
            idTVstatus.setText("Recording Started")
        } else {
            // if audio recording permissions are
            // not granted by user below method will
            // ask for runtime permission for mic and storage.
            RequestPermissions()
        }
    }
//    private fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>?,
//        grantResults: IntArray
//    ) {
//        if (permissions != null) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        }
//        // this method is called when user will
//        // grant the permission for audio recording.
//        when (requestCode) {
//            REQUEST_AUDIO_PERMISSION_CODE -> if (grantResults.size > 0) {
//                val permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED
//                val permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED
//                if (permissionToRecord && permissionToStore) {
//                    Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_LONG)
//                        .show()
//                } else {
//                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_LONG)
//                        .show()
//                }
//            }
//        }
//    }

    fun CheckPermissions(): Boolean {
        // this method is used to check permission
        val result = ContextCompat.checkSelfPermission(applicationContext,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val result1 = ContextCompat.checkSelfPermission(applicationContext,
            Manifest.permission.RECORD_AUDIO
        )
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    private fun RequestPermissions() {
        // this method is used to request the
        // permission for audio recording and storage.
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_AUDIO_PERMISSION_CODE
        )
    }


    fun playAudio() {
        btnStop.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
        btnRecord.setBackgroundColor(resources.getColor(android.R.color.holo_purple))
        btnPlay.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
        btnStopPlay.setBackgroundColor(resources.getColor(android.R.color.holo_purple))

        // for playing our recorded audio
        // we are using media player class.
        mPlayer = MediaPlayer()
        try {
            // below method is used to set the
            // data source which will be our file name
            mPlayer.setDataSource(mFileName)

            // below method will prepare our media player
            mPlayer.prepare()

            // below method will start our media player.
            mPlayer.start()
            idTVstatus.setText("Recording Started Playing")
        } catch (e: IOException) {
            Log.e("TAG", "prepare() failed")
        }
    }

    fun pauseRecording() {
        btnStop.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
        btnRecord.setBackgroundColor(resources.getColor(android.R.color.holo_purple))
        btnPlay.setBackgroundColor(resources.getColor(android.R.color.holo_purple))
        btnStopPlay.setBackgroundColor(resources.getColor(android.R.color.holo_purple))

        // below method will stop
        // the audio recording.
        mRecorder.stop()

        // below method will release
        // the media recorder class.
        mRecorder.release()
        idTVstatus.setText("Recording Stopped")
    }

    fun pausePlaying() {
        // this method will release the media player
        // class and pause the playing of our recorded audio.
        mPlayer.release()
        btnStop.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
        btnStop.setBackgroundColor(resources.getColor(android.R.color.holo_purple))
        btnPlay.setBackgroundColor(resources.getColor(android.R.color.holo_purple))
        btnStopPlay.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
        idTVstatus.setText("Recording Play Stopped")
    }
}
