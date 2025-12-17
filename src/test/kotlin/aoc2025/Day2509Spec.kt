package aoc2025

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2509Spec : FunSpec ({

        val example = """
            7,1
            11,1
            11,7
            9,7
            9,5
            2,5
            2,3
            7,3
        """.trimIndent().split("\n")

    test("largest red rectangle") {
        largestRedRectangle(example).shouldBe(50)
    }

    test("largest enclosed rectangle") {
        largestEnclosedRectangle(example).shouldBe(24)
    }
})