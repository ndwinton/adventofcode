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
        joltageOfBank("987654321111111").shouldBe(98L)
        joltageOfBank("811111111111119").shouldBe(89L)
        joltageOfBank("234234234234278").shouldBe(78L)
        joltageOfBank("818181911112111").shouldBe(92L)
    }

    test("joltage of banks with expanded size") {
        joltageOfBank("987654321111111", 12).shouldBe(987654321111L)
        joltageOfBank("811111111111119", 12).shouldBe(811111111119L)
        joltageOfBank("234234234234278", 12).shouldBe(434234234278L)
        joltageOfBank("818181911112111", 12).shouldBe(888911112111L)
    }

    test("total joltage") {
        totalJoltageOfBanks(example).shouldBe(357L)
        totalJoltageOfBanks(example, 12).shouldBe(3121910778619L)
    }
})