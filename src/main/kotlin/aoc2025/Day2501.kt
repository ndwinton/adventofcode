package aoc2025

import kotlin.math.absoluteValue
import kotlin.math.sign

private data class State(val position: Int, val zeroes: Int)

fun numberOfStopsOnZero(input: List<String>) =
    input.map { it.drop(1).toInt() * (if (it.first() == 'L') -1 else 1) }
        .fold(State(50, 0)) { state, move ->
            val newPosition = state.position + move
            val newZeroes = state.zeroes + (if (newPosition % 100 == 0) 1 else 0)
            State(newPosition, newZeroes)
        }.zeroes

fun numberOfPassesThroughZero(input: List<String>) =
    input.map { it.drop(1).toInt() * (if (it.first() == 'L') -1 else 1) }
        .fold(State(50, 0)) { state, move ->
            val completeTurns = (move / 100).absoluteValue
            val remainder = move % 100
            val newPosition = state.position + remainder
            val passedOrStoppedOnZero = when {
                newPosition.absoluteValue > 100 -> 1
                newPosition.sign == -state.position.sign -> 1
                newPosition % 100 == 0  -> 1
                else -> 0
            }
            State(newPosition % 100, state.zeroes + completeTurns + passedOrStoppedOnZero)
        }.zeroes