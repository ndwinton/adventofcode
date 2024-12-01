package aoc2024

import kotlin.math.abs

fun totalListDistance(lines: List<String>): Int {
    val first = lines.map { it.split(Regex("""\s+"""))[0].toInt() }.sorted()
    val second = lines.map { it.split(Regex("""\s+"""))[1].toInt() }.sorted()
    return first.zip(second).sumOf { abs(it.first - it.second) }
}

fun similarityScore(lines: List<String>): Long {
    val values = lines.map { it.split(Regex("""\s+"""))[0].toLong() }
    val counts = lines.map { it.split(Regex("""\s+"""))[1].toLong() }.groupingBy { it }.eachCount()
    return values.sumOf { it * counts.getOrDefault(it, 0) }
}