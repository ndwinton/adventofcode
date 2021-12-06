package aoc2021

import kotlin.math.abs
import kotlin.math.max

object Day05 {
    fun parseLine(input: String): Line =
        input.trim().split(Regex("""\s*->\s*""")).let {
            Line(parsePoint(it[0]), parsePoint(it[1]))
        }

    private fun parsePoint(s: String): Point =
        s.split(",").let { Point(it[0].toInt(), it[1].toInt()) }

    fun countIntersections(input: List<String>, includeDiagonals: Boolean = false): Int =
        input.map { parseLine(it) }
            .filter { includeDiagonals || it.isHorizontal() || it.isVertical() }
            .flatMap { it.points }
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .count()
}

data class Point(val x: Int, val y: Int)

data class Line(val a: Point, val b: Point) {
    val points: List<Point> get() =
        (0 .. steps()).map { Point(a.x + deltaX() * it, a.y + deltaY() * it)}

    fun isHorizontal() = a.y == b.y

    fun isVertical() = a.x == b.x

    private fun steps() = max(abs(a.x - b.x), abs(a.y - b.y))

    private fun deltaX() = sign(b.x - a.x)

    private fun deltaY() = sign(b.y - a.y)

    private fun sign(x: Int): Int = when {
        x == 0 -> 0
        x < 0 -> -1
        else -> 1
    }
}