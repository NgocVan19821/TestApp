package com.lutech.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import java.io.File
import java.io.IOException

class ConvertVoiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_convert_voice)

        val inputFile = File("https://soundcloud.com/mcwhizzkid/dj-whizzkid-sonorous")
        val outputAbdulBasitMarattal = File("/path/to/your/output/abdul_basit_marattal.mp3")
        val outputAbdulBasitMujawwad = File("/path/to/your/output/abdul_basit_mujawwad.mp3")
        val outputAbdurRashidAliSufi = File("/path/to/your/output/abdur_rashid_ali_sufi.mp3")
        val outputNasserBinAliAlQatami = File("/path/to/your/output/nasser_bin_ali_al_qatami.mp3")

        if (inputFile.exists()) {
            try {
                val processBuilderMarattal = ProcessBuilder(
                    "ffmpeg",
                    "-i", inputFile.absolutePath,
                    "-af", "aevalsrc=0:d=0.5[s1];[s1]showwavespic=s=640x120:colors=white,format=rgba[out]",
                    "-y", outputAbdulBasitMarattal.absolutePath
                )

                val processBuilderMujawwad = ProcessBuilder(
                    "ffmpeg",
                    "-i", inputFile.absolutePath,
                    "-af", "aevalsrc=0:d=0.5[s1];[s1]showwavespic=s=640x120:colors=white,format=rgba[out]",
                    "-y", outputAbdulBasitMujawwad.absolutePath
                )

                val processBuilderSufi = ProcessBuilder(
                    "ffmpeg",
                    "-i", inputFile.absolutePath,
                    "-af", "aevalsrc=0:d=0.5[s1];[s1]showwavespic=s=640x120:colors=white,format=rgba[out]",
                    "-y", outputAbdurRashidAliSufi.absolutePath
                )

                val processBuilderAlQatami = ProcessBuilder(
                    "ffmpeg",
                    "-i", inputFile.absolutePath,
                    "-af", "aevalsrc=0:d=0.5[s1];[s1]showwavespic=s=640x120:colors=white,format=rgba[out]",
                    "-y", outputNasserBinAliAlQatami.absolutePath
                )

                val processMarattal = processBuilderMarattal.start()
                val processMujawwad = processBuilderMujawwad.start()
                val processSufi = processBuilderSufi.start()
                val processAlQatami = processBuilderAlQatami.start()

                processMarattal.waitFor()
                processMujawwad.waitFor()
                processSufi.waitFor()
                processAlQatami.waitFor()

                // Conversion is complete, you can perform further actions here
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}

