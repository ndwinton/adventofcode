package aoc2024

fun isLevelSafe(level: List<Int>) : Boolean =
    level.zipWithNext()
        .map { it.first - it.second }
        .let { diffs ->
            diffs.all { it in -3..-1 } || diffs.all { it in 1..3 }
        }

tailrec fun levelDampened(level: List<Int>, index: Int = 0, result : List<List<Int>> = listOf(level)) : List<List<Int>> =
    if (index >= level.size) result
    else levelDampened(level, index + 1, result + listOf(level.take(index) + level.drop(index + 1)))

fun isDampenedLevelSafe(level: List<Int>) =
    levelDampened(level).any { isLevelSafe(it) }