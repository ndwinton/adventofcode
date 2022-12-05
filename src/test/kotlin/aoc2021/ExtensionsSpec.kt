package aoc2021

import common.transpose
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ExtensionsSpec : FunSpec({
    test("list transposition") {
        listOf(listOf(1, 2, 3), listOf(4, 5, 6), listOf(7, 8, 9)).transpose()
            .shouldBe(listOf(listOf(1, 4, 7), listOf(2, 5, 8), listOf(3, 6, 9)))
    }
})