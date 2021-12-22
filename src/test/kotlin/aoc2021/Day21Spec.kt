package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day21Spec : FunSpec({
    test("deterministic die roll") {
        val die: DiracDie = DeterministicDie()
        die.roll().shouldBe(1)
        die.roll().shouldBe(2)
        die.rolls.shouldBe(2)
    }

    test("deterministic die starts again after 100") {
        val die: DiracDie = DeterministicDie()
        (1 .. 100).forEach { die.roll() }
        die.roll().shouldBe(1)
        die.roll().shouldBe(2)
        die.rolls.shouldBe(102)
    }

    test("player setup") {
        val die = DeterministicDie()
        val player = DiracPlayer(4, die)
        player.score.shouldBe(0)
    }

    test("player scoring") {
        val die = DeterministicDie()
        val player1 = DiracPlayer(4, die)
        val player2 = DiracPlayer(8, die)
        player1.play()
        player1.score.shouldBe(10)
        player2.play()
        player2.score.shouldBe(3)
        player1.play()
        player1.score.shouldBe(14)
        player2.play()
        player2.score.shouldBe(9)
        player1.play()
        player1.score.shouldBe(20)
        player2.play()
        player2.score.shouldBe(16)
        player1.play()
        player1.score.shouldBe(26)
        player2.play()
        player2.score.shouldBe(22)
    }

    test("play deterministic game") {
        val die2 = DeterministicDie(0)
        playDirac(die2, 4, 8).shouldBe(739785)
    }
})