package aoc2024

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2409Spec : FunSpec ({
    val example = "2333133121414131402"

    test("defrag checksum") {
        defragChecksum(example).shouldBe(1928)
    }
})