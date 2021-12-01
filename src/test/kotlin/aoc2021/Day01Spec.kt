package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day01Spec : FunSpec({

    test("empty list") {
        Day01.numberOfIncreases(emptyList()).shouldBe(0)
    }

    test("single increase") {
        Day01.numberOfIncreases(listOf(1, 2)).shouldBe(1)
    }

    test("given values") {
        Day01.numberOfIncreases(listOf(199, 200, 208, 210,200, 207, 240, 269, 260, 263)).shouldBe(7)
    }

    test("windowed") {
        Day01.numberOfIncreases(listOf(199, 200, 208, 210,200, 207, 240, 269, 260, 263), 3).shouldBe(5)
    }
})