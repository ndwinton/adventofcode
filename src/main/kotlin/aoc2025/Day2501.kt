package aoc2025

import kotlin.math.absoluteValue
import kotlin.math.sign

fun numberOfStopsOnZero(input: List<String>) =
    input.map { it.drop(1).toInt() * (if (it.first() == 'L') -1 else 1) }
        .fold(Pair(50, 0)) { state, next ->
            // println("$state, $next")
            val newPosition = state.first + next
            val newZeroes = state.second + (if (newPosition % 100 == 0) 1 else 0)
            Pair(newPosition, newZeroes)
        }.second

fun numberOfPassesThroughZero(input: List<String>) =
    input.map { it.drop(1).toInt() * (if (it.first() == 'L') -1 else 1) }
        .fold(Pair(50, 0)) { state, next ->
            val completeTurns = (next / 100).absoluteValue
            val remainder = next % 100
            val newPosition = state.first + remainder
            val passedOrStoppedOnZero = when {
                newPosition.absoluteValue > 100 -> 1
                newPosition.sign == -state.first.sign -> 1
                newPosition % 100 == 0  -> 1
                else -> 0
            }
            Pair(newPosition % 100, state.second + completeTurns + passedOrStoppedOnZero)
        }.second