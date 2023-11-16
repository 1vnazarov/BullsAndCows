package com.example.bullsandcows

class Logic {
    var message = ""
    val times = R.integer.len
    fun genNumber(): List<Int> {
        val numbers = (0..9).toMutableList()
        val num = mutableListOf<Int>()
        repeat(times) {
            val index = (0..<numbers.size).random()
            num += numbers[index]
            numbers.removeAt(index)
        }
        return num
    }

    fun getInput(str: String): List<Int>? {
        if (str.isEmpty()) {
            message = ""
            return null
        }
        if (str.length < times) {
            message = "Введите последовательность из $times уникальных цифр"
            return null
        }

        str.forEach {
            if (it.toString().toIntOrNull() == null) {
                message = "Введите последовательность из четырех уникальных цифр"
                return null
            }
        }
        val checked = mutableListOf<String>()
        repeat(times) {
            if (!checked.contains(str[it].toString())) {
                checked += str[it].toString()
            }
            else {
                message = "Введите последовательность из четырех уникальных цифр"
                return null
            }
        }
        val res = mutableListOf<Int>()
        repeat(times) {
            res += str[it].toString().toInt()
        }
        return res
    }

    fun check(gen: List<Int>, user: List<Int>): Pair<Int, Int> {
        var cows = 0
        var bulls = 0
        val guessed = mutableSetOf<Int>()
        for (i in gen.indices) {
            if (gen[i] == user[i]) bulls++
            else guessed.add(gen[i])
        }
        for (i in guessed.indices) {
            if (gen[i] != user[i] && user[i] in guessed) {
                cows++
                guessed.remove(gen[i])
            }
        }
        return Pair(bulls, cows)
    }
}