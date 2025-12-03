package aoc2025

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2503Spec : FunSpec ({

        val example = """
            987654321111111
            811111111111119
            234234234234278
            818181911112111
        """.trimIndent().split("\n")

    test("joltage of banks") {
        joltageOfBank("987654321111111").shouldBe(98)
        joltageOfBank("811111111111119").shouldBe(89)
        joltageOfBank("234234234234278").shouldBe(78)
        joltageOfBank("818181911112111").shouldBe(92)
    }

    test("total joltage") {
        totalJoltageOfBanks(example).shouldBe(357)
    }
})