package com.example.reshiprimer

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import kotlin.math.roundToInt
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val constraintLayout: ConstraintLayout = findViewById(R.id.constraintLayout)
        val textViewError: TextView = findViewById(R.id.textViewError)
        val startButton: Button = findViewById(R.id.startButton)
        val changeButton: Button = findViewById(R.id.searchButton)
        val firstNum: TextView = findViewById(R.id.firstNum)
        val operationLabel: TextView = findViewById(R.id.operationLabel)
        val secondNum: TextView = findViewById(R.id.secondNum)
        val result: TextView = findViewById(R.id.resultText)
        val correctLabel: TextView = findViewById(R.id.correctLabel)
        val wrongLabel: TextView = findViewById(R.id.wrongLabel)
        val totalLabel: TextView = findViewById(R.id.totalLabel)
        val percentLabel: TextView = findViewById(R.id.percentLabel)
        val rnd = Random
        var correctInt = 0
        var wrongInt = 0
        val operations: List<String> = listOf("+", "-", "/", "*")
        changeButton.isEnabled = false
        textViewError.isVisible = false
        result.isEnabled = false

        fun genRandomPrimer() {
            result.text = ""
            operationLabel.text = operations.elementAt(rnd.nextInt(0, 4))
            var first = rnd.nextInt(10, 100)
            var second = rnd.nextInt(10, 100)
            if (operationLabel.text == "/") {
                while (first.toDouble() % second.toDouble() != 0.0) {
                    first = rnd.nextInt(10, 100)
                    second = rnd.nextInt(10, 100)
                }
            }
            firstNum.text = first.toString()
            secondNum.text = second.toString()
        }

        fun checkPrimer(): Boolean {
            return when (operationLabel.text) {
                "+" -> {
                    (firstNum.text.toString().toInt() + secondNum.text.toString()
                            .toInt()) == result.text.toString().toInt()
                }
                "-" -> {
                    (firstNum.text.toString().toInt() - secondNum.text.toString()
                            .toInt()) == result.text.toString().toInt()
                }
                "/" -> {
                    (firstNum.text.toString().toInt() / secondNum.text.toString()
                            .toInt()) == result.text.toString().toInt()
                }
                "*" -> {
                    (firstNum.text.toString().toInt() * secondNum.text.toString()
                            .toInt()) == result.text.toString().toInt()
                }
                else -> false
            }
        }

        fun checkInputCorrectness(value: String): Boolean {
            return value.toIntOrNull() != null
        }

        // Событие по нажатии на "Старт"
        startButton.setOnClickListener()
        {
            startButton.isEnabled = false
            constraintLayout.setBackgroundResource(R.color.white);
            textViewError.isVisible = false

            result.isEnabled = true
            changeButton.isEnabled = true
            result.text = ""
            genRandomPrimer()
        }

        // Включаем кнопку "Проверка" когда введен какой-то текст
        result.addTextChangedListener()
        {
            changeButton.isEnabled = !result.text.isNullOrBlank()
        }

        // Событие по нажатии на кнопку "Проверка"
        changeButton.setOnClickListener()
        {
            textViewError.isVisible = false
            if (checkInputCorrectness(result.text.toString())) {
                if (checkPrimer()) {
                    // правильное решение
                    correctInt++
                    correctLabel.text = correctInt.toString()
                    constraintLayout.setBackgroundResource(R.color.green);
                } else {
                    // неправильное решение
                    wrongInt++
                    wrongLabel.text = wrongInt.toString()
                    constraintLayout.setBackgroundResource(R.color.red);
                }
                totalLabel.text = (correctInt + wrongInt).toString()

                percentLabel.text = (
                        (((correctInt.toDouble() / (totalLabel.text.toString().toDouble() / 100.0)) * 100).roundToInt().toDouble() / 100
                    ).toString()
                    + "%")
                result.isEnabled = false
                changeButton.isEnabled = false
                startButton.isEnabled = true
            } else {
                // некорректный ввод
                textViewError.isVisible = true
            }
        }

    }
}