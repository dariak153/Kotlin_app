package org.mycode.mykotlinapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mariuszgromada.math.mxparser.Expression

class CalculationsActivity : AppCompatActivity() {
    private lateinit var resultView: TextView
    private lateinit var inputExpression: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculations)

        resultView = findViewById(R.id.resultView)
        inputExpression = findViewById(R.id.inputExpression)
    }

    fun onCalculate(view: View) {
        val expression = inputExpression.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val result = try {
                eval(expression).toString()
            } catch (e: Exception) {
                "Błąd: ${e.localizedMessage}"
            }

            withContext(Dispatchers.Main) {
                resultView.text = result
            }
        }
    }


    private fun eval(expression: String): Double {
        val e = Expression(expression)
        return e.calculate()
    }
}