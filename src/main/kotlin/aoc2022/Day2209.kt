package aoc2022

import kotlin.math.abs

data class Pos(val x: Int, val y: Int)

fun parseRopeSteps(steps: List<String>): List<Pair<String,Int>> =
    steps.map { step -> step.split(" ").let { Pair(it[0], it[1].toInt()) } }

tailrec fun ropeTailSteps(
    commands: List<Pair<String,Int>>,
    headPos: Pos = Pos(0, 0),
    tailPos: Pos = Pos(0, 0),
    results: List<Pos> = listOf(Pos(0, 0))
): List<Pos> {
    if (commands.isEmpty()) return results.distinct()

    val command = commands.first()

    val deltaX = when (command.first) {
        "R" -> 1
        "L" -> -1
        else -> 0
    }
    val deltaY = when (command.first) {
        "U" -> 1
        "D" -> -1
        else -> 0
    }

    val newHeadPos = Pos(headPos.x + deltaX, headPos.y + deltaY)
    val newTailPos = when {
        abs(newHeadPos.x - tailPos.x) > 1 && newHeadPos.y == tailPos.y -> Pos(tailPos.x + deltaX, tailPos.y)
        abs(newHeadPos.x - tailPos.x) > 1 -> Pos(tailPos.x + deltaX, newHeadPos.y)
        abs(newHeadPos.y - tailPos.y) > 1 && newHeadPos.x == tailPos.x -> Pos(tailPos.x, tailPos.y + deltaY)
        abs(newHeadPos.y - tailPos.y) > 1 -> Pos(newHeadPos.x, tailPos.y + deltaY)
        else -> tailPos
    }

    return if (command.second > 1) ropeTailSteps(listOf(Pair(command.first, command.second - 1)) + commands.drop(1), newHeadPos, newTailPos, results + newTailPos)
    else ropeTailSteps(commands.drop(1), newHeadPos, newTailPos, results + newTailPos)
}

fun ropeTailStepsCount(input: List<String>) = ropeTailSteps(parseRopeSteps(input)).size