package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day2203Spec : FunSpec ({

    test("rucksack priority") {
        table(
            headers("rucksack", "priority"),
            row("vJrwpWtwJgWrhcsFMMfFFhFp", 16),
            row("jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL", 38)
        ).forAll() { rucksack, priority ->
            rucksackPriority(rucksack).shouldBe(priority)
        }
    }

    test("sample data") {
        rucksackPriorityTotal("""
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent().split("\n")).shouldBe(157)
    }

    test("badge total") {
        rucksackBadgeTotal("""
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
        """.trimIndent().split("\n")).shouldBe(70)
    }
})