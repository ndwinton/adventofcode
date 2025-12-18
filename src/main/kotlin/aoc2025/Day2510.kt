package aoc2025

data class IndicatorInstructions(val target: Int, val toggles: List<Int>, val joltages: List<Int>)

fun parseIndicatorInstructions(line: String): IndicatorInstructions {
    val parts = line.split(" ")

    val target = parts[0].filter { it == '.' || it == '#'}
        .reversed()
        .fold(0) { acc, ch -> (acc shl 1) or (if (ch == '#') 1 else 0) }

    val toggles = parts.drop(1)
        .dropLast(1)
        .map { it.split(Regex("[(),]"))
            .filter { it.isNotEmpty() }
            .map { it.toInt() }
            .fold (0) { acc, i -> acc or (1 shl i) }
        }

    val joltages = parts.last()
        .split(Regex("[{},]"))
        .filter { it.isNotEmpty() }
        .map { it.toInt() }

    return IndicatorInstructions(target, toggles, joltages)
}

fun findFewestPresses(instructions: IndicatorInstructions): Int {

    tailrec fun find(states: Set<Int>, depth: Int, seen: Set<Int>): Int {
        val newStates = states.flatMap { state -> instructions.toggles.map { it xor state } }
            .filter { !seen.contains(it) }
            .toSet()

        return when {
            newStates.isEmpty() -> 1000000000
            newStates.any { it == instructions.target } -> depth
            else -> find(newStates, depth + 1, seen + newStates)
        }
    }

    return find(setOf(0), 1, emptySet())
}


fun findTotalFewestPressesForInput(input: List<String>): Int =
    input.sumOf { findFewestPresses(parseIndicatorInstructions(it)) }