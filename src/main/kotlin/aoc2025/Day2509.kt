package aoc2025

import common.combinations
import kotlin.math.abs

private data class Coord(val x: Long, val y: Long)

private fun parseCoords(input: List<String>): List<Coord> =
    input.map { it.split(",").map { it.toLong() }.let { list -> Coord(list[0], list[1]) } }

fun largestRedRectangle(input: List<String>) =
    parseCoords(input).combinations(2)
        .maxOf { (abs(it[0].x - it[1].x) + 1) * (abs(it[0].y - it[1].y) + 1) }
