package aoc2025

data class JoltageArray(val value: ShortArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as JoltageArray

        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }
}

data class IndicatorInstructions constructor(val target: Int, val toggles: List<Int>, val joltages: JoltageArray)


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
        .map { it.toShort() }
        .toShortArray()

    return IndicatorInstructions(target, toggles, JoltageArray(joltages))
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

fun findFewestPressesForJoltages(instructions: IndicatorInstructions): Int {

    val expandedToggles = instructions
        .toggles
        .map { toggle -> (0 ..< instructions.joltages.value.size).map { ((toggle shr it) and 1).toShort() }.toShortArray() }

    fun exceedsBounds(joltageArray: JoltageArray): Boolean =
        joltageArray.value.mapIndexed { index, sh -> sh > instructions.joltages.value[index] }.any()

    tailrec fun find(states: Set<JoltageArray>, depth: Int, seen: Set<JoltageArray>): Int {
        println("${states.size} $depth")
        val newStates = states.flatMap { state ->
            expandedToggles.map { toggle ->
                JoltageArray(state.value.mapIndexed { index, b -> (b + toggle[index]).toShort() }.toShortArray()) }
        }.filter { !seen.contains(it) || exceedsBounds(it) }.toSet()

        return when {
            newStates.isEmpty() -> 1000000000
            newStates.any { it.value.contentEquals(instructions.joltages.value) } -> depth
            else -> find(newStates, depth + 1, seen + newStates)
        }
    }

    return find(setOf(JoltageArray(ShortArray(instructions.joltages.value.size) { 0 })), 1, emptySet())
}


fun findTotalFewestPressesForInput(input: List<String>): Int =
    input.sumOf { findFewestPresses(parseIndicatorInstructions(it)) }

fun findTotalFewestJoltagePressesForInput(input: List<String>): Int =
    input.sumOf { println(it); findFewestPressesForJoltages(parseIndicatorInstructions(it)) }