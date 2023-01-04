package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe

class Day2214Spec : FunSpec({

    val example = """
        498,4 -> 498,6 -> 496,6
        503,4 -> 502,4 -> 502,9 -> 494,9
    """.trimIndent().split("\n")

    test("parsing coord line") {
        parseCoordLine("495,81 -> 495,83 -> 494,83 -> 494,91").shouldBe(listOf(
            Coord(495, 81),
            Coord(495,83),
            Coord(494,83),
            Coord(494,91)
        ))
    }

    test("making grid from input sets bounds") {
        val grid = loadRockGrid(example)
        grid.bounds.shouldBe(Pair(Coord(494,0), Coord(503, 9)))
    }

    test("contents outside grid") {
        val grid = loadRockGrid(example)
        grid[0, 0].shouldBe("?")
        grid[504, 9].shouldBe("?")
    }

    test("contents in grid") {
        val grid = loadRockGrid(example)
        table(
            headers("x", "y", "contents"),
            row(498, 5, "#"),
            row(498, 6, "#"),
            row(500, 9, "#"),
            row(499, 8, " ")
        ).forAll { x, y, contents -> grid[x, y].shouldBe(contents) }
    }

    test("initial grid contains no sand") {
        loadRockGrid(example).sand.shouldBe(emptySet())
    }

    test("adding first grain produces new grid") {
        val before = loadRockGrid(example)
        val after = before.addGrain()
        after.sand.size.shouldBe(1)
        after[500, 8].shouldBe("o")
    }

    test("grain falls left") {
        val before = loadRockGrid(example)
        val after = before.addGrain().addGrain()
        after.sand.size.shouldBe(2)
        after[500, 8].shouldBe("o")
        after[499, 8].shouldBe("o")
    }

    test("grain falls right") {
        val before = loadRockGrid(example)
        val after = before.addGrain().addGrain().addGrain()
        after.sand.size.shouldBe(3)
        after[500, 8].shouldBe("o")
        after[499, 8].shouldBe("o")
        after[501, 8].shouldBe("o")
    }

    test("fill grid") {
        val empty = loadRockGrid(example)
        val filled = empty.fill()
        filled.sand.size.shouldBe(24)
    }

    test("part 2") {
    }
})