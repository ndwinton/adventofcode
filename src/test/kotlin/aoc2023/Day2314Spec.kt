package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day2314Spec : FunSpec ({
    val example = """
        O....#....
        O.OO#....#
        .....##...
        OO.#O....O
        .O.....O#.
        O.#..O.#.#
        ..O..#O..O
        .......O..
        #....###..
        #OO..#....
        """.trimIndent()

    val lines = example.split("\n")

    test("roll a line") {
        rollRockLine("O.O#.O...O").shouldBe("OO.#OO....")
    }

    test("score a line") {
        scoreRockLine("O.O#.O...O").shouldBe(24)
    }

    test("score the whole grid") {
        scoreRockGrid(lines).shouldBe(136)
    }
})






