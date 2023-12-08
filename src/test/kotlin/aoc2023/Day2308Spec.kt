package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe

class Day2308Spec : FunSpec ({
    val example = """
            RL
            
            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent()

        val lines = example.split("\n")

    val example2 = """
        LLR

        AAA = (BBB, BBB)
        BBB = (AAA, ZZZ)
        ZZZ = (ZZZ, ZZZ)
    """.trimIndent()

    val lines2 = example2.split("\n")

    val example3 = """
        LR

        11A = (11B, XXX)
        11B = (XXX, 11Z)
        11Z = (11B, XXX)
        22A = (22B, XXX)
        22B = (22C, 22C)
        22C = (22Z, 22Z)
        22Z = (22B, 22B)
        XXX = (XXX, XXX)
    """.trimIndent()

    val lines3 = example3.split("\n")

    test("parse input") {
        parseNetworkMap(lines.take(5)).shouldBe(Pair(
            "RL",
            mapOf(
                "AAA" to Pair("BBB", "CCC"),
                "BBB" to Pair("DDD", "EEE"),
                "CCC" to Pair("ZZZ", "GGG")
            )
        ))
    }

    test("traverse network map") {
        val (instructions, map) = parseNetworkMap(lines2)
        traverseNetworkMap("AAA", 0, instructions, map).shouldBe(6)
    }

    test("traverse network map multiple paths") {
        val (instructions, map) = parseNetworkMap(lines3)
        val startNodes = map.keys.filter { it.endsWith('A') }
        traverseNetworkMapMultiple(startNodes, 0, instructions, map).shouldBe(6L)
    }
})
