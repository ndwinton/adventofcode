package aoc2021

import kotlin.math.abs
import kotlin.math.max

fun xPositions(velocity: Int) = generateSequence(Pair(velocity, 1)) {
    // Pair first is x position, second is time
    when {
        velocity > 0 && it.second < velocity -> Pair(it.first + (velocity - it.second), it.second + 1)
        velocity < 0 && it.second < -velocity -> Pair(it.first + (velocity + it.second), it.second + 1)
        else -> Pair(it.first, it.second + 1)
    }
}

fun yPositions(velocity: Int) = generateSequence(Pair(velocity, 1)) { Pair(it.first + velocity - it.second, it.second + 1) }

fun minXToRange(low: Int, high: Int) = (1 .. high).dropWhile { (it * it + it) / 2 < low }.first()

// Pairs are (velocity, time)
fun xInRange(low: Int, high: Int): List<Pair<Int,Int>> =
    (minXToRange(low, high) .. high)
        .flatMap { velocity ->
            xPositions(velocity)
                .takeWhile { it.first <= high && it.second <= 200 } // TODO: Work out better bounds
                .dropWhile { it.first < low }
                .map { Pair(velocity, it.second) } }

// Pairs are (velocity, time)
fun yInRange(low: Int, high: Int): List<Pair<Int,Int>> =
    (low .. max(abs(low), abs(high)))
        .flatMap { velocity ->
            yPositions(velocity)
                .dropWhile { it.first > high }
                .takeWhile { it.first >= low && it.second <= 200 } // TODO: Work out better bounds
                .map { Pair(velocity, it.second) } }

fun findMaxYVelocity(xPos: List<Pair<Int,Int>>, yPos: List<Pair<Int,Int>>): Int {
    val xByTime = xPos.groupBy { it.second }
    val yByTime = yPos.groupBy { it.second }

    return yByTime.filter { xByTime.containsKey(it.key) }.map { (_, list) -> list.maxOf { it.first }}.maxOf { it }
}

fun findMaximumHeight(xLow: Int, xHigh: Int, yLow: Int, yHigh: Int) =
    yPositions(findMaxYVelocity(xInRange(xLow, xHigh), yInRange(yLow, yHigh))).takeWhile { it.first > 0 }.maxOf { it.first }

fun totalDistinctVelocities(xLow: Int, xHigh: Int, yLow: Int, yHigh: Int): Int {
    val xByTime = xInRange(xLow, xHigh).groupBy { it.second }
    val yByTime = yInRange(yLow, yHigh).groupBy { it.second }

    return yByTime.filter { xByTime.containsKey(it.key) }
        .flatMap { (time, yList) ->
            yList.map { it.first }.cartesianProduct((xByTime[time] ?: emptyList()).map { it.first })
        }
        .distinct().size
}