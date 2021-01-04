package ntua.hci.sensorsdemo

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private lateinit var textView: TextView
    private var sensor: Sensor? = null

    override fun onResume() {
        super.onResume()

        sensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.getSensorList(Sensor.TYPE_ALL).forEach { sensor ->
            findViewById<TextView>(R.id.txtSensors).append(sensor.name + "\n")
        }

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
        textView = findViewById<TextView>(R.id.text)

        if (sensor == null) {
            textView.text = getString(R.string.no_sensor)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val xStr = event!!.values[0].toString()
        val yStr = event!!.values[1].toString()
        val zStr = event!!.values[2].toString()

        val strCoord = "X: $xStr\nY: $yStr\nZ: $zStr\n"
        textView.text = strCoord
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do something here if sensor accuracy changes.
    }
}