package com.example.bullsandcows

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.bullsandcows.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val bac = Logic()
    private var user: List<Int>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.enterNumber.addTextChangedListener {
            user = bac.getInput(it.toString())
            user = user!!
            binding.errorText.text = bac.message
            binding.goToGuess.isEnabled = user != null
        }
        binding.goToGuess.setOnClickListener {
            val intent = Intent(this, GuessActivity::class.java)
            intent.putExtra("user", user?.toTypedArray())
            startActivity(intent)
        }
    }
}