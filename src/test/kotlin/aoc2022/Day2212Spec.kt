package aoc2022

import common.aStar
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2212Spec : FunSpec({
    val example = """
        Sabqponm
        abcryxxl
        accszExk
        acctuvwj
        abdefghi
    """.trimIndent().lines()

    test("basic example") {
        HeightMapNode.loadGrid(example)
        val path = aStar(HeightMapNode.nodeWithHeight('S'), HeightMapNode.nodeWithHeight('E'), ::manhattan)
        (path.size - 1).shouldBe(31)
    }

    test("wrapper") {
        fewestStepsToEndpoint(example).shouldBe(31)
    }
})