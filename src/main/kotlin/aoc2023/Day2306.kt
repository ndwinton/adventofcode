package aoc2023

import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

fun parseRaceData(lines: List<String>): List<Pair<Int, Int>> =
    lines[0].split(Regex("""\s+""")).drop(1).map { it.toInt() }
        .zip(lines[1].split(Regex("""\s+""")).drop(1).map { it.toInt() })

fun distanceMovedForButtonPress(press: Int, raceDuration: Int): Int = (raceDuration - press) * press

fun numberOfWinningTimes(duration: Int, record: Int) =
    (0 .. duration).map { distanceMovedForButtonPress(it, duration) }.count { it > record }

fun boatRaceAnswerPart1(lines: List<String>): Int =
    parseRaceData(lines).map { numberOfWinningTimes(it.first, it.second) }.fold(1) { acc, i -> acc * i }

fun parseRaceDataSingle(lines: List<String>) =
    Pair(lines[0].split(Regex("""\s+""")).drop(1).joinToString("").toLong(),
        lines[1].split(Regex("""\s+""")).drop(1).joinToString("").toLong())

fun quadraticRoots(duration: Long, record: Long) =
    Pair(duration.toDouble() - sqrt(duration.toDouble() * duration - 4.0 * record ) / 2.0,
        duration.toDouble() + sqrt(duration.toDouble() * duration - 4.0 * record ) / 2.0)

fun boatRaceAnswerPart2(lines: List<String>): Long {
    val (duration, record) = parseRaceDataSingle(lines)
    val (lowerRoot, upperRoot) = quadraticRoots(duration, record)
    return (upperRoot - lowerRoot).toLong()
}

