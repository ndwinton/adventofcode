package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2201Spec : FunSpec ({
    val example = """
            1000
            2000
            3000

            4000

            5000
            6000

            7000
            8000
            9000

            10000
        """.trimIndent()
    test("maximum in example input") {
        maximumCalories(example).shouldBe(24000)
    }

    test("top three total") {
        topThreeCaloriesTotal(example).shouldBe(45000)
    }
})