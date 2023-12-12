package aoc2023

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day2312Spec : FunSpec ({
    val example = """
        ???.### 1,1,3
        .??..??...?##. 1,1,3
        ?#?#?#?#?#?#?#? 1,3,1,6
        ????.#...#... 4,1,1
        ????.######..#####. 1,6,5
        ?###???????? 3,2,1
    """.trimIndent()

    val lines = example.split("\n")

    test("examples") {
        table(
            headers("input", "expected"),
            row("???.### 1,1,3", 1),
            row(".??..??...?##. 1,1,3", 4),
            row("?#?#?#?#?#?#?#? 1,3,1,6", 1),
            row("????.#...#... 4,1,1", 1),
            row("????.######..#####. 1,6,5", 4),
            row("?###???????? 3,2,1", 10),
            row("?.???.?#???#???.? 3,6,1", 5),
            row("?#????#?.??#?????? 3,1,1,1,1,1", 25)
        ).forAll { input, expected -> springArrangements(input).shouldBe(expected) }
    }
})

