package aoc2025

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2510Spec : FunSpec ({

        val example = """
            [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
            [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
            [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """.trimIndent().split("\n")

    test("parse indicator value") {
        parseIndicatorInstructions(example[0]).shouldBe(IndicatorInstructions(0b0110, listOf(0b1000, 0b1010, 0b0100, 0b1100, 0b0101, 0b0011), listOf(3, 5, 4, 7)))
        parseIndicatorInstructions(example[1]).shouldBe(IndicatorInstructions(0b01000, listOf(0b11101, 0b01100, 0b10001, 0b00111, 0b11110), listOf(7, 5, 12, 7, 2)))
    }

    test("finding fewest presses") {
        findFewestPresses(parseIndicatorInstructions(example[0])).shouldBe(2)
        findFewestPresses(parseIndicatorInstructions(example[1])).shouldBe(3)
        findFewestPresses(parseIndicatorInstructions(example[2])).shouldBe(2)
    }

    test("total for input") {
        findTotalFewestPressesForInput(example).shouldBe(7)
    }
})