package aoc2025

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2501Spec : FunSpec ({
    val example = """
        L68
        L30
        R48
        L5
        R60
        L55
        L1
        L99
        R14
        L82
        """.trimIndent()

    test("number of stops on zero") {
        numberOfStopsOnZero(example.split("\n")).shouldBe(3)
    }

    test("number of passes through zero") {
        numberOfPassesThroughZero(example.split("\n")).shouldBe(6)

    }
})