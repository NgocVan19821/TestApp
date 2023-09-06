package com.lutech.testapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_setting_system.buttonSave
import kotlinx.android.synthetic.main.activity_setting_system.radioGroup
import java.io.File


class SettingSystemActivity : AppCompatActivity() {
    private val storagePermissionCode = 101 // You can use any unique code
    val filePath = "${R.raw.test}"

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_system)
        testGroup()
        val permission = android.Manifest.permission.WRITE_SETTINGS
        val granted = Settings.System.canWrite(this)
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
        if (!granted) {
            startActivityForResult( Intent(android.provider.Settings.ACTION_SETTINGS), 0);

            // Nếu ứng dụng chưa được cấp quyền, hiển thị hộp thoại yêu cầu quyền
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivityForResult(intent, 100)
        } else {
            // Ứng dụng đã được cấp quyền, thực hiện các hành động cần thiết
        }
        val storagePermission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        val audioPermission = android.Manifest.permission.MODIFY_AUDIO_SETTINGS

        val storagePermissionGranted = ContextCompat.checkSelfPermission(this, storagePermission) == PackageManager.PERMISSION_GRANTED
        val audioPermissionGranted = ContextCompat.checkSelfPermission(this, audioPermission) == PackageManager.PERMISSION_GRANTED

        if (!storagePermissionGranted) {
            ActivityCompat.requestPermissions(this, arrayOf(storagePermission), 100)
        }

        if (!audioPermissionGranted) {
            ActivityCompat.requestPermissions(this, arrayOf(audioPermission), 150)
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val granted = Settings.System.canWrite(this)

            if (granted) {
                testAlarm()
            } else {
                Log.d("89989","chua co quyen")
            }
        }
    }

    @SuppressLint("Range")
    private fun testRingtone(){
        val audioFile = File(filePath)

        if (audioFile.exists()) {
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DATA, audioFile.absolutePath)
                put(MediaStore.MediaColumns.TITLE, "Ringtone Title")
                put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3")
                put(MediaStore.Audio.Media.IS_RINGTONE, true)
                put(MediaStore.Audio.Media.IS_NOTIFICATION, false)
                put(MediaStore.Audio.Media.IS_ALARM, false)
                put(MediaStore.Audio.Media.IS_MUSIC, false)
            }

            val resolver = this.contentResolver
            val uri = MediaStore.Audio.Media.getContentUriForPath(audioFile.absolutePath)

            // Delete the previous ringtone (optional)
            val ringtoneCursor = resolver.query(uri!!, null, null, null, null)
            if (ringtoneCursor != null && ringtoneCursor.moveToFirst()) {
                val id = ringtoneCursor.getLong(ringtoneCursor.getColumnIndex(MediaStore.MediaColumns._ID))
                val existingRingtoneUri = Uri.withAppendedPath(uri, id.toString())
                resolver.delete(existingRingtoneUri, null, null)
                ringtoneCursor.close()
            }

            // Insert the new ringtone
            val newRingtoneUri = resolver.insert(uri, values)

            // Set the default ringtone
            RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE, newRingtoneUri)
        }

    }

    @SuppressLint("Range")
    private fun testAlarm(){
        val audioFile = File(filePath)

        if (audioFile.exists()) {
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DATA, audioFile.absolutePath)
                put(MediaStore.MediaColumns.TITLE, "Alarm Title")
                put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3")
                put(MediaStore.Audio.Media.IS_RINGTONE, false)
                put(MediaStore.Audio.Media.IS_NOTIFICATION, false)
                put(MediaStore.Audio.Media.IS_ALARM, true) // Set as an alarm tone
                put(MediaStore.Audio.Media.IS_MUSIC, false)
            }

            val resolver = this.contentResolver
            val uri = MediaStore.Audio.Media.getContentUriForPath(audioFile.absolutePath)

            // Delete the previous alarm tone (optional)
            val alarmCursor = resolver.query(uri!!, null, null, null, null)
            if (alarmCursor != null && alarmCursor.moveToFirst()) {
                val id = alarmCursor.getLong(alarmCursor.getColumnIndex(MediaStore.MediaColumns._ID))
                val existingAlarmUri = Uri.withAppendedPath(uri, id.toString())
                resolver.delete(existingAlarmUri, null, null)
                alarmCursor.close()
            }

            // Insert the new alarm tone
            val newAlarmUri = resolver.insert(uri, values)

            // Set the default alarm tone
            RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM, newAlarmUri)
        }

    }

    @SuppressLint("Range")
    private fun testNotification(){
        val audioFile = File(filePath)

        if (audioFile.exists()) {
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DATA, audioFile.absolutePath)
                put(MediaStore.MediaColumns.TITLE, "Notification Sound Title")
                put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3")
            }

            val resolver = this.contentResolver
            val uri = MediaStore.Audio.Media.getContentUriForPath(audioFile.absolutePath)

            // Delete the previous notification sound (optional)
            val notificationCursor = resolver.query(uri!!, null, null, null, null)
            if (notificationCursor != null && notificationCursor.moveToFirst()) {
                val id = notificationCursor.getLong(notificationCursor.getColumnIndex(MediaStore.MediaColumns._ID))
                val existingNotificationUri = Uri.withAppendedPath(uri, id.toString())
                resolver.delete(existingNotificationUri, null, null)
                notificationCursor.close()
            }

            // Insert the new notification sound
            val newNotificationUri = resolver.insert(uri, values)

            // Set the default notification sound
            val notificationSettings = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            if (newNotificationUri != null) {
                Settings.System.putString(resolver, Settings.System.NOTIFICATION_SOUND, newNotificationUri.toString())
            }
        }

    }


    private fun testGroup(){
        buttonSave.setOnClickListener {
            // Check which radio button is selected
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId

            when (selectedRadioButtonId) {
                R.id.radioButtonDefault -> {
                    // Apply the default theme
//                    testNotification()
                    testAlarm()
                    Toast.makeText(this, "Notification", Toast.LENGTH_LONG).show()
                        Log.d("899899","Notification" )
                }
                R.id.radioButtonAlternative -> {
                    // Apply the alternative theme
//                    testRingtone()
                    Toast.makeText(this, "Ringtone", Toast.LENGTH_LONG).show()
                    Log.d("899899","Ringtone" )

                }
                // Add more cases for other themes if needed

                else -> {
                    // Handle the case when no radio button is selected
                }
            }
            Log.d("89999", selectedRadioButtonId.toString())

            // Recreate the activity to apply the new theme
            recreate()
        }

    }

}



