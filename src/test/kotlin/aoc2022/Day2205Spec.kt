package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2205Spec : FunSpec ({
    val sampleLines = """
        |    [D]    
        |[N] [C]    
        |[Z] [M] [P]
        | 1   2   3 
        |
        |move 1 from 2 to 1
        |move 3 from 1 to 3
        |move 2 from 2 to 1
        |move 1 from 1 to 2
    """.trimMargin("|").split("\n")

    test("parse stack state lines") {
        parseCrateStackStateLines(sampleLines).shouldBe(listOf("NZ", "DCM", "P"))
    }

    test("parsing moves") {
        parseCrateMoveLines(sampleLines).shouldBe(listOf(
            CrateMove(1, 2, 1),
            CrateMove(3, 1, 3),
            CrateMove(2, 2, 1),
            CrateMove(1, 1, 2)
        ))
    }

    test("apply moves to crates") {
        applyMovesToCrates(sampleLines).shouldBe("CMZ")
    }

    test("apply moves to crates, multi-move version") {
        applyMovesToCratesMultiMove(sampleLines).shouldBe("MCD")
    }
})

