package aoc2021

import io.kotest.core.Tuple3
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day02Spec : FunSpec({
    test("no-op") {
        Day02.move(emptyList(), Pair(0, 0)).shouldBe(Pair(0, 0))
    }

    test("forward") {
        Day02.move(listOf("forward 2"), Pair(0, 0)).shouldBe(Pair(2, 0))
        Day02.move(listOf("forward 2", "forward 3"), Pair(1, 0)).shouldBe(Pair(6, 0))
    }

    test("down") {
        Day02.move(listOf("down 2"), Pair(0, 0)).shouldBe(Pair(0, 2))
        Day02.move(listOf("down 2", "down 3"), Pair(1, 0)).shouldBe(Pair(1, 5))
    }

    test("up") {
        Day02.move(listOf("up 2"), Pair(0, 2)).shouldBe(Pair(0, 0))
        Day02.move(listOf("up 2", "up 3"), Pair(1, 6)).shouldBe(Pair(1, 1))
    }

    test("given example") {
        Day02.move("""
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
        """.trimIndent().lines(), Pair(0, 0)).shouldBe(Pair(15, 10))
    }


    test("forward with aim") {
        Day02.moveWithAim(listOf("forward 5"), Day02.Position(0, 0, 3))
            .shouldBe(Day02.Position(5, 15, 3))
    }

    test("move with aim") {
        Day02.moveWithAim("""
            forward 5
            down 5
            forward 8
            up 3
            down 8
            forward 2
        """.trimIndent().lines(), Day02.Position(0, 0, 0)
        ).shouldBe(Day02.Position(15, 60, 10))
    }
})