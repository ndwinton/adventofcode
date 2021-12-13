package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe

class Day12Spec : FunSpec({
    val example1 = """
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end
    """.trimIndent().lines()

    val example2 = """
        dc-end
        HN-start
        start-kj
        dc-start
        dc-HN
        LN-dc
        HN-end
        kj-sa
        kj-HN
        kj-dc
    """.trimIndent().lines()

    val example3 = """
        fs-end
        he-DX
        fs-he
        start-DX
        pj-DX
        end-zg
        zg-sl
        zg-pj
        pj-he
        RW-he
        fs-DX
        pj-RW
        zg-RW
        start-pj
        he-WI
        zg-he
        pj-fs
        start-RW
    """.trimIndent().lines()

    val example1Paths = """
        start,A,b,A,c,A,end
        start,A,b,A,end
        start,A,b,end
        start,A,c,A,b,A,end
        start,A,c,A,b,end
        start,A,c,A,end
        start,A,end
        start,b,A,c,A,end
        start,b,A,end
        start,b,end
    """.trimIndent().lines()

    test("simplest example") {
        findPaths(listOf("start-end")).shouldBe(listOf("start,end"))
    }

    test("build connections") {
        buildConnections(example1).shouldBe(mapOf(
            "start" to setOf("A", "b"),
            "A" to setOf("start", "b", "c", "end"),
            "b" to setOf("start", "A", "d", "end"),
            "c" to setOf("A"),
            "d" to setOf("b"),
            "end" to setOf("A", "b")
        ))
    }

    test("example paths") {
        findPaths(example1).shouldContainExactlyInAnyOrder(example1Paths)
    }

    test("larger example") {
        findPaths(example3).count().shouldBe(226)
    }

    test("example with part 2 rule") {
        findPaths(example1, ::part2Rule).count().shouldBe(36)
        findPaths(example2, ::part2Rule).count().shouldBe(103)
        findPaths(example3, ::part2Rule).count().shouldBe(3509)

    }
})