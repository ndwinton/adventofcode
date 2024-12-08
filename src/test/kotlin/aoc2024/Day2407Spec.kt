package aoc2024

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2407Spec : FunSpec ({
    val example = """
        190: 10 19
        3267: 81 40 27
        83: 17 5
        156: 15 6
        7290: 6 8 6 15
        161011: 16 10 13
        192: 17 8 14
        21037: 9 7 18 13
        292: 11 6 16 20""".trimIndent()

    test("solvable calibrations") {
        solvableCalibrationsTotal(example.split("\n")).shouldBe(3749)
    }

    test("solvable calibrations with concat") {
        solvableCalibrationsTotal(example.split("\n"), true).shouldBe(11387)
    }
})