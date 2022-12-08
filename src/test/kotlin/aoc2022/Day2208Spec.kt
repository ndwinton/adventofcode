package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2208Spec : FunSpec({
    val input = """
        30373
        25512
        65332
        33549
        35390
    """.trimIndent().split("\n")

    test("row visibility L to R") {
        rowVisibilityLeftToRight(listOf(2, 5, 5, 1, 2)).shouldBe(listOf(true, true, false, false, false))
        rowVisibilityLeftToRight(listOf(2, 1, 5, 5, 2)).shouldBe(listOf(true, false, true, false, false))
    }

    test("whole row visibility") {
        rowVisibility(listOf(2, 5, 5, 1, 2)).shouldBe(listOf(true, true, true, false, true))
    }

    test("input to lists") {
        parseTreeHeightMap(input).shouldBe(listOf(
            listOf(3, 0, 3, 7, 3),
            listOf(2, 5, 5, 1, 2),
            listOf(6, 5, 3, 3, 2),
            listOf(3, 3, 5, 4, 9),
            listOf(3, 5, 3, 9, 0)
        ))
    }

    test("whole map visibility") {
        heightMapVisbility(parseTreeHeightMap(input)).shouldBe(listOf(
            listOf(true, true, true, true, true),
            listOf(true, true, true, false, true),
            listOf(true, true, false, true, true),
            listOf(true, false, true, false, true),
            listOf(true, true, true, true, true),
            ))
    }

    test("visibility count") {
        visibilityCount(input).shouldBe(21)
    }

    test("scenic scores for row looking right") {
        scenicScoresLookingRight(listOf(2, 5, 5, 1, 2)).shouldBe(listOf(1, 1, 2, 1, 0))
        scenicScoresLookingRight(listOf(2, 1, 5, 5, 2)).shouldBe(listOf(2, 1, 1, 1, 0))
    }

    test("scenic scores for row") {
        scenicScoresForRow(listOf(2, 5, 5, 1, 2)).shouldBe(listOf(0, 1, 2, 1, 0))
    }

    test("scenic scores for entire map") {
        heightMapScenicScores(parseTreeHeightMap(input)).shouldBe(listOf(
            listOf(0, 0, 0, 0, 0),
            listOf(0, 1, 4, 1, 0),
            listOf(0, 6, 1, 2, 0),
            listOf(0, 1, 8, 3, 0),
            listOf(0, 0, 0, 0, 0),
        ))
    }

    test("max scenic score") {
        maxScenicScore(input).shouldBe(8)
    }
})


