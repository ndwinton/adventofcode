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

// Calculate minimum X velocity that can ever reach lower bound
fun minPossibleVx(low: Int, high: Int) = (1 .. high).dropWhile { (it * it + it) / 2 < low }.first()

data class VelocityTime(val velocity: Int, val time: Int)

fun vxInRange(low: Int, high: Int, maxTicks: Int = 200): List<VelocityTime> =
    (minPossibleVx(low, high) .. high)
        .flatMap { velocity ->
            xPositions(velocity)
                .takeWhile { it.position <= high && it.time <= maxTicks }
                .dropWhile { it.position < low }
                .map { VelocityTime(velocity, it.time) } }

// Max for VY - under gravity, if travelling faster than the maximum depth of
// the target per tick at y = 0, it will always overshoot, so this
// is the maximum possible Y velocity
fun maxPossibleVy(low: Int, high: Int) = max(abs(low), abs(high))

fun vyInRange(low: Int, high: Int, maxTicks: Int = 200): List<VelocityTime> =
    (low .. maxPossibleVy(low, high))
        .flatMap { velocity ->
            yPositions(velocity)
                .dropWhile { it.position > high }
                .takeWhile { it.position >= low && it.time <= maxTicks }
                .map { VelocityTime(velocity, it.time) } }

fun findMaxOnTargetVy(xPos: List<VelocityTime>, yPos: List<VelocityTime>): Int {
    val xByTime = xPos.groupBy { it.time }
    val yByTime = yPos.groupBy { it.time }

    return yByTime.filter { xByTime.containsKey(it.key) }.map { (_, list) -> list.maxOf { it.velocity }}.maxOf { it }
}

fun findMaximumHeight(xLow: Int, xHigh: Int, yLow: Int, yHigh: Int) =
    yPositions(
        findMaxOnTargetVy(
            vxInRange(xLow, xHigh, maxTicks(yLow, yHigh)),
            vyInRange(yLow, yHigh, maxTicks(yLow, yHigh))
        )
    ).takeWhile { it.position > 0 }.maxOf { it.position }

// With maximum Y velocity it will take 2 * v + 1 ticks to fall beyond the lowest bound
private fun maxTicks(yLow: Int, yHigh: Int) = maxPossibleVy(yLow, yHigh) * 2 + 1

fun totalDistinctVelocities(xLow: Int, xHigh: Int, yLow: Int, yHigh: Int): Int {
    val xByTime = vxInRange(xLow, xHigh, maxTicks(yLow, yHigh)).groupBy { it.time }
    val yByTime = vyInRange(yLow, yHigh, maxTicks(yLow, yHigh)).groupBy { it.time }

    return yByTime.filter { xByTime.containsKey(it.key) }
        .flatMap { (time, yList) ->
            yList.map { it.velocity }.cartesianProduct((xByTime[time] ?: emptyList()).map { it.velocity })
        }
        .distinct().size
}

