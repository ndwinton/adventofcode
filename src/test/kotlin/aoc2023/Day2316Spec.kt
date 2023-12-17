package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day2316Spec : FunSpec ({
    val example = """
        .|...\....
        |.-.\.....
        .....|-...
        ........|.
        ..........
        .........\
        ..../.\\..
        .-.-/..|..
        .|....-|.\
        ..//.|....
    """.trimIndent()

    val lines = example.split("\n")

    test("grid energy") {
        runBeamSimulation(lines).shouldBe(46)
    }

    test("max energy") {
        maximumEnergized(lines).shouldBe(51)
    }
})

