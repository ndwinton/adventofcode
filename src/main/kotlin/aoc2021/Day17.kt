package aoc2021

import kotlin.math.abs
import kotlin.math.max

data class PositionTime(val position: Int, val time: Int)

fun xPositions(velocity: Int) = generateSequence(PositionTime(velocity, 1)) {
    when {
        velocity > 0 && it.time < velocity -> PositionTime(it.position + (velocity - it.time), it.time + 1)
        velocity < 0 && it.time < -velocity -> PositionTime(it.position + (velocity + it.time), it.time + 1)
        else -> PositionTime(it.position, it.time + 1)
    }
}

fun yPositions(velocity: Int) = generateSequence(PositionTime(velocity, 1)) { PositionTime(it.position + velocity - it.time, it.time + 1) }

// Calculate minimum that can ever reach lower bound
fun minForX(low: Int, high: Int) = (1 .. high).dropWhile { (it * it + it) / 2 < low }.first()

data class VelocityTime(val velocity: Int, val time: Int)

fun xInRange(low: Int, high: Int): List<VelocityTime> =
    (minForX(low, high) .. high)
        .flatMap { velocity ->
            xPositions(velocity)
                .takeWhile { it.position <= high && it.time <= 200 } // TODO: Work out better bounds
                .dropWhile { it.position < low }
                .map { VelocityTime(velocity, it.time) } }

// Max for Y - under gravity, if travelling faster than the maximum depth of
// the target per tick at y = 0, it will always overshoot
fun maxForY(low: Int, high: Int) = max(abs(low), abs(high))

fun yInRange(low: Int, high: Int): List<VelocityTime> =
    (low .. maxForY(low, high))
        .flatMap { velocity ->
            yPositions(velocity)
                .dropWhile { it.position > high }
                .takeWhile { it.position >= low && it.time <= 200 } // TODO: Work out better bounds
                .map { VelocityTime(velocity, it.time) } }

fun findMaxYVelocity(xPos: List<VelocityTime>, yPos: List<VelocityTime>): Int {
    val xByTime = xPos.groupBy { it.time }
    val yByTime = yPos.groupBy { it.time }

    return yByTime.filter { xByTime.containsKey(it.key) }.map { (_, list) -> list.maxOf { it.velocity }}.maxOf { it }
}

fun findMaximumHeight(xLow: Int, xHigh: Int, yLow: Int, yHigh: Int) =
    yPositions(findMaxYVelocity(xInRange(xLow, xHigh), yInRange(yLow, yHigh))).takeWhile { it.position > 0 }.maxOf { it.position }

fun totalDistinctVelocities(xLow: Int, xHigh: Int, yLow: Int, yHigh: Int): Int {
    val xByTime = xInRange(xLow, xHigh).groupBy { it.time }
    val yByTime = yInRange(yLow, yHigh).groupBy { it.time }

    return yByTime.filter { xByTime.containsKey(it.key) }
        .flatMap { (time, yList) ->
            yList.map { it.velocity }.cartesianProduct((xByTime[time] ?: emptyList()).map { it.velocity })
        }
        .distinct().size
}