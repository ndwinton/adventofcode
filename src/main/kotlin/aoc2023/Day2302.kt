package aoc2023

import aoc2021.Tuple3
import kotlin.math.max

fun parseRGBTuples(line: String): List<Tuple3> =
    line.split(";").map {
        val r = extractDigits(it, "red")
        val g = extractDigits(it, "green")
        val b = extractDigits(it, "blue")

        Tuple3(r, g, b)
    }

private fun extractDigits(string: String, colour: String) =
    Regex("(\\d+) $colour").find(string).let { match ->
        if (match != null) match.groupValues[1] else "0"
    }.toInt()

fun possibleLineIndexSum(lines: List<String>, limits: Tuple3): Int =
    lines.mapIndexed { index, line ->
        val tuples = parseRGBTuples(line)
        val filtered = tuples
            .filter { it.x <= limits.x }
            .filter { it.y <= limits.y }
            .filter { it.z <= limits.z }
        if (tuples.size == filtered.size) index + 1 else 0
    }.sum()

fun powerOfMinPossibleTuple(line: String) =
    parseRGBTuples(line).fold(Tuple3(0, 0, 0)) { acc, tuple ->
        Tuple3(max(tuple.x, acc.x), max(tuple.y, acc.y), max(tuple.z, acc.z))
    }.let { it.x * it.y * it.z }