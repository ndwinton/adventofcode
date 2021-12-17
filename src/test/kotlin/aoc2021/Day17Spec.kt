package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldNotContainAll
import io.kotest.matchers.shouldBe

class Day17Spec : FunSpec({
    test("x position sequence") {
        xPositions(3).take(5).toList().shouldBe(listOf(
            PositionTime(3, 1), PositionTime(5, 2), PositionTime(6, 3), PositionTime(6, 4), PositionTime(6, 5)
        ))

        xPositions(-3).take(5).toList().shouldBe(listOf(
            PositionTime(-3, 1), PositionTime(-5, 2), PositionTime(-6, 3), PositionTime(-6, 4), PositionTime(-6, 5)
        ))
    }

    test("y position sequence") {
        yPositions(3).take(7).toList().shouldBe(listOf(
            PositionTime(3, 1), PositionTime(5, 2), PositionTime(6, 3), PositionTime(6, 4), PositionTime(5, 5), PositionTime(3, 6), PositionTime(0, 7)
        ))
    }

    test("x in range") {
        xInRange(20, 30).shouldContainAll(VelocityTime(6, 5), VelocityTime(6, 30), VelocityTime(7, 4), VelocityTime(9, 3))
        xInRange(20, 30).shouldNotContainAll(VelocityTime(6, 4), VelocityTime(6, 31), VelocityTime(7, 3), VelocityTime(9, 4))
    }

    test("y in range") {
        yInRange(-10, -5).shouldContainExactlyInAnyOrder(
            VelocityTime(-10, 1), VelocityTime(-9, 1), VelocityTime(-8, 1), VelocityTime(-7, 1), VelocityTime(-6, 1), VelocityTime(-5, 1),
            VelocityTime(-4, 2), VelocityTime(-3, 2), VelocityTime(-2, 2), VelocityTime(-2, 3), VelocityTime(-1, 3), VelocityTime(-1, 4),
            VelocityTime(0, 4), VelocityTime(0, 5), VelocityTime(1, 5), VelocityTime(1, 6), VelocityTime(2, 7), VelocityTime(3, 9), VelocityTime(4, 10),
            VelocityTime(5, 12), VelocityTime(6, 14), VelocityTime(7, 16), VelocityTime(8, 18), VelocityTime(9, 20)
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