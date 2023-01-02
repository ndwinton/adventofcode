package aoc2022


sealed class PacketElement : Comparable<PacketElement>

class PacketList(val packets: List<PacketElement> = listOf()) : PacketElement() {
    operator fun get(i: Int) = packets[i]
    override fun compareTo(other: PacketElement): Int =
        when (other) {
            is PacketList -> {
                val comparisons = packets.zip(other.packets).map { it.first.compareTo(it.second) }.dropWhile { it == 0 }
                if (comparisons.isEmpty()) this.size.compareTo(other.size) else comparisons.first()
            }
            else -> this.compareTo(PacketList(listOf(other)))
        }

    override fun toString(): String {
        return "PacketList($packets)"
    }

    val size: Int
        get() = packets.size
}

class PacketValue(val value: Int) : PacketElement() {
    override fun compareTo(other: PacketElement): Int =
        when (other) {
            is PacketValue -> value - other.value
            else -> PacketList(listOf(this)).compareTo(other)
        }

    override fun toString(): String {
        return "PacketValue($value)"
    }
}

fun parsePackets(packetString: String): PacketElement {
    val tokens = tokenisePackets(packetString)
    val result = parsePacketList(tokens, emptyList()).first as PacketList
    return result.packets[0]
}

private fun parsePacketList(tokens: List<Token>, results: List<PacketElement>): Pair<PacketElement,List<Token>> =
    if (tokens.isEmpty()) Pair(PacketList(results), emptyList())
    else when (val first = tokens.first()) {
        is NumberToken -> parsePacketList(tokens.drop(1), results + PacketValue(first.value))
        is OpenToken -> parsePacketList(tokens.drop(1), emptyList()).let {
            parsePacketList(it.second, results + it.first)
        }
        is CloseToken -> Pair(PacketList(results), tokens.drop(1))
    }


sealed class Token
object OpenToken : Token()
object CloseToken : Token()
class NumberToken(val value: Int) : Token()

fun tokenisePackets(packetString: String): List<Token> =
    Regex("""(\[|\]|,|\d+)""")
        .findAll(packetString)
        .map { it.groupValues[1] }
        .filter { it != "," }
        .map {
            when (it) {
                "[" -> OpenToken
                "]" -> CloseToken
                else -> NumberToken(it.toInt())
            }
        }.toList()

fun sumOfOrderedPairIndices(lines: List<String>) =
    lines.filter { it.isNotBlank() }
        .chunked(2)
        .mapIndexed { index, pair ->
            if (parsePackets(pair[0]) <= parsePackets(pair[1])) index + 1
            else 0
        }.sum()

