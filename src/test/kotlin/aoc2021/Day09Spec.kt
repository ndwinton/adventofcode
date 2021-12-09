package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day09Spec : FunSpec({

    test("create heightmap") {
        HeightMap(listOf("123", "456", "789", "012")).let {
            it.width.shouldBe(3)
            it.height.shouldBe(4)
        }
    }

    test("evaluate minimum at location") {
        val hm = HeightMap(listOf("123", "416", "789", "012"))
        table(
            headers("row", "col", "result"),
            row(0, 0, true),
            row(0, 1, false),
            row(1, 1, true),
            row(2, 1, false),
            row(3, 0, true)
        ).forAll { row, col, result -> hm.isLocalMinimum(row, col).shouldBe(result) }
    }

    test("risk level sum") {
        HeightMap(
            """
                2199943210
                3987894921
                9856789892
                8767896789
                9899965678
            """.trimIndent().lines()
        ).sumRiskLevels().shouldBe(15)
    }
})