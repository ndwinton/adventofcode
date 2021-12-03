package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day03Spec : FunSpec({
    val lines = """
        00100
        11110
        10110
        10111
        10101
        01111
        00111
        11100
        10000
        11001
        00010
        01010
    """.trimIndent().lines()

    test("given values") {
        Day03.gammaEpsilon(lines).shouldBe(Pair(22, 9))
    }

    test("gamma * epsilon") {
        Day03.gammaEpsilonProduct(lines).shouldBe(22 * 9)
    }
})