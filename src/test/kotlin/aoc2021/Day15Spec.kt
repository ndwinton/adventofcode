package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day15Spec : FunSpec({
    val example = """
        1163751742
        1381373672
        2136511328
        3694931569
        7463417111
        1319128137
        1359912421
        3125421639
        1293138521
        2311944581
    """.trimIndent().lines()

    test("basic example") {
        GridNode.loadGrid(example)
        val path = aStar(GridNode.getNode(0, 0), GridNode.getNode(9, 9), ::manhattan)
        path.size.shouldBe(19)
        path.map { it.weight(it) }.shouldBe(listOf(1, 1, 2, 1, 3, 6, 5, 1, 1, 1, 5, 1, 1, 3, 2, 3, 2, 1, 1))
    }
})