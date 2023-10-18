package com.example.navigateactivityapp

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.Mock
import android.view.MotionEvent
import android.hardware.SensorEvent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.content.Context
import android.widget.Toast
import org.mockito.MockitoAnnotations
import java.lang.reflect.Field

class ExampleUnitTest {

    @Mock
    private val sensorManager: SensorManager = mock(SensorManager::class.java)

    @Mock
    private val sensor: Sensor = mock(Sensor::class.java)

    private lateinit var homeActivity: home

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        homeActivity = home()

        homeActivity.sensorManager = sensorManager

        homeActivity.sensor = sensor
    }

    @Test
    fun testOnTouchEvent() {
        val event = MotionEvent.obtain(0L, 0L, MotionEvent.ACTION_DOWN, 100f, 100f, 0)

        homeActivity.onTouchEvent(event)
        assertEquals(100f, homeActivity.x1)
        assertEquals(100f, homeActivity.y1)
    }

    @Test
    fun testOnSensorChanged() {
        val sensorEvent = createSensorEvent(floatArrayOf(1.0f, 2.0f, 3.0f), Sensor.TYPE_ACCELEROMETER)

        val mockContext = mock(Context::class.java)
        val mockToast = mock(Toast::class.java)

        `when`(mockContext.applicationContext).thenReturn(mockContext)
        `when`(Toast.makeText(eq(mockContext), eq("Rotating"), eq(Toast.LENGTH_LONG))).thenReturn(mockToast)

        homeActivity.onSensorChanged(sensorEvent)

        verify(mockToast).show()
    }

    private fun createSensorEvent(values: FloatArray, sensorType: Int): SensorEvent {
        val sensorEventClass = Class.forName("android.hardware.SensorEvent")
        val sensorEventConstructor = sensorEventClass.getDeclaredConstructor(Int::class.java)
        sensorEventConstructor.isAccessible = true
        val sensorEvent = sensorEventConstructor.newInstance(values.size)

        val valuesField: Field = sensorEventClass.getDeclaredField("values")
        valuesField.isAccessible = true
        valuesField.set(sensorEvent, values)

        val sensorField: Field = sensorEventClass.getDeclaredField("sensor")
        sensorField.isAccessible = true
        sensorField.set(sensorEvent, sensorType)

        return sensorEvent as SensorEvent
    }
}
