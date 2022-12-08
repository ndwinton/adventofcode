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



