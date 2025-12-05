package aoc2025

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2505Spec : FunSpec ({

        val example = """
            3-5
            10-14
            16-20
            12-18
            
            1
            5
            8
            11
            17
            32
        """.trimIndent().split("\n")

    test("count fresh ingredients") {
        countFreshIngredients(example).shouldBe(3)
    }

})