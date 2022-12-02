package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day2202Spec : FunSpec ({
    test("score for individual line") {
        table(
            headers("input", "score"),
            row("A Y", 8),
            row("B X", 1),
            row("C Z", 6)
        ).forAll() { input, result ->
            scoreForRPSLine(input).shouldBe(result)
        }
    }

    test("total score") {
        totalScoreForRPSLines("""
            A Y
            B X
            C Z
        """.trimIndent().split("\n")).shouldBe(15)
    }
})