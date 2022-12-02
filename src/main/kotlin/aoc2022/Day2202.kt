package aoc2022

private val gameScores = mapOf(
    "A" to mapOf("A" to 4, "B" to 8, "C" to 3),
    "B" to mapOf("A" to 1, "B" to 5, "C" to 9),
    "C" to mapOf("A" to 7, "B" to 2, "C" to 6)
)

private val usToThem = mapOf("X" to "A", "Y" to "B", "Z" to "C")

fun scoreForRPSLine(input: String): Int {
    val (them, us) = input.split(" ")

    return scoreForRPSGame(them, usToThem[us]!!)
}

private fun scoreForRPSGame(them: String, us: String) = gameScores[them]!![us]!!

fun totalScoreForRPSLines(lines: List<String>) = lines.sumOf { scoreForRPSLine(it) }

private val strategyMap = mapOf(
    "X" to mapOf("A" to "C", "B" to "A", "C" to "B"),
    "Y" to mapOf("A" to "A", "B" to "B", "C" to "C"),
    "Z" to mapOf("A" to "B", "B" to "C", "C" to "A")
)
fun translateStrategy(them: String, stategy: String): String = strategyMap[stategy]!![them]!!

fun totalScoreForRPSLinesNewStrategy(lines: List<String>) = lines.sumOf {
    val (them, strategy) = it.split(" ")
    scoreForRPSGame(them, translateStrategy(them, strategy))
}