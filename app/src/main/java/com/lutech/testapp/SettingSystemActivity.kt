package com.lutech.testapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting_system.tv
import kotlinx.android.synthetic.main.activity_setting_system.tvOk


class SettingSystemActivity : AppCompatActivity() {

    private val FOREGROUND_SERVICE_PERMISSION_REQUEST_CODE = 1001
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_system)

        tvOk.setOnClickListener {
            if (isBatteryOptimization(this)) {
                tv.visibility = View.INVISIBLE

                Toast.makeText(this,  "R", Toast.LENGTH_SHORT).show()
                // Permission is already granted, start your foreground service here
            } else {
                requestIgnoreOptimizeBattery()

                // Request the permission
                Toast.makeText(this,  "C", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun requestIgnoreOptimizeBattery() {
        val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        intent.data = Uri.parse("package:$packageName")
        startActivityForResult(intent, FOREGROUND_SERVICE_PERMISSION_REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isBatteryOptimization(context: Context): Boolean {
        var powerManager = context.getSystemService(POWER_SERVICE) as PowerManager
        return powerManager != null && powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == FOREGROUND_SERVICE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                tv.visibility = View.INVISIBLE
                Toast.makeText(this, "okkkkk", Toast.LENGTH_SHORT).show()
                // Permission granted, start your foreground service here
            } else{
                tv.visibility = View.VISIBLE

            }
        }
}
}

