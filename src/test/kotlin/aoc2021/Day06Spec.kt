package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day06Spec : FunSpec({
    test("fish can be created with initial state") {
        Fish(timer = 3).let { it.timer.shouldBe(3) }
    }

    test("evolving fish mid-lifecycke") {
        Fish(3).evolve().shouldBe(listOf(Fish(2)))
    }

    test("new fish spawned at day 0") {
        Fish(0).evolve().shouldBe(listOf(Fish(6), Fish(8)))
    }

    test("evolving a shoal") {
        Shoal(3, 4, 3, 1, 2).evolve().shouldBe(Shoal(2, 3, 2, 0, 1))
        Shoal(0, 1, 0, 5, 6, 7, 8).evolve().shouldBe(Shoal(6, 0, 6, 4, 5, 6, 7, 8, 8))
    }

    test("evolving a counted shoal") {
        CountedShoal(mapOf(
            0 to 2L,
            1 to 1L,
            2 to 0L,
            3 to 0L,
            4 to 0L,
            5 to 1L,
            6 to 1L,
            7 to 1L,
            8 to 1L)).evolve()
            .shouldBe(CountedShoal(mapOf(
                0 to 1L,
                1 to 0L,
                2 to 0L,
                3 to 0L,
                4 to 1L,
                5 to 1L,
                6 to 3L,
                7 to 1L,
                8 to 2L)))
    }
})