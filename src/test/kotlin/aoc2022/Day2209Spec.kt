package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2209Spec : FunSpec({
    val input = """
        R 4
        U 4
        L 3
        D 1
        R 4
        D 1
        L 5
        R 2
    """.trimIndent().split("\n")

    test("parse commands") {
        parseRopeSteps(listOf("R 4", "U 4")).shouldBe(listOf(
            Pair("R", 4),
            Pair("U", 4)
        ))
    }

    test("initial position") {
        ropeTailSteps(emptyList()).shouldBe(listOf(Pos(0, 0)))
    }

    test("going right") {
        ropeTailSteps(listOf(Pair("R", 3))).shouldBe(listOf(Pos(0, 0), Pos(1, 0), Pos(2, 0)))
    }

    test("going left") {
        ropeTailSteps(listOf(Pair("L", 3))).shouldBe(listOf(Pos(0, 0), Pos(-1, 0), Pos(-2, 0)))
    }

    test("going up") {
        ropeTailSteps(listOf(Pair("U", 3))).shouldBe(listOf(Pos(0, 0), Pos(0, 1), Pos(0, 2)))
    }

    test("going diagonal") {
        ropeTailSteps(listOf(Pair("U", 1), Pair("R", 1), Pair("U", 1))).shouldBe(listOf(Pos(0, 0), Pos(1, 1)))
    }

    test("sample input") {
        ropeTailStepsCount(input).shouldBe(13)
    }
})


