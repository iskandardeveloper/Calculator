package com.example.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var textScreen: TextView
    private var hasResult = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        textScreen = binding.textScreen

        Toast.makeText(this, "\uD83D\uDD25Created by Iskandar Developer\uD83D\uDD25", Toast.LENGTH_SHORT).show()

        binding.numberZero.setOnClickListener {
            binding.numberZero.animate().alpha(1F)
            if (textScreen.text != "0") {
                writeNumber(0)
            }
        }

        binding.numberZero.setOnLongClickListener {
            if (textScreen.text != "0") {
                writeNumber(0)
                writeNumber(0)
            }
            true
        }

        binding.numberOne.setOnClickListener {
            writeNumber(1)
        }

        binding.numberTwo.setOnClickListener {
            writeNumber(2)
        }

        binding.numberThree.setOnClickListener {
            writeNumber(3)
        }

        binding.numberFour.setOnClickListener {
            writeNumber(4)
        }

        binding.numberFive.setOnClickListener {
            writeNumber(5)
        }

        binding.numberSix.setOnClickListener {
            writeNumber(6)
        }

        binding.numberSeven.setOnClickListener {
            writeNumber(7)
        }

        binding.numberEight.setOnClickListener {
            writeNumber(8)
        }

        binding.numberNine.setOnClickListener {
            writeNumber(9)
        }

        binding.point.setOnClickListener {
            val example = textScreen.text.toString()
            var actionIndex = -1
            for (i in example.indices) {
                if (example[i] == '+' || example[i] == '-' || example[i] == 'x' || example[i] == '÷' || example[i] == '%') {
                    actionIndex = i
                }
            }
            if (actionIndex == -1){
                if (!textScreen.text.toString().contains('.')){
                    textScreen.text = "${textScreen.text}."
                }
            }else {
                if (!textScreen.text.subSequence(actionIndex, textScreen.text.length).contains('.')) {
                    textScreen.text = "${textScreen.text}."
                }
            }
        }

        binding.actionAddition.setOnClickListener {
            writeAction("+")
        }

        binding.actionSubtraction.setOnClickListener {
            if (textScreen.text == "0") {
                textScreen.text = "-"
            } else {
                writeAction("-")
            }
        }

        binding.actionMultiplication.setOnClickListener {
            writeAction("x")
        }

        binding.actionDivision.setOnClickListener {
            writeAction("÷")
        }

        binding.actionPercent.setOnClickListener {
            writeAction("%")
        }

        binding.actionEqual.setOnClickListener {
            if (!hasResult) {
                if (textScreen.text.startsWith('-')) {
                    textScreen.text = "0-${textScreen.text.subSequence(1, textScreen.text.length)}"
                    calculate()
                } else {
                    if (
                        textScreen.text.contains('+') ||
                        textScreen.text.contains('-') ||
                        textScreen.text.contains('x') ||
                        textScreen.text.contains('÷') ||
                        textScreen.text.contains('%')
                    ) {
                        if (textScreen.text.toString().endsWith('+') ||
                            textScreen.text.toString().endsWith('-') ||
                            textScreen.text.toString().endsWith('x') ||
                            textScreen.text.toString().endsWith('÷') ||
                            textScreen.text.toString().endsWith('%')
                        ) {
                            textScreen.text = "Error!"
                            hasResult = true
                        } else {
                            calculate()
                        }
                    } else {
                        textScreen.text = "${textScreen.text}= \n${textScreen.text}"
                        hasResult = true
                    }
                }
            }
        }

        binding.actionDelete.setOnClickListener {
            val example = textScreen.text
            if (example.length == 1 || example == "-") {
                textScreen.text = "0"
            } else {
                textScreen.text = example.subSequence(0, example.length - 1)
            }
        }

        binding.actionClear.setOnClickListener {
            textScreen.text = "0"
            hasResult = false
        }
    }

    @SuppressLint("SetTextI18n")
    fun writeNumber(number: Int) {
        if (hasResult) {
            textScreen.text = "$number"
            hasResult = false
        } else {
            if (textScreen.text == "0") {
                textScreen.text = "$number"
            } else {
                textScreen.text = "${textScreen.text}$number"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun writeAction(action: String) {
        if (hasResult) {
            val example = textScreen.text.toString()
            for (i in example.indices) {
                if (example[i] == '=') {
                    textScreen.text = "${example.subSequence(i + 1, example.length)}$action"
                }
            }
            hasResult = false
        } else {
            if (textScreen.text.toString().endsWith('+') ||
                textScreen.text.toString().endsWith('-') ||
                textScreen.text.toString().endsWith('x') ||
                textScreen.text.toString().endsWith('÷') ||
                textScreen.text.toString().endsWith('%')
            ) {
                textScreen.text =
                    "${textScreen.text.subSequence(0,  textScreen.text.length - 1)}$action"
            } else {
                textScreen.text = "${textScreen.text}$action"
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun calculate() {
        val numbers = ArrayList<Double>()
        val actions = ArrayList<Int>()
        val example = textScreen.text.toString()
        var index = 0
        for (i in example.indices) {
            if (actions.isEmpty()) {
                when (example[i]) {
                    '+' -> {
                        numbers.add(example.subSequence(index, i).toString().toDouble())
                        actions.add(0)
                        index = i
                    }
                    '-' -> {
                        numbers.add(example.subSequence(index, i).toString().toDouble())
                        actions.add(1)
                        index = i
                    }
                    'x' -> {
                        numbers.add(example.subSequence(index, i).toString().toDouble())
                        actions.add(2)
                        index = i
                    }
                    '÷' -> {
                        numbers.add(example.subSequence(index, i).toString().toDouble())
                        actions.add(3)
                        index = i
                    }
                    '%' -> {
                        numbers.add(example.subSequence(index, i).toString().toDouble())
                        actions.add(4)
                        index = i
                    }
                }
            } else {
                when (example[i]) {
                    '+' -> {
                        numbers.add(example.subSequence(index + 1, i).toString().toDouble())
                        actions.add(0)
                        index = i
                    }
                    '-' -> {
                        numbers.add(example.subSequence(index + 1, i).toString().toDouble())
                        actions.add(1)
                        index = i
                    }
                    'x' -> {
                        numbers.add(example.subSequence(index + 1, i).toString().toDouble())
                        actions.add(2)
                        index = i
                    }
                    '÷' -> {
                        numbers.add(example.subSequence(index + 1, i).toString().toDouble())
                        actions.add(3)
                        index = i
                    }
                    '%' -> {
                        numbers.add(example.subSequence(index + 1, i).toString().toDouble())
                        actions.add(4)
                        index = i
                    }
                }
            }
        }
        numbers.add(example.subSequence(index + 1, example.length).toString().toDouble())
        var count = 0
        var result = numbers[0]
        while (count < actions.size) {
            when (actions[count]) {
                0 -> {
                    result += numbers[count + 1]
                }
                1 -> {
                    result -= numbers[count + 1]
                }
                2 -> {
                    result *= numbers[count + 1]
                }
                3 -> {
                    result /= numbers[count + 1]
                }
                4 -> {
                    result = result / 100 * numbers[count + 1]
                }
            }
            count++
        }
        val intResult: Int = result.toInt()
        if (result / intResult == 1.0 || result / intResult == 0.0) {
            textScreen.text = "${textScreen.text}= \n$intResult"
        } else {
            textScreen.text = "${textScreen.text}= \n$result"
        }
        hasResult = true
    }
}