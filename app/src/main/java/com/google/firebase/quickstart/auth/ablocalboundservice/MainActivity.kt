package com.google.firebase.quickstart.auth.ablocalboundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var myService: BoundService? = null
    var isBound = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this, BoundService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)
        
        val showtime : Button = findViewById(R.id.showtime)
        val myTextView : TextView = findViewById(R.id.textView)
        showtime.setOnClickListener {
            val currentTime = myService?.getCurrentTime()
            myTextView.text = currentTime
        }
    }

    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            val binder = service as BoundService.MyLocalBinder
            myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }
}