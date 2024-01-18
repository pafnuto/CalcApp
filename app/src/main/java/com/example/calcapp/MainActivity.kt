package com.example.calcapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var EnteredTextView: TextView
    lateinit var ResultTextView: TextView

    var currentInput: String = ""
    var currentOperator: String? = null
    var firstOperand: Double = 0.0
    var isResultDisplayed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EnteredTextView = findViewById(R.id.enteredTV)
        ResultTextView = findViewById(R.id.resultTV)

        var buttons = arrayOf(
            R.id.One, R.id.Two, R.id.Three, R.id.Four, R.id.Five, R.id.Six, R.id.Seven,
            R.id.Eight, R.id.Nine, R.id.ZeroZero, R.id.Zero, R.id.Equalto, R.id.Decimal
        )
        for (buttonID in buttons)
            findViewById<Button>(buttonID).setOnClickListener { onDigitClicked(it) }
        }

    fun onDigitClicked(view: View) {
        if (isResultDisplayed) {
            ClearAll()
            isResultDisplayed = false
        }
        currentInput += (view as Button).text.toString()
        updateDisplay()
    }

    fun onOperatorClicked(operator: String) {
        if (currentInput.isNotEmpty()) {
            if (currentOperator != null) {
                calculateResult()
            }
            currentOperator = operator
            firstOperand = currentInput.toDouble()
            currentInput = ""
            isResultDisplayed = false
            updateDisplay()
        } else if (isResultDisplayed) {
            currentOperator = operator
            currentInput = firstOperand.toString()
            isResultDisplayed = false
            updateDisplay()
        }
    }

    fun ClearAll() { //Кнопка AC очищает поле ввода
        currentInput = ""
        currentOperator = null
        firstOperand = 0.0
        isResultDisplayed = false
        updateDisplay()
    }

    fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length - 1)
            updateDisplay()
        }
    }

    fun calculateResult() {
        if (currentOperator != null && currentInput.isNotEmpty()) {
            val secondOperand = currentInput.toDouble()
            when (currentOperator) {
                "+" -> firstOperand += secondOperand
                "-" -> firstOperand -= secondOperand
                "*" -> firstOperand *= secondOperand
                "/" -> {
                    if (secondOperand != 0.0) {
                        firstOperand /= secondOperand
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Division By Zero Error",
                            Toast.LENGTH_SHORT
                        ).show()
                        ClearAll()
                        EnteredTextView.text = "Error"
                        return
                    }
                }

                "%" -> firstOperand = (firstOperand * secondOperand) / 100
            }
            currentOperator = null
            currentInput = firstOperand.toString()
            isResultDisplayed = true
            updateDisplay()
            ResultTextView.text = "=$firstOperand"

        }
    }

    fun updateDisplay() {
        val expression = if (currentOperator != null) {
            "$firstOperand $currentOperator $currentInput"
        } else {
            currentInput
        }
        EnteredTextView.text = expression
        ResultTextView.text = if (currentOperator != null) {
            "$firstOperand $currentOperator"
        } else {
            ""
        }
    }
}