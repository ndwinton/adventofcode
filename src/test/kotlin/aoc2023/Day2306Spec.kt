package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day2306Spec : FunSpec ({
    val example = """
        Time:      7  15   30
        Distance:  9  40  200
        """.trimIndent()

        val lines = example.split("\n")

    test("parse input") {
        parseRaceData(lines).shouldBe(listOf(Pair(7, 9), Pair(15, 40), Pair(30, 200)))
    }

    test("distance moved in time for") {
        table(
            headers("press", "result"),
            row(0, 0),
            row(1, 6),
            row(2, 10),
            row(3, 12),
            row(4, 12),
            row(5, 10),
            row(6, 6),
            row(7, 0)
        ).forAll { press, result ->
            distanceMovedForButtonPress(press = press, raceDuration = 7).shouldBe(result)
        }
    }

    test("number of winning times") {
        numberOfWinningTimes(7, 9).shouldBe(4)
    }
    test("part 1 answer") {
        boatRaceAnswerPart1(lines).shouldBe(288)
    }

    test("part 2 answer") {
        boatRaceAnswerPart2(lines).shouldBe(71503L)
    }
})
