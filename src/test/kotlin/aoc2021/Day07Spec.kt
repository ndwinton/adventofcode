package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day07Spec : FunSpec({
    val data = listOf(16, 1, 2, 0, 4, 2, 7, 1, 2, 14)

    test("calculating cost") {
        table(
            headers("position", "cost"),
            row(1, 41),
            row(2, 37),
            row(3, 39),
            row(10, 71)
        ).forAll { position, cost -> alignmentCost(data, position).shouldBe(cost) }
    }

    test("minimum alignment cost") {
        minimumAlignmentCost(data, ::alignmentCost).shouldBe(37)
    }

    test("calculating revised cost") {
        table(
            headers("position", "cost"),
            row(2, 206),
            row(5, 168),
        ).forAll { position, cost -> alignmentCost2(data, position).shouldBe(cost) }
    }

    test("revised minimum cost") {
        minimumAlignmentCost(data, ::alignmentCost2).shouldBe(168)
    }
})
