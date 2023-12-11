package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe

class Day2311Spec : FunSpec ({
    val example = """
        ...#......
        .......#..
        #.........
        ..........
        ......#...
        .#........
        .........#
        ..........
        .......#..
        #...#.....
    """.trimIndent()

    val lines = example.split("\n")

    val example2 = """
        """.trimIndent()

    val lines2 = example2.split("\n")

    test("expand universe") {
        expandUniverse(lines).shouldBe("""
            ....#........
            .........#...
            #............
            .............
            .............
            ........#....
            .#...........
            ............#
            .............
            .............
            .........#...
            #....#.......
        """.trimIndent().split("\n"))
    }

    test("find galaxies") {
        findGalaxies(expandUniverse(lines)).shouldBe(
            listOf(
                Coord(row=0, col=4),
                Coord(row=1, col=9),
                Coord(row=2, col=0),
                Coord(row=5, col=8),
                Coord(row=6, col=1),
                Coord(row=7, col=12),
                Coord(row=10, col=9),
                Coord(row=11, col=0),
                Coord(row=11, col=5)
            )
        )
    }

    test("pairings") {
        pairings(findGalaxies(lines)).size.shouldBe(36)
    }

    test("shortest sum") {
        shortestDistanceSum(lines).shouldBe(374)
    }
})
