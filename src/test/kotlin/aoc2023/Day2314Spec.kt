package aoc2023

import io.kotest.core.spec.style.FunSpec
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
        rollGridAndScore(lines).shouldBe(136)
    }

    test("spin cycles") {
//        spinCycle(lines, 1).shouldBe(
//            """
//            .....#....
//            ....#...O#
//            ...OO##...
//            .OO#......
//            .....OOO#.
//            .O#...O#.#
//            ....O#....
//            ......OOOO
//            #...O###..
//            #..OO#....
//        """.trimIndent().split("\n")
//        )
//
//
//        spinCycle(lines, 3).shouldBe(
//            """
//            .....#....
//            ....#...O#
//            .....##...
//            ..O#......
//            .....OOO#.
//            .O#...O#.#
//            ....O#...O
//            .......OOO
//            #...O###.O
//            #.OOO#...O
//        """.trimIndent().split("\n")
//        )
//
//        (1 .. 20).forEach {
//            val g = spinCycle(lines, it)
//            val s = scoreGrid(g)
//            println("--- $it -> $s")
//            g.forEach { println(it) }
//        }
        //scoreGrid(spinCycle(lines, 100)).shouldBe(68)
        scoreGrid(spinCycle(lines)).shouldBe(64)
    }
})






