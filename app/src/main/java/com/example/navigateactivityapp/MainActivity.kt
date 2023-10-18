package com.example.navigateactivityapp

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.Toast


class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener, SensorEventListener {
    private lateinit var gestureDetector: GestureDetector
    private lateinit var pic: ImageView
    private var sensorManager: SensorManager ?= null
    private var sensor: Sensor ?= null
    var x1:Float = 0.0f
    var x2:Float = 0.0f
    var y1:Float = 0.0f
    var y2:Float = 0.0f

    var x:Float = 0.0f
    var y:Float = 0.0f
    var z:Float = 0.0f
    var threshold:Float = 1000.0f
    var time:Long = 0
    companion object{
        const val MIN_DISTANCE = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gestureDetector = GestureDetector(this, this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        pic = findViewById(R.id.home)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(ev)
        when (ev.action) {
            0->{
                x1 = ev.x
                y1 = ev.y
            }
            1->{
                x2 = ev.x
                y2 = ev.y

                val valX: Float = x2 - x1
                val valY: Float = y2 - y1

                if (Math.abs(valX) > MIN_DISTANCE) {
                    if (x2 > x1) moveToWest()
                    else if (x2 < x1) moveToEast()
                }
                if (Math.abs(valY) > MIN_DISTANCE) {
                    if (y2 > y1) moveToSouth()
                    else if (y1 > y2) moveToNorth()
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    override fun onDown(e: MotionEvent): Boolean {
        return false

    }

    override fun onShowPress(e: MotionEvent) {
    }

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    override fun onResume(){
        super.onResume()
        sensorManager!!.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }
    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent) {
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    private fun moveToEast() {
        startActivity(Intent(this, MainActivity2::class.java))
        finish()
    }
    private fun moveToWest() {
        startActivity(Intent(this, MainActivity3::class.java))
        finish()
    }
    private fun moveToSouth() {
        startActivity(Intent(this, MainActivity4::class.java))
        finish()
    }
    private fun moveToNorth() {
        startActivity(Intent(this, MainActivity5::class.java))
        finish()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val curr_x = event!!.values[0]
        val curr_y = event!!.values[1]
        val curr_z = event!!.values[2]
        val curr_time = System.currentTimeMillis()
        if ((curr_time - time) > 100) {
            val timeDiff = curr_time - time
            time = curr_time
            var speed = Math.abs(curr_x + curr_y + curr_z - x - y - z) / timeDiff * 1000
            if (speed > threshold) {
                shakePic()
                Toast.makeText(this,"Rotating",Toast.LENGTH_LONG)
            }
        }
    }

    private fun shakePic(){

        pic.animate().apply {
            duration  = 250
            rotationBy(-20f)
        }.withEndAction{
            pic.animate().apply {
                duration  = 250
                rotationBy(40f)}
        }.withEndAction{
            pic.animate().apply {
                duration  = 250
                rotationBy(-40f)}
        }.withEndAction{
            pic.animate().apply {
                duration  = 250
                rotationBy(40f)
            }.withEndAction{
                pic.animate().apply {
                    duration  = 250
                    rotationBy(-40f)
                }}.withEndAction{
                pic.animate().apply {
                    duration  = 250
                    rotationBy(40f)}
            }.withEndAction{
                pic.animate().apply {
                    duration  = 250
                    rotationBy(-40f)
                }
            }.withEndAction{
                pic.animate().apply {
                    duration  = 250
                    rotationBy(-20f)}
            }


        }.start()

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}