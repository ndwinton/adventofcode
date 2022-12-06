package aoc2022

fun findStartOfPacketMarker(input: String, offset: Int = 0): Int = findStartOfUniqueSequence(input, 4)

fun findStartOfMessageMarker(input: String, offset: Int = 0): Int = findStartOfUniqueSequence(input, 14)

private tailrec fun findStartOfUniqueSequence(input: String, size: Int, offset: Int = 0): Int =
    // Note: No guard against exhausting input
    if (input.take(size).toSet().size == size) offset + size
    else findStartOfUniqueSequence(input.drop(1), size, offset + 1)