package org.mycode.mykotlinapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.coroutines.*


class PIDControllerActivity : AppCompatActivity() {

    private var targetValue = 30.0
    private var throttleMaxMemory = 21
    private var vP = 5.0
    private var vI = 2.0
    private var vD = 3.0

    private val delayedThrottle = DoubleArray(throttleMaxMemory)
    private var throttle = 0.0
    private var engineDelta = 0.0
    private var currentValue = 1.0
    private var xP = 0.0
    private var xI = 0.0
    private var xD = 0.0
    private var integralSum = 0.0
    private var currError = 0.0
    private var lastError = 0.0

    private val historyMeasuresThrottle = DoubleArray(800)
    private val historyMeasuresValue = DoubleArray(800)
    private val historyMeasuresTarget = DoubleArray(800)

    private var counter = 0
    private var currentTemperature = 0.0

    private lateinit var seekBar: SeekBar
    private lateinit var currentTemperatureView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pidcontroller)

        seekBar = findViewById(R.id.seekBar)
        currentTemperatureView = findViewById(R.id.currentTemperature)

        vP /= 100
        vI /= 100000
        vD /= 1

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                targetValue = progress.toDouble()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                doThrottleStep()

                xP = (targetValue - currentValue) * vP
                integralSum += (targetValue - currentValue)
                xI = integralSum * vI
                currError = (targetValue - currentValue)
                xD = (currError - lastError) * vD
                lastError = currError

                throttle = xP + xI + xD
                if (throttle < 0) throttle = 0.0
                if (throttle > 1) throttle = 1.0

                historyMeasuresThrottle[counter] = throttle
                historyMeasuresValue[counter] = currentValue
                historyMeasuresTarget[counter] = targetValue
                counter += 1
                if (counter >= 800) counter = 0

                currentTemperature = currentValue

                withContext(Dispatchers.Main) {
                    currentTemperatureView.text = "Temperatura docelowa wody: ${targetValue}℃ \nObecna temperatura wody: %.2f".format(currentTemperature) + "℃"

                }

                delay(10)
            }
        }
    }

    private fun doThrottleStep() {
        System.arraycopy(delayedThrottle, 0, delayedThrottle, 1, throttleMaxMemory - 1)
        delayedThrottle[0] = throttle

        engineDelta += (delayedThrottle[2] - 0.35) / 20
        if (engineDelta > 0.3) engineDelta = 0.3
        if (engineDelta < -0.3) engineDelta = -0.3
        currentValue += engineDelta
    }

}