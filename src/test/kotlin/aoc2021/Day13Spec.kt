package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day13Spec : FunSpec({
    val example = """
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0

        fold along y=7
        fold along x=5
    """.trimIndent().lines()

    test("build sheet") {
        buildSheet(example).shouldBe(listOf(
            listOf(0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0),
            listOf(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1),
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            listOf(0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0),
            listOf(0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0),
            listOf(0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1),
            listOf(1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
            listOf(1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
        ))
    }

    test("folding a sheet horizontally") {
        foldHorizontally(listOf(
            listOf(1, 0, 1, 0),
            listOf(1, 0, 0, 1),
            listOf(0, 0, 0, 0),
            listOf(0, 0, 0, 0),
            listOf(1, 1, 1, 1),
        )).shouldBe(listOf(
            listOf(2, 1, 2, 1),
            listOf(1, 0, 0, 1),
        ))
    }
    
    test("folding vertically") {
        foldVertically(listOf(
            listOf(1, 0, 0, 0, 1),
            listOf(1, 0, 0, 1, 1),
            listOf(0, 0, 0, 0, 1),
            listOf(0, 0, 0, 0, 1),
            listOf(1, 1, 0, 1, 1),
        )).shouldBe(listOf(
            listOf(2, 0),
            listOf(2, 1),
            listOf(1, 0),
            listOf(1, 0),
            listOf(2, 2),
        ))
    }
})


