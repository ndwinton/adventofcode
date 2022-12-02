package aoc2022

private val states = mapOf(
    "A" to mapOf("X" to 4, "Y" to 8, "Z" to 3),
    "B" to mapOf("X" to 1, "Y" to 5, "Z" to 9),
    "C" to mapOf("X" to 7, "Y" to 2, "Z" to 6)
)

fun scoreForRPSLine(input: String): Int {
    val (them, us) = input.split(" ")

    return states[them]!![us]!!
}

fun totalScoreForRPSLines(lines: List<String>) = lines.map { scoreForRPSLine(it) }.sum()