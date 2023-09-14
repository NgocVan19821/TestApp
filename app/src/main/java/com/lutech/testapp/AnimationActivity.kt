package com.lutech.testapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalTime
import java.util.Calendar
import java.util.concurrent.TimeUnit
data class TimeComponents(val hours: Long, val minutes: Long, val seconds: Long, val milliseconds: Long)

class AnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        main2()
        main3()
    }
    fun main2() {
        val calendar = Calendar.getInstance()
        println(calendar.timeInMillis)
        Log.d("89989",calendar.timeInMillis.toString() )
    }


    @SuppressLint("NewApi")
    fun getCurrentTimeWithMillis(): String {
        val currentTime = LocalTime.now()
        val hour = currentTime.hour
        val minute = currentTime.minute
        val second = currentTime.second
        val millisecond = currentTime.nano / 1_000_000
        val digit1 = millisecond / 10
        val digit2 = millisecond % 10
        Log.d("8998989","Current Time: $digit1" )
        Log.d("8998989","Current Time: $digit2" )
        return "$hour:$minute:$second.$digit1"

    }

    fun main() {
        val currentTime = getCurrentTimeWithMillis()
        println("Current Time: $currentTime")
        Log.d("8998989","Current Time: $currentTime" )

    }

    fun convertMillisToTime(duration: Long): TimeComponents {
        val hours = duration / 3600000
        val remainingMillis1 = duration % 3600000
        val minutes = remainingMillis1 / 60000
        val remainingMillis2 = remainingMillis1 % 60000
        val seconds = remainingMillis2 / 1000
        val milliseconds = remainingMillis2 % 1000

        return TimeComponents(hours, minutes, seconds, milliseconds)
    }

    fun main3() {
        val durationInMillis = 3661000L // Example duration in milliseconds

        val timeComponents = convertMillisToTime(durationInMillis)

        println("Hours: ${timeComponents.hours}, Minutes: ${timeComponents.minutes}, Seconds: ${timeComponents.seconds}, Milliseconds: ${timeComponents.milliseconds}")
        Log.d("899", "Hours: ${timeComponents.hours}, Minutes: ${timeComponents.minutes}, Seconds: ${timeComponents.seconds}, Milliseconds: ${timeComponents.milliseconds}")
    }


}