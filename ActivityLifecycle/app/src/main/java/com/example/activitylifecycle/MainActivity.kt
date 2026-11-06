package com.example.activitylifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LifecycleTest", "onCreate called")
        setContent { Text(text = "Activity Lifecycle") }
    }

    override fun onStart() {
        super.onStart()
        Log.d("LifecycleTest", "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d("LifecycleTest", "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d("LifecycleTest", "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d("LifecycleTest", "onStop called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("LifecycleTest", "onRestart called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifecycleTest", "onDestroy called")
    }
}