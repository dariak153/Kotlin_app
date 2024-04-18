package org.mycode.mykotlinapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonPID: Button = findViewById(R.id.button1)
        val buttonOpenGL: Button = findViewById(R.id.button2)
        val buttonAPI: Button = findViewById(R.id.button3)
        val buttonCalculations: Button = findViewById(R.id.button4)

        buttonPID.setOnClickListener {
            val intent = Intent(this, PIDControllerActivity::class.java)
            startActivity(intent)
        }

        buttonOpenGL.setOnClickListener {
            val intent = Intent(this, OpenGLActivity::class.java)
            startActivity(intent)
        }

        buttonAPI.setOnClickListener {
            val intent = Intent(this, APIConnectionActivity::class.java)
            startActivity(intent)
        }

        buttonCalculations.setOnClickListener {
            val intent = Intent(this, CalculationsActivity::class.java)
            startActivity(intent)
        }
    }
}
