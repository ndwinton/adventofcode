package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day2206Spec : FunSpec({
    test("sample inputs") {
        table(
            headers("input", "result"),
            row("bvwbjplbgvbhsrlpgdmjqwftvncz", 5),
            row("nppdvjthqldpwncqszvftbrmjlhg", 6),
            row("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10),
            row("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11)
        ).forAll { input, result -> findStartOfPacketMarker(input).shouldBe(result)  }
    }
})