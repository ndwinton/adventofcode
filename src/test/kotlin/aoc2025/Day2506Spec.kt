package aoc2025

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2506Spec : FunSpec ({

        val example = """
        123 328  51 64 
         45 64  387 23 
          6 98  215 314
        *   +   *   +  
        """.trimIndent().split("\n")

    test("solve worksheet") {
        solveWorksheet(example).shouldBe(4277556L)
    }

    test("solve worksheet top to bottom") {
        solveWorksheetTopToBottom(example).shouldBe(3263827L)
    }
})