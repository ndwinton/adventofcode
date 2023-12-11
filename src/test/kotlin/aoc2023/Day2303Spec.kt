package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe
import common.get

class Day2303Spec : FunSpec ({
    val example = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...${'$'}.*....
        .664.598..
        """.trimIndent()

        val lines = example.split("\n")

    test("accessing elements of input lines") {
        table(
            headers("row", "col", "result"),
            row(0, 0, '4'),
            row(8, 3, '$'),
            row(-1, -1, '.'),
            row(-1, 2, '.'),
            row(2, 100, '.')
        ).forAll { row, col, result -> lines[row, col].shouldBe(result) }
    }

    test("find part numbers per row") {
        findPartNumbersPerRow(lines).shouldBe(listOf(
            listOf(467),
            listOf(),
            listOf(35, 633),
            listOf(),
            listOf(617),
            listOf(),
            listOf(592),
            listOf(755),
            listOf(),
            listOf(664, 598)
        ))
    }

    test("sum of part numbers") {
        sumOfPartNumbers(lines).shouldBe(4361)
    }
})