package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldNotContainAll
import io.kotest.matchers.shouldBe

class Day17Spec : FunSpec({
    test("x position sequence") {
        xPositions(3).take(5).toList().shouldBe(listOf(
            Pair(3, 1), Pair(5, 2), Pair(6, 3), Pair(6, 4), Pair(6, 5)
        ))

        xPositions(-3).take(5).toList().shouldBe(listOf(
            Pair(-3, 1), Pair(-5, 2), Pair(-6, 3), Pair(-6, 4), Pair(-6, 5)
        ))
    }

    test("y position sequence") {
        yPositions(3).take(7).toList().shouldBe(listOf(
            Pair(3, 1), Pair(5, 2), Pair(6, 3), Pair(6, 4), Pair(5, 5), Pair(3, 6), Pair(0, 7)
        ))
    }

    test("x in range") {
        xInRange(20, 30).shouldContainAll(Pair(6, 5), Pair(6, 30), Pair(7, 4), Pair(9, 3))
        xInRange(20, 30).shouldNotContainAll(Pair(6, 4), Pair(6, 31), Pair(7, 3), Pair(9, 4))
    }

    test("y in range") {
        yInRange(-10, -5).shouldContainExactlyInAnyOrder(
            Pair(-10, 1), Pair(-9, 1), Pair(-8, 1), Pair(-7, 1), Pair(-6, 1), Pair(-5, 1),
            Pair(-4, 2), Pair(-3, 2), Pair(-2, 2), Pair(-2, 3), Pair(-1, 3), Pair(-1, 4),
            Pair(0, 4), Pair(0, 5), Pair(1, 5), Pair(1, 6), Pair(2, 7), Pair(3, 9), Pair(4, 10),
            Pair(5, 12), Pair(6, 14), Pair(7, 16), Pair(8, 18), Pair(9, 20)
        )
    }

    test("max y velocity") {
        findMaxYVelocity(xInRange(20, 30), yInRange(-10, -5)).shouldBe(9)
    }

    test("maximum height") {
        findMaximumHeight(20, 30, -10, -5).shouldBe(45)
    }

    test("distinct velocities") {
        totalDistinctVelocities(20, 30, -10, -5).shouldBe(112)
    }
})