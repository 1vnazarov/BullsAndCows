package com.example.bullsandcows

import android.content.Intent
import android.os.Bundle
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
        binding.enterNumber.hint = "Введите последовательность из ${bac.times} уникальных цифр"
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
        binding.tryGuess.setOnClickListener {
            attempts++
            res = bac.check(gen, user!!)
            binding.bullsCount.text = res.first.toString()
            binding.cowsCount.text = res.second.toString()
            if (res.first < 4) Toast.makeText(
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
            }
        }

        binding.showAnswer.setOnClickListener {
            answerShown = true
            binding.root.removeView(binding.showAnswer)
            binding.showAnswerText.text = gen.toString()
            binding.showAnswerText.x = -400F
            binding.showAnswerText.y = 1500F
        }

        binding.restartButton.setOnClickListener {
            restart()
        }
    }

    private fun restart() {
        startActivity(Intent(applicationContext, this::class.java))
    }

}