package com.lutech.testapp
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_main.bottomSheetBG
import kotlinx.android.synthetic.main.activity_main.btnDelete
import kotlinx.android.synthetic.main.activity_main.btnDone
import kotlinx.android.synthetic.main.activity_main.btnList
import kotlinx.android.synthetic.main.activity_main.btnRecord
import kotlinx.android.synthetic.main.activity_main.tvTimer
import kotlinx.android.synthetic.main.bottom_sheet.bottomSheet
import kotlinx.android.synthetic.main.bottom_sheet.btnCancel
import kotlinx.android.synthetic.main.bottom_sheet.btnOk
import kotlinx.android.synthetic.main.bottom_sheet.filenameInput
import java.io.File
import java.io.IOException
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date

const val REQUEST_CODE = 200
class MainActivity : AppCompatActivity(), Timer.OnTimerTickListener {
    private var permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO)
    private var permissionGranted = false
    private lateinit var recorder: MediaRecorder
    private var dirPath = ""
    private var filename = ""
    private var isRecording = false
    private var isPaused = false
    private lateinit var timer: Timer
    private lateinit var vibrator: Vibrator

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        permissionGranted = ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED
        if(!permissionGranted)
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.peekHeight = 0
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        timer = Timer(this)
        vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        btnRecord.setOnClickListener {
            when{
                isPaused ->resumeRecorder()
                isRecording -> pauseRecorder()
                else -> startRecording()
            }
            vibrator.vibrate(VibrationEffect.createOneShot(10, VibrationEffect.DEFAULT_AMPLITUDE))
        }
        btnList.setOnClickListener {
            Log.d("89998898", "list")

        }
        btnDone.setOnClickListener {
            stopRecorder()
            Log.d("89998898", "done")
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBG.visibility = View.VISIBLE
            filenameInput.setText(filename)

        }
        btnCancel.setOnClickListener {
            File("$dirPath$filename.mp3").delete()
            dismiss()
        }
        btnOk.setOnClickListener {
            dismiss()
            save()
        }
        bottomSheetBG.setOnClickListener {
            File("$dirPath$filename.mp3").delete()
            dismiss()
        }
        btnDelete.setOnClickListener {
            stopRecorder()
            File("$dirPath$filename.mp3").delete()
            Log.d("89998898", "delete")

        }
        btnDelete.isClickable = false
    }
    private fun save(){
        val newFilename = filenameInput.text.toString()
        if(newFilename != filename){
            var newFile = File("$dirPath$newFilename.mp3")
            File("$dirPath$filename.mp3").renameTo(newFile)
        }
    }
    private fun dismiss(){
        bottomSheetBG.visibility = View.GONE
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        hideKeyboard(filenameInput)
    }
    private fun hideKeyboard(view: View){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE)
            permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED
    }
    private fun pauseRecorder(){
        recorder.pause()
        isPaused = true
        btnRecord.setTextColor(ContextCompat.getColor(applicationContext,R.color.black))
        timer.pause()
    }
    private fun resumeRecorder(){
        recorder.resume()
        isPaused = false
        btnRecord.setTextColor(ContextCompat.getColor(applicationContext,R.color.white))
        timer.start()
    }
    private fun startRecording(){
        if(!permissionGranted) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
            return
        }
        recorder= MediaRecorder()
        dirPath = "${externalCacheDir?.absoluteFile}/"
        val simpleDateFormat = SimpleDateFormat("yyyy.MM.DD_hh.mm.ss")
        val date = simpleDateFormat.format(Date())
        filename = "audio_record_$date"
        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile("$dirPath$filename.mp3")
            try {
                prepare()
            } catch (e: IOException){}
            start()
        }
        btnRecord.setTextColor(ContextCompat.getColor(applicationContext,R.color.black))
        isRecording = true
        isPaused = false

        timer.start()
        btnDelete.isClickable = true
        btnDelete.setTextColor(ContextCompat.getColor(applicationContext,R.color.black))
        btnList.visibility = View.GONE
        btnDone.visibility = View.VISIBLE
    }
    private fun stopRecorder(){
        timer.stop()
        recorder.apply {
            stop()
            release()
        }
        isPaused = false
        isRecording = false
        btnList.visibility = View.VISIBLE
        btnDone.visibility = View.GONE
        btnDelete.isClickable = false
        btnDelete.setTextColor(ContextCompat.getColor(applicationContext,R.color.black))
        btnRecord.setTextColor(ContextCompat.getColor(applicationContext,R.color.black))
        tvTimer.text = "00:00:00"

    }

    override fun onTimerTick(duration: String) {
        tvTimer.text = duration
    }
}
