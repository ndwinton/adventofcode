package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2315Spec : FunSpec ({
    val example = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".trimIndent()

    val lines = example.split("\n")

    test("hash sum") {
        initSequenceTotal(example).shouldBe(1320)
    }

    test("focusing power") {
        focusingPower(example).shouldBe(145)
    }
})






