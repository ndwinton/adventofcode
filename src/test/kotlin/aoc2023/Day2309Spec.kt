package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2309Spec : FunSpec ({
    val example = """

        """.trimIndent()

        val lines = example.split("\n")

    test("predict previous") {
        predictPrev(listOf(10L, 13L, 16L, 21L, 30L, 45L)).shouldBe(5L)
    }
})
