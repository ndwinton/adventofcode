package aoc2022

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.ints.shouldBeLessThan
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class Day2213Spec : FunSpec({
    test("parsing empty list") {
        val result = parsePackets("[]")
        result.shouldBeInstanceOf<PacketList>()
        result.size.shouldBe(0)
    }

    test("tokenising packet string") {
        val tokens = tokenisePackets("[1,20,[3]]").toList()
        tokens[0].shouldBe(OpenToken)
        (tokens[1] as NumberToken).value.shouldBe(1)
        (tokens[2] as NumberToken).value.shouldBe(20)
        tokens[3].shouldBe(OpenToken)
        (tokens[4] as NumberToken).value.shouldBe(3)
        tokens[5].shouldBe(CloseToken)
        tokens[6].shouldBe(CloseToken)
    }

    test("parsing single element list") {
        val result = parsePackets("[1]") as PacketList
        result.size.shouldBe(1)
        val value = result[0].shouldBeInstanceOf<PacketValue>()
        value.value.shouldBe(1)
    }

    test("parsing list of integers") {
        val result = parsePackets("[1,2,3]") as PacketList
        result.size.shouldBe(3)
        val value = result[2].shouldBeInstanceOf<PacketValue>()
        value.value.shouldBe(3)
    }

    test("comparing integer elements") {
        PacketValue(1).compareTo(PacketValue(2)).shouldBeLessThan(0)
        PacketValue(2).compareTo(PacketValue(1)).shouldBeGreaterThan(0)
        PacketValue(3).compareTo(PacketValue(3)).shouldBe(0)
    }

    test("comparing simple lists of equal length") {
        val list1 = parsePackets("[1,2,3,1]")
        val list2 = parsePackets("[1,3,2,1]")
        list1.compareTo(list2).shouldBeLessThan(0)
        list2.compareTo(list1).shouldBeGreaterThan(0)
        list1.compareTo(list1).shouldBe(0)
    }

    test("comparing lists of unequal length") {
        val list1 = parsePackets("[1,2,3]")
        val list2 = parsePackets("[1,2,3,4]")
        list1.compareTo(list2).shouldBeLessThan(0)
        list2.compareTo(list1).shouldBeGreaterThan(0)
    }

    test("given examples") {
        table(
            headers("list1", "list2", "ordered"),
            row("[1,1,3,1,1]", "[1,1,5,1,1]", true),
            row("[[1],[2,3,4]]", "[[1],4]", true),
            row("[9]", "[[8,7,6]]", false),
            row("[[4,4],4,4]", "[[4,4],4,4,4]", true),
            row("[7,7,7,7]", "[7,7,7]", false),
            row("[]", "[3]", true),
            row("[[[]]]", "[[]]", false),
            row("[1,[2,[3,[4,[5,6,7]]]],8,9]", "[1,[2,[3,[4,[5,6,0]]]],8,9]", false),
        ).forAll { list1, list2, ordered -> (parsePackets(list1) <= parsePackets(list2)).shouldBe(ordered) }
    }

    val pairs = """
        [1,1,3,1,1]
        [1,1,5,1,1]

        [[1],[2,3,4]]
        [[1],4]

        [9]
        [[8,7,6]]

        [[4,4],4,4]
        [[4,4],4,4,4]

        [7,7,7,7]
        [7,7,7]

        []
        [3]

        [[[]]]
        [[]]

        [1,[2,[3,[4,[5,6,7]]]],8,9]
        [1,[2,[3,[4,[5,6,0]]]],8,9]
    """.trimIndent().split("\n")

    test("part 1 - ordered pair calculation") {
        sumOfOrderedPairIndices(pairs).shouldBe(13)
    }

    test("part 2 - decoder key") {
        decoderKey(pairs).shouldBe(140)
    }
})