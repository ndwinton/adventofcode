package aoc2022

import common.transpose

fun rowVisibilityLeftToRight(row: List<Int>, maxSeen: Int = -1, results: List<Boolean> = emptyList()): List<Boolean> =
    when {
        row.isEmpty() -> results
        (row.first() > maxSeen) -> rowVisibilityLeftToRight(row.drop(1), row.first(), results + true)
        else -> rowVisibilityLeftToRight(row.drop(1), maxSeen, results + false)
    }

fun rowVisibility(row: List<Int>) =
    rowVisibilityLeftToRight(row)
        .zip(rowVisibilityLeftToRight(row.reversed()).reversed())
        .map { it.first || it.second }

fun parseTreeHeightMap(lines: List<String>) = lines.map { line -> line.toList().map { it - '0' } }

fun heightMapVisbility(heightMap: List<List<Int>>): List<List<Boolean>> {
    val fromRows = heightMap.map { rowVisibility(it) }
    val fromCols = heightMap.transpose().map { rowVisibility(it) }.transpose()
    return fromRows.zip(fromCols).map { rowPair -> rowPair.first.zip(rowPair.second).map { it.first || it.second} }
}

fun visibilityCount(input: List<String>) = heightMapVisbility(parseTreeHeightMap(input)).sumOf { row -> row.count { it } }

tailrec fun scenicScoresLookingRight(row: List<Int>, results: List<Int> = emptyList()): List<Int> {
    val head = row.first()
    val tail = row.drop(1)
    return when {
        // right hand end
        tail.isEmpty() -> results + 0
        // taller than anything to the right
        tail.all { it < head } -> scenicScoresLookingRight(tail, results + tail.size)
        // blocked somewhere to the right
        else -> scenicScoresLookingRight(tail, results + (tail.takeWhile {it < head }.count() + 1))
    }
}

fun scenicScoresForRow(row: List<Int>) =
    scenicScoresLookingRight(row)
        .zip(scenicScoresLookingRight(row.reversed()).reversed())
        .map { it.first * it.second }

fun heightMapScenicScores(heightMap: List<List<Int>>): List<List<Int>> {
    val fromRows = heightMap.map { scenicScoresForRow(it) }
    val fromCols = heightMap.transpose().map { scenicScoresForRow(it) }.transpose()
    return fromRows.zip(fromCols).map { rowPair -> rowPair.first.zip(rowPair.second).map { it.first * it.second } }
}

fun maxScenicScore(input: List<String>) = heightMapScenicScores(parseTreeHeightMap(input)).maxOf { row -> row.max() }


