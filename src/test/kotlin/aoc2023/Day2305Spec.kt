package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day2305Spec : FunSpec ({
    val example = """
        seeds: 79 14 55 13
        
        seed-to-soil map:
        50 98 2
        52 50 48
        
        soil-to-fertilizer map:
        0 15 37
        37 52 2
        39 0 15
        
        fertilizer-to-water map:
        49 53 8
        0 11 42
        42 0 7
        57 7 4
        
        water-to-light map:
        88 18 7
        18 25 70
        
        light-to-temperature map:
        45 77 23
        81 45 19
        68 64 13
        
        temperature-to-humidity map:
        0 69 1
        1 0 69
        
        humidity-to-location map:
        60 56 37
        56 93 4
        """.trimIndent()

        val lines = example.split("\n")

    test("chunk input") {
        example.split(Regex("""\n\n""")).size.shouldBe(8)
    }

    test("parsing almanac") {
        val almanac = parseAlmanac(example)
        almanac.seeds.shouldBe(listOf(79L, 14L, 55L, 13L))
        almanac.groups[0].transforms[0].shouldBe(Transform((98L..99L), -48L))
    }

    test("apply transforms") {
        val almanac = parseAlmanac(example)
        applySeedTransforms(79L, almanac.groups).shouldBe(82L)
        applySeedTransforms(14L, almanac.groups).shouldBe(43L)
        applySeedTransforms(55L, almanac.groups).shouldBe(86L)
        applySeedTransforms(13L, almanac.groups).shouldBe(35L)
    }

    test("lowest location") {
        lowestLocationForSeeds(parseAlmanac(example)).shouldBe(35L)
    }
})