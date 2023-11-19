package com.example.bullsandcows

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.bullsandcows.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setPositiveButton("Ок", null)
        dialogBuilder.setNegativeButton("Сыграть снова") { _, _ ->
            restart()
        }
        val bac = Logic()
        var attempts = 0
        var answerShown = false
        binding.enterNumber.hint = "${bac.times} уникальных цифр"
        binding.tryGuess.alpha = 0.5F
        binding.showAnswer.alpha = 0F
        binding.showAnswerText.alpha = 0F

        var res: Pair<Int, Int>
        var user: List<Int>? = null
        val gen = bac.genNumber()
        binding.enterNumber.addTextChangedListener {
            user = bac.getInput(it.toString())
            binding.errorText.text = bac.message
            binding.tryGuess.isEnabled = user != null
            binding.tryGuess.alpha = if (user != null) 1F else 0.5F
        }

        binding.clear.setOnClickListener {
            binding.enterNumber.setText("")
        }

        binding.tryGuess.setOnClickListener {
            attempts++
            res = bac.check(gen, user!!)

            val text = TextView(this)
            binding.linearLayout.addView(text, 0)
            text.textAlignment = View.TEXT_ALIGNMENT_CENTER
            text.textSize = 24F
            text.text = "${user!!.joinToString("")} - ${res.first}Б ${res.second}К"
            text.setTextColor(getColor(R.color.white))

            binding.bullsCount.text = res.first.toString()
            binding.cowsCount.text = res.second.toString()
            if (res.first < bac.times) Toast.makeText(
                applicationContext,
                "Попробуйте еще раз",
                Toast.LENGTH_SHORT
            ).show()
            else if (!answerShown) {
                dialogBuilder.setTitle("Супер!")
                dialogBuilder.setMessage("Вы угадали последовательность $gen за $attempts попыток")
                dialogBuilder.show()
            }
            else {
                dialogBuilder.setTitle("Игра окончена")
                dialogBuilder.setMessage("За $attempts попыток Вам не удалось угадать загаданную последовательность $gen, но это не повод сдаваться!")
                dialogBuilder.show()
            }
            if (attempts >= 5) {
                binding.showAnswer.alpha = 1F
                binding.showAnswerText.alpha = 1F
                binding.history.y = binding.showAnswerText.y + binding.showAnswerText.height
            }
        }

        binding.showAnswer.setOnClickListener {
            answerShown = true
            binding.showAnswer.visibility = View.INVISIBLE
            binding.showAnswerText.text = gen.toString()
            binding.showAnswerText.y = binding.showAnswer.y
            binding.history.y = binding.showAnswerText.y + binding.showAnswerText.height
        }

        binding.restartButton.setOnClickListener {
            restart()
        }
    }

    private fun restart() {
        finish()
        startActivity(Intent(applicationContext, this::class.java))
    }
}