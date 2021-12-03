package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
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

    test("O2 generator rating") {
        table(
            headers( "inputs", "result"),
            row(listOf("0", "1"), "1"),
            row(listOf("00", "01", "10", "11"), "11"),
            row(listOf("0010", "1010", "0110", "0101"), "0110"),
            row(lines, "10111")
        ).forAll { inputs, result -> Day03.o2Generator(inputs).shouldBe(result) }
    }

    test("CO2 scrubber rating") {
        Day03.co2Scrubber(lines).shouldBe("01010")
    }
})