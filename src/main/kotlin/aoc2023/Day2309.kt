package aoc2023

fun calculateDiffs(list: List<Long>) = list.zipWithNext().map { it.second - it.first }

fun predictNext(list: List<Long>): Long {
    val diffs = calculateDiffs(list)
    return if (diffs.all { it == 0L }) list.last() else list.last() + predictNext(diffs)
}

fun sumOfPredictions(lines: List<String>) =
    lines.map { it.split(" ").map { it.toLong() } }.map { predictNext(it) }.sum()

fun predictPrev(list: List<Long>): Long = predictNext(list.reversed())

fun sumOfPredictionsBackwards(lines: List<String>) =
    lines.map { it.split(" ").map { it.toLong() } }.map { predictPrev(it) }.sum()