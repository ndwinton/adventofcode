package aoc2021

import java.lang.Integer.min
import kotlin.math.max

object Day05 {
    fun parseLine(input: String): Line =
        input.trim().split(Regex("""\s*->\s*""")).let {
            Line(parsePoint(it[0]), parsePoint(it[1]))
        }

    private fun parsePoint(s: String): Point =
        s.split(",").let { Point(it[0].toInt(), it[1].toInt()) }

    fun countIntersections(input: List<String>): Int =
        input.map { parseLine(it) }
        .flatMap { it.points }
        .groupingBy { it }
        .eachCount()
        .filter { it.value > 1 }
        .count()
}

data class Point(val x: Int, val y: Int)

data class Line(val a: Point, val b: Point) {
    val points: List<Point> get() =
        when {
            isHorizontal() -> (min(a.x, b.x)..max(a.x, b.x)).map { Point(it, a.y) }
            isVertical() -> (min(a.y, b.y)..max(a.y, b.y)).map { Point(a.x, it) }
            else -> emptyList()
        }

    fun isHorizontal() = a.y == b.y

    fun isVertical() = a.x == b.x
}