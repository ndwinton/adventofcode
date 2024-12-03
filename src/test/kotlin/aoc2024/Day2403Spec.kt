package aoc2024

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day2403Spec : FunSpec ({
    val example1 = """xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))"""

    val example2 = """xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"""

    test("example1") {
        cleanAndExecute(example1).shouldBe(161)
    }

    test("example2") {
        cleanAndExecuteWithConditions(example2).shouldBe(48)
    }
})