package com.example.bullsandcows

class Logic {
    var message = ""
    val times = 4
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
                message = "Введите последовательность из $times уникальных цифр"
                return null
            }
        }
        val checked = mutableListOf<String>()
        repeat(times) {
            if (!checked.contains(str[it].toString())) {
                checked += str[it].toString()
            }
            else {
                message = "Введите последовательность из $times уникальных цифр"
                return null
            }
        }
        val res = mutableListOf<Int>()
        repeat(times) {
            res += str[it].toString().toInt()
        }
        message = ""
        return res
    }

    fun check(gen: List<Int>, user: List<Int>): Pair<Int, Int> {
        var cows = 0
        var bulls = 0
        for (i in gen.indices) {
            if (user[i] in gen) {
                if (gen[i] == user[i]) bulls++
                else cows++
            }
        }
        return Pair(bulls, cows)
    }
}