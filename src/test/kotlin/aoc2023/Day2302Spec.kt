package aoc2023

import aoc2021.Tuple3
import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day2302Spec : FunSpec ({
    val example = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
        """.trimIndent()

    test("parse lines") {
        table(
            headers("input", "output"),
            row("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                listOf(Tuple3(4, 0, 3), Tuple3(1, 2, 6), Tuple3(0, 2, 0))),
            row("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                listOf(Tuple3(20, 8, 6), Tuple3(4, 13, 5), Tuple3(1, 5, 0))),
        ).forAll { input, output -> parseRGBTuples(input).shouldBe(output)}
    }

    test("select by limit tuple") {
        possibleLineIndexSum(example.split("\n"), Tuple3(12, 13, 14)).shouldBe(8)
    }

    test("game power") {
        table(
            headers("input", "output"),
            row("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green", 48),
            row("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red", 1560)
        ).forAll { input, output -> powerOfMinPossibleTuple(input).shouldBe(output)}
    }
})