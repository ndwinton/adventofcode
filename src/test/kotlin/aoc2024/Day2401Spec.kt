package aoc2024

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2401Spec : FunSpec ({
    val example = """
        3   4
        4   3
        2   5
        1   3
        3   9
        3   3
        """.trimIndent()

    test("total list distance") {
        totalListDistance(example.split("\n")).shouldBe(11)
    }

    test("similarity score") {
        similarityScore(example.split("\n")).shouldBe(31L)
    }
})