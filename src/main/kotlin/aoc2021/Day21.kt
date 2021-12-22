package aoc2021

import kotlin.math.min

interface DiracDie {
    val rolls: Int
    fun roll(): Int
}

class DeterministicDie(var rollCount: Int = 0) : DiracDie {
    override val rolls: Int
        get() = rollCount

    override fun roll(): Int {
        val result = (rollCount % 100) + 1
        rollCount++
        return result
    }
}

class DiracPlayer(start: Int, private val die: DiracDie) {
    private var position = start
    private var currentScore = 0
    fun play() {
        val roll = (1 .. 3).sumOf { die.roll() }
        position = (position + roll) % 10
        currentScore += (position + (if (position == 0) 10 else 0))
    }

    val score: Int
        get() = currentScore
}

fun playDirac(die: DiracDie, start1: Int, start2: Int): Int {
    val player1 = DiracPlayer(start1, die)
    val player2 = DiracPlayer(start2, die)
    while (true) {
        player1.play()
        if (player1.score >= 1000) break
        player2.play()
        if (player2.score >= 1000) break
    }
    return min(player1.score, player2.score) * die.rolls
}