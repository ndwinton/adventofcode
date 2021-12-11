package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day11Spec : FunSpec({
    test("step without flashes") {
        octopusStep(listOf(listOf(1, 2, 3), listOf(2, 3, 4), listOf(3, 4, 5))).shouldBe(
            listOf(listOf(2, 3, 4), listOf(3, 4, 5), listOf(4, 5, 6)))
    }

    test("step with one flash") {
        octopusStep(listOf(listOf(9, 2, 3), listOf(2, 3, 4), listOf(3, 4, 5))).shouldBe(
            listOf(listOf(0, 4, 4), listOf(4, 5, 5), listOf(4, 5, 6)))
    }

    test("step with multiple flashes") {
        octopusStep(listOf(listOf(8, 2, 3), listOf(2, 9, 4), listOf(3, 4, 8))).shouldBe(
            listOf(listOf(0, 5, 5), listOf(5, 0, 7), listOf(5, 7, 0)))
    }

    test("given example") {
        val cells = linesToCells("""
            5483143223
            2745854711
            5264556173
            6141336146
            6357385478
            4167524645
            2176841721
            6882881134
            4846848554
            5283751526
        """.trimIndent().lines())
        simulateOctopuses(cells, 10, 0).shouldBe(204)
        simulateOctopuses(cells, 100, 0).shouldBe(1656)

    }
})