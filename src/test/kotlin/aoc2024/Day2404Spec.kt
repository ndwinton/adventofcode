package aoc2024

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2404Spec : FunSpec ({
    val example = """
        MMMSXXMASM
        MSAMXMSMSA
        AMXSXMAAMM
        MSAMASMSMX
        XMASAMXAMM
        XXAMMXXAMA
        SMSMSASXSS
        SAXAMASAAA
        MAMMMXMMMM
        MXMXAXMASX
    """.trimIndent()


    test("example 1") {
        countXmases(example.split("\n")).shouldBe(18)
    }

})