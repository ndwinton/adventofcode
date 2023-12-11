package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe

class Day2310Spec : FunSpec ({
    val example = """
        ..F7.
        .FJ|.
        SJ.L7
        |F--J
        LJ...
        """.trimIndent()

    val lines = example.split("\n")

    val example2 = """
        FF7FSF7F7F7F7F7F---7
        L|LJ||||||||||||F--J
        FL-7LJLJ||||||LJL-77
        F--JF--7||LJLJ7F7FJ-
        L---JF-JLJ.||-FJLJJ7
        |F|F-JF---7F7-L7L|7|
        |FFJF7L7F-JF7|JL---7
        7-L-JL7||F7|L7F-7F7|
        L.L7LFJ|||||FJL7||LJ
        L7JLJL-JLJLJL--JLJ.L
        """.trimIndent()

    val lines2 = example2.split("\n")

    test("find start") {
        findStart(lines).shouldBe(Coord(2, 0))
    }

    test("find pipe length") {
        findPipeLength(lines).shouldBe(8)
    }

    test("count enclosed") {
        countEnclosed(lines2).shouldBe(10)
        countEnclosed(lines).shouldBe(1)
    }
})
