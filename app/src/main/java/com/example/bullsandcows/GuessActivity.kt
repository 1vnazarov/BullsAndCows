package com.example.bullsandcows

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bullsandcows.databinding.ActivityGuessBinding

class GuessActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        val user = intent.extras?.getIntegerArrayList("user")?.toList()
        super.onCreate(savedInstanceState)
        binding = ActivityGuessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tryGuess.setOnClickListener {
            val intent = Intent(this, GuessActivity::class.java)
            startActivity(intent)
        }
    }
}