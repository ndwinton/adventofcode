package aoc2025

import common.combinations
import kotlin.math.abs

private data class Coord(val x: Long, val y: Long)

private fun parseCoords(input: List<String>): List<Coord> =
    input.map { it.split(",").map { it.toLong() }.let { list -> Coord(list[0], list[1]) } }

fun largestRedRectangle(input: List<String>) =
    parseCoords(input).combinations(2)
        .maxOf { (abs(it[0].x - it[1].x) + 1) * (abs(it[0].y - it[1].y) + 1) }


private data class Box(val corner1: Coord, val corner2: Coord) {
    val minX = minOf(corner1.x, corner2.x)
    val maxX = maxOf(corner1.x, corner2.x)
    val minY = minOf(corner1.y, corner2.y)
    val maxY = maxOf(corner1.y, corner2.y)
    val area = (maxX - minX + 1) * (maxY - minY + 1)

    fun overlaps(other: Box): Boolean {

        fun intervalOverlaps(min1: Long, max1: Long, min2: Long, max2: Long): Boolean =
            (min1 in min2..max2) || (max1 >= min2 && min1 <= max2)

        val xOverlap = intervalOverlaps(minX + 1, maxX - 1, other.minX, other.maxX)
        val yOverlap = intervalOverlaps(minY + 1, maxY - 1, other.minY, other.maxY)

        return xOverlap && yOverlap
    }
}

// Note that this only works for the kind of shape used in the puzzle where there are no large "negative space"
// areas enclosed by points where the curve is concave (e.g. like the area inside a 'U'

fun largestEnclosedRectangle(input: List<String>): Long {
    val coords = parseCoords(input)
    val boxes = coords.combinations(2)
        .map { Box(it[0], it[1]) }
        .sortedByDescending { it.area }
    val edges = coords.zipWithNext { a, b ->
        Box(Coord(minOf(a.x, b.x), minOf(a.y, b.y)),
            Coord(maxOf(a.x, b.x), maxOf(a.y, b.y))) } +
            Box(coords.last(), coords.first())

    return boxes.first { box -> edges.none { box.overlaps(it) } }.let { println(it); it.area }
}


