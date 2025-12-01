package aoc2025

fun numberOfZeroes(input: List<String>) =
    input.map { it.drop(1).toInt() * (if (it.first() == 'L') -1 else 1) }
        .fold(Pair(50, 0)) { state, next ->
            // println("$state, $next")
            val newPosition = state.first + next
            val newZeroes = state.second + (if (newPosition % 100 == 0) 1 else 0)
            Pair(newPosition, newZeroes)
        }.second