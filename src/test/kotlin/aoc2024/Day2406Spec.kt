package aoc2024

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2406Spec : FunSpec ({
    val example = """
        ....#.....
        .........#
        ..........
        ..#.......
        .......#..
        ..........
        .#..^.....
        ........#.
        #.........
        ......#...""".trimIndent()

    test("distinct visited") {
        distinctVisited(example.split("\n")).shouldBe(41)
    }
})