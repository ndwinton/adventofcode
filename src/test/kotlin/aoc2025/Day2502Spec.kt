package aoc2025

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2502Spec : FunSpec ({
    val example = """11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"""


    test("find in range") {
        invalidIdsInRange(11L..22L).shouldBe(listOf(11L, 22L))
        invalidIdsInRange(95L..115L).shouldBe(listOf(99L))
        invalidIdsInRange(998L..1012L).shouldBe(listOf(1010L))
    }

    test ("sum of all in data") {
        sumOfInvalidIdsInRangeData(example).shouldBe(1227775554L)
    }
})