package aoc2024

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2402Spec : FunSpec ({
    val example = """
        7 6 4 2 1
        1 2 7 8 9
        9 7 6 2 1
        1 3 2 4 5
        8 6 4 4 1
        1 3 6 7 9
        """.trimIndent()

    test("level is safe") {
        example.split("\n").map {
            isLevelSafe(inputLineAsIntList(it))
        }.shouldBe(listOf(true, false, false, false, false, true))
    }

    test("dampen level") {
        levelDampened(
            listOf(1, 2, 3, 4)).shouldBe(listOf(
            listOf(1, 2, 3, 4),
            listOf(2, 3, 4),
            listOf(1, 3, 4),
            listOf(1, 2, 4),
            listOf(1, 2, 3)))
    }

    test("dampened level is safe") {
        example.split("\n").map {
            isDampenedLevelSafe(inputLineAsIntList(it))
        }.shouldBe(listOf(true, false, false, true, true, true))
    }
})