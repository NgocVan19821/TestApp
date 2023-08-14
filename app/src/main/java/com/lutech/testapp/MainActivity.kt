package com.lutech.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import java.io.File
import java.lang.Float.max
import java.lang.Float.min
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var textToSpeech: TextToSpeech

    private lateinit var mScaleGestureDetector: ScaleGestureDetector
    private var mScaleFactor = 1.0f
    private lateinit var mImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mImageView = findViewById(R.id.image_view_1)
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        textToSpeech = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val file = File("/path/to/your/audio/file.mp3")
                if (file.exists()) {
                    val text = "Hello, this is a sample text to convert voice."
                    val langResult = textToSpeech.setLanguage(Locale.US)

                    if (langResult == TextToSpeech.LANG_MISSING_DATA || langResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                        // Handle language data missing or not supported
                    } else {
                        val params = HashMap<String, String>()
                        params[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "conversionId"
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params)
                    }
                }
            }
        }

        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String) {}

            override fun onDone(utteranceId: String) {
                if (utteranceId == "conversionId") {
                    // Conversion is done, you can perform any further actions here
                }
            }

            override fun onError(utteranceId: String) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.shutdown()
    }
    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        mScaleGestureDetector.onTouchEvent(motionEvent)
        return true
    }

    // Zooming in and out in a bounded range
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            mScaleFactor = max(0.1f, min(mScaleFactor, 10.0f))
            mImageView.scaleX = mScaleFactor
            mImageView.scaleY = mScaleFactor
            return true
        }
    }
}
