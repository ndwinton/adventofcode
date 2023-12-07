package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

class Day2307Spec : FunSpec ({
    val example = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
        """.trimIndent()

        val lines = example.split("\n")

    test("parse input") {
        parseCamelCardsHands(lines).shouldBe(listOf(
            Hand("32T3K", 765),
            Hand("T55J5", 684),
            Hand("KK677", 28),
            Hand("KTJJT", 220),
            Hand("QQQJA", 483),
        ))
    }

    test("comparisons") {
        Hand("QQQJA", 483).compareTo(Hand("KTJJT", 220)).shouldBeGreaterThan(0)
    }

    test("part 1 score") {
        camelCardsScore(lines).shouldBe(6440)
    }
})
