package aoc2021

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Day16Spec : FunSpec({
    test("hex string to binary string") {
        hexToBits("D2FE28").shouldBe("110100101111111000101000")
    }

    test("parse literal packet") {
        parseBits("110100101111111000101000").value.shouldBe(listOf(Literal(6, 2021)))
    }

    test("parse operator packet of type ID 0") {
        parseBits("00111000000000000110111101000101001010010001001000000000").value.shouldBe(listOf(
            Operator(1, listOf(Literal(6, 10), Literal(2, 20)))
        ))
    }

    test("parse operator packet of type ID 1") {
        parseBits("11101110000000001101010000001100100000100011000001100000").value.shouldBe(listOf(
            Operator(7, listOf(Literal(2, 1), Literal(4, 2), Literal(1, 3)))
        ))
    }

    test("large example") {
        sumVersions(parseBits(hexToBits("A0016C880162017C3686B18A3D4780")).value).shouldBe(31)
    }
})