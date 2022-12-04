package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2204Spec : FunSpec ({

    test("parsing input lines") {
        parseToPairOfRanges("2-4,6-8").shouldBe(Pair((2.. 4), (6 .. 8)))
    }

    test("fully contains") {
        rangePairHasFullContainment(parseToPairOfRanges("6-6,4-6")).shouldBe(true)
        rangePairHasFullContainment(parseToPairOfRanges("2-6,4-8")).shouldBe(false)
    }

    test("overlapping") {
        rangePairHasOverlap(parseToPairOfRanges("6-6,4-6")).shouldBe(true)
        rangePairHasOverlap(parseToPairOfRanges("2-6,4-8")).shouldBe(true)
        rangePairHasOverlap(parseToPairOfRanges("2-3,4-8")).shouldBe(false)
    }
})