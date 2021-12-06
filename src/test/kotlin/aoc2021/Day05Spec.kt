package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe

class Day05Spec : FunSpec({
    test("parse line") {
        Day05.parseLine("1,1 -> 2,3").shouldBe(Line(Point(1, 1), Point(2, 3)))
    }

    test("horizontality") {
        Line(Point(1, 1), Point(2, 2)).isHorizontal().shouldBe(false)
        Line(Point(1, 1), Point(2, 1)).isHorizontal().shouldBe(true)
    }

    test("verticality") {
        Line(Point(1, 1), Point(2, 2)).isVertical().shouldBe(false)
        Line(Point(1, 1), Point(1, 2)).isVertical().shouldBe(true)
    }

    test("generating points on horizontal line") {
        Line(Point(2, 3), Point(5, 3)).points.shouldBe(listOf(
            Point(2, 3), Point(3,3), Point(4, 3), Point(5, 3)
        ))
    }

    test("generating points on vertical line") {
        Line(Point(2, 5), Point(2, 3)).points.shouldBe(listOf(
            Point(2, 5), Point(2,4), Point(2, 3)))
    }

    test("count of intersections") {
        Day05.countIntersections(listOf(
            "2,2 -> 2,1",
            "7,4 -> 7,0",
            "1,4 -> 10,4",
            "4,4 -> 4,4",
            "0,10 -> 2,10",
            "0,10 -> 5,10")).shouldBe(5)
    }

    test("generating diagonal -- LL-UR") {
        Line(Point(1, 2), Point(3, 4)).points.shouldBe(listOf(
            Point(1, 2),
            Point(2, 3),
            Point(3, 4)
        ))
    }

    test("generating diagonal -- UL-LR") {
        Line(Point(1, 4), Point(3, 2)).points.shouldBe(listOf(
            Point(1, 4),
            Point(2, 3),
            Point(3, 2)
        ))
    }
})