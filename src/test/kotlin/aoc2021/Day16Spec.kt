package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.data.forAll
import io.kotest.data.headers
import io.kotest.data.row
import io.kotest.data.table
import io.kotest.matchers.shouldBe

class Day16Spec : FunSpec({
    test("hex string to binary string") {
        hexToBits("D2FE28").shouldBe("110100101111111000101000")
    }

    test("parse literal packet") {
        parseBits("110100101111111000101000").value.shouldBe(listOf(Literal(6, 4,2021)))
    }

    test("parse operator packet of type ID 0") {
        parseBits("00111000000000000110111101000101001010010001001000000000").value.shouldBe(listOf(
            Operator(1, 6, listOf(Literal(6, 4, 10), Literal(2, 4, 20)))
        ))
    }

    test("parse operator packet of type ID 1") {
        parseBits("11101110000000001101010000001100100000100011000001100000").value.shouldBe(listOf(
            Operator(7, 3, listOf(Literal(2, 4, 1), Literal(4, 4, 2), Literal(1, 4, 3)))
        ))
    }

    test("large example") {
        sumVersions(parseBits(hexToBits("A0016C880162017C3686B18A3D4780")).value).shouldBe(31)
    }

    test("evaluate packets") {
        table(
            headers("bits", "result", "name"),
            row("C200B40A82", 3, "sum"),
            row("04005AC33890", 54, "product"),
            row("880086C3E88112", 7, "min"),
            row("CE00C43D881120", 9, "max"),
            row("D8005AC2A8F0", 1, "less"),
            row("F600BC2D8F", 0, "greater"),
            row("9C005AC2F8F0", 0, "equal"),
            row("9C0141080250320F1802104A08", 1, "expr")
        ).forAll { bits, result, name ->
            evaluatePackets(parseBits(hexToBits(bits)).value).shouldBe(result)
        }
    }
})