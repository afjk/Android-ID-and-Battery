package com.example.androididbattery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var deviceIdValue: TextView
    private lateinit var batteryLevelValue: TextView
    private lateinit var batteryUpdateHandler: Handler
    private lateinit var batteryUpdateRunnable: Runnable
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        deviceIdValue = findViewById(R.id.device_id_value)
        batteryLevelValue = findViewById(R.id.battery_level_value)
        
        // Initialize battery update handler
        batteryUpdateHandler = Handler(Looper.getMainLooper())
        
        // Get and display device ID
        displayDeviceId()
        
        // Start battery level monitoring
        startBatteryMonitoring()
    }
    
    private fun displayDeviceId() {
        try {
            val androidId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            deviceIdValue.text = androidId ?: "Not available"
        } catch (e: Exception) {
            deviceIdValue.text = "Error retrieving device ID"
        }
    }
    
    private fun startBatteryMonitoring() {
        batteryUpdateRunnable = object : Runnable {
            override fun run() {
                updateBatteryLevel()
                batteryUpdateHandler.postDelayed(this, 1000) // Update every 1 second
            }
        }
        batteryUpdateHandler.post(batteryUpdateRunnable)
    }
    
    private fun updateBatteryLevel() {
        val batteryManager = getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        val batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        
        if (batteryLevel != Integer.MIN_VALUE) {
            batteryLevelValue.text = "$batteryLevel%"
        } else {
            // Fallback method for older devices
            val batteryIntent = registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            batteryIntent?.let { intent ->
                val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                
                if (level != -1 && scale != -1) {
                    val batteryPct = (level * 100 / scale)
                    batteryLevelValue.text = "$batteryPct%"
                } else {
                    batteryLevelValue.text = "Unknown"
                }
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        batteryUpdateHandler.removeCallbacks(batteryUpdateRunnable)
    }
}