package aoc2021

fun hexToBits(hex: String): String =
    hex.map { "000${it.digitToInt(16).toString(2)}".takeLast(4) }.joinToString("")

sealed class Packet(open val version: Byte)

data class Literal(override val version: Byte, val value: Int) : Packet(version)

data class Operator(override val version: Byte, val packets: List<Packet>) : Packet(version)

tailrec fun parseBits(bits: String, count: Int = Int.MAX_VALUE, results: List<Packet> = emptyList()): OpResult {
    if (bits.isEmpty() || bits.all { it == '0' }) return OpResult(results, "")
    if (count == 0) return OpResult(results, bits)

    val version = bits.take(3).toByte(2)
    val type = bits.take(6).drop(3).toByte(2)
    val parsed = if (type.toInt() == 4) parseLiteral(bits.drop(6), 0) else parseOperator(bits.drop(6))
    val packet = when (parsed) {
        is LiteralResult -> Literal(version, parsed.value)
        is OpResult -> Operator(version, parsed.value)
    }
    return parseBits(parsed.remaining, count - 1, results + listOf(packet))
}

fun parseOperator(bits: String, value: List<Packet> = emptyList()): OpResult =
    if (bits[0] == '0') parseOperator0(bits.drop(1))
    else parseOperator1(bits.drop(1))

fun parseOperator0(bits: String): OpResult {
    val length = bits.take(15).toInt(2)
    val subBits = bits.drop(15).take(length)
    return OpResult(parseBits(subBits).value, bits.drop(15 + length))
}

fun parseOperator1(bits: String): OpResult {
    val count = bits.take(11).toInt(2)
    return parseBits(bits.drop(11), count)
}

tailrec fun parseLiteral(bits: String, value: Int): LiteralResult {
    val next = bits.take(5)
    return if (next[0] == '0') LiteralResult(value.shl(4) + next.toInt(2), bits.drop(5))
    else parseLiteral(bits.drop(5), value.shl(4) + next.toInt(2) - 16)
}

fun sumVersions(packets: List<Packet>): Int =
    packets.sumOf {
        when (it) {
            is Literal -> it.version.toInt()
            is Operator -> it.version.toInt() + sumVersions(it.packets)
        }
    }

sealed class ParseResult(open val remaining: String)
data class LiteralResult(val value: Int, override val remaining: String) : ParseResult(remaining)
data class OpResult(val value: List<Packet>, override val remaining: String) : ParseResult(remaining)