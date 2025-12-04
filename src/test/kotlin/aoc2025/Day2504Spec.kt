package aoc2025

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2504Spec : FunSpec ({

        val example = """
            ..@@.@@@@.
            @@@.@.@.@@
            @@@@@.@.@@
            @.@@@@..@.
            @@.@@@@.@@
            .@@@@@@@.@
            .@.@.@.@@@
            @.@@@.@@@@
            .@@@@@@@@.
            @.@.@@@.@.
        """.trimIndent().split("\n")

    test("count accessible rolls") {
        countAccessibleRolls(example).shouldBe(13)
    }

    test("count removed rolls"){
        countRemovedRolls(example).shouldBe(43)
    }
})