package aoc2022

tailrec fun findStartOfPacketMarker(input: String, offset: Int = 0): Int =
    if (input.take(4).toSet().size == 4) offset + 4
    else findStartOfPacketMarker(input.drop(1), offset + 1)
