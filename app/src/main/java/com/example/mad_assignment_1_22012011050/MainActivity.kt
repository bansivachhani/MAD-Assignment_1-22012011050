package com.example.mad_assignment_1_22012011050

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView
    private var operand1: Double? = null
    private var operator: String? = null
    private val decimalFormat = DecimalFormat("#.########")  

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.resultTextView)

        val buttons = listOf(
            R.id.button0, R.id.button1, R.id.button2, R.id.button3,
            R.id.button4, R.id.button5, R.id.button6, R.id.button7,
            R.id.button8, R.id.button9,
            R.id.buttonAdd, R.id.buttonSubtract,
            R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonModulus,
            R.id.buttonEquals, R.id.buttonClear
        )

        buttons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { onButtonClick(it) }
        }
    }

    private fun onButtonClick(view: View) {
        when (view.id) {
            R.id.buttonClear -> clear()
            R.id.buttonEquals -> calculate()
            R.id.buttonAdd, R.id.buttonSubtract,
            R.id.buttonMultiply, R.id.buttonDivide, R.id.buttonModulus -> setOperator(view)
            else -> appendNumber((view as Button).text.toString())
        }
    }

    private fun clear() {
        resultTextView.text = "0"
        operand1 = null
        operator = null
    }

    private fun appendNumber(number: String) {

        if (resultTextView.text == "Error") {
            resultTextView.text = "0"
        }

        resultTextView.text = if (resultTextView.text == "0") {
            number
        } else {
            resultTextView.text.toString() + number
        }
    }

    private fun setOperator(view: View) {
        operand1 = resultTextView.text.toString().toDoubleOrNull()

        if (operand1 == null) {
            resultTextView.text = "Error"
            return
        }

        operator = (view as Button).text.toString()
        resultTextView.text = "0"
    }

    private fun calculate() {
        val operand2 = resultTextView.text.toString().toDoubleOrNull()

        if (operand1 == null || operator == null || operand2 == null) {
            resultTextView.text = "Error"
            return
        }

        val result = when (operator) {
            "+" -> operand1?.plus(operand2)
            "-" -> operand1?.minus(operand2)
            "*" -> operand1?.times(operand2)
            "/" -> if (operand2 != 0.0) operand1?.div(operand2) else {
                resultTextView.text = "Error"
                return
            }
            "%" -> operand1?.rem(operand2)
            else -> null
        }

        resultTextView.text = if (result != null) {
            decimalFormat.format(result)
        } else {
            "Error"
        }
        operand1 = null
        operator = null
    }
}
