package aoc2022

tailrec fun findStartOfPacketMarker(input: String, offset: Int = 0): Int =
    if (input.take(4).toSet().size == 4) offset + 4
    else findStartOfPacketMarker(input.drop(1), offset + 1)

tailrec fun findStartOfMessageMarker(input: String, offset: Int = 0): Int =
    if (input.take(14).toSet().size == 14) offset + 14
    else findStartOfMessageMarker(input.drop(1), offset + 1)