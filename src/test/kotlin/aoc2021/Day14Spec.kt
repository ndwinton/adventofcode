package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day14Spec : FunSpec({

    val example = """
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
    """.trimIndent().lines()

    test("extract rules") {
        polymerRules(example).let {
            it.size.shouldBe(16)
            it["CN"].shouldBe("C")
        }
    }

    test("polymerise") {
        polymerise("NNCB", polymerRules(example), 1).shouldBe("NCNBCHB")
        polymerise("NNCB", polymerRules(example), 4).shouldBe("NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB")
    }

    test("part 2") {
        polymerPart2(example, 10).shouldBe(1588L)
        polymerPart2(example, 40).shouldBe(2188189693529L)
    }
})