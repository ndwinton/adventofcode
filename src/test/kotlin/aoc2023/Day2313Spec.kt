package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day2313Spec : FunSpec ({
    val example = """
        #.##..##.
        ..#.##.#.
        ##......#
        ##......#
        ..#.##.#.
        ..##..##.
        #.#.##.#.
        
        #...##..#
        #....#..#
        ..##..###
        #####.##.
        #####.##.
        ..##..###
        #....#..#
    """.trimIndent()

    val lines1 = example.split("\n\n")[0].split("\n")
    val lines2 = example.split("\n\n")[1].split("\n")

    test("horizontal reflection") {
        horizontalReflection(lines1).shouldBe(0)
        horizontalReflection(lines2).shouldBe(4)
    }

    test("vertical reflection") {
        verticalReflection(lines1).shouldBe(5)
        verticalReflection(lines2).shouldBe(0)
    }

    test("part 1") {
        reflectionTotals(example).shouldBe(405)
    }
})



