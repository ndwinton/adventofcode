package aoc2024

import common.combinations

fun uniqueAntinodes(grid: List<String>): Int =
    grid.flatMapIndexed  { row, line ->
            line.mapIndexed { col, char -> Pair(char, Pos(row, col)) }.filter { it.first != '.' } // List of Pair(Char, Pos)
        }.groupBy { it.first } // Map grouped by Char, value a list of the positions
        .map { Pair(it.key, it.value.map { pair -> pair.second }.combinations(2).toList()) }  // Pair-wise combinations of Pos
        .flatMap { pair -> pair.second.flatMap { findAntinodesForTwoNodes(it) }}
        .distinct()
        .filter { it.row in grid.indices && it.col in grid[0].indices }
        .size


private fun findAntinodesForTwoNodes(nodes: List<Pos>): List<Pos> {
    val rowDelta = nodes[1].row - nodes[0].row
    val colDelta = nodes[1].col - nodes[0].col
    return listOf(
        Pos(nodes[0].row - rowDelta, nodes[0].col - colDelta),
        Pos(nodes[1].row + rowDelta, nodes[1].col + colDelta)
    )
}

fun uniqueAntinodesExtended(grid: List<String>): Int =
    grid.flatMapIndexed  { row, line ->
        line.mapIndexed { col, char -> Pair(char, Pos(row, col)) }.filter { it.first != '.' } // List of Pair(Char, Pos)
    }.groupBy { it.first } // Map grouped by Char, value a list of the positions
        .map { Pair(it.key, it.value.map { pair -> pair.second }.combinations(2).toList()) }  // Pair-wise combinations of Pos
        .flatMap { pair ->
            pair.second.flatMap {
                findExtendedAntinodesBefore(it, grid.indices, grid[0].indices) + findExtendedAntinodesAfter(
                    it,
                    grid.indices,
                    grid[0].indices
                ) + it
            }
        }
        .distinct()
        .size

private tailrec fun findExtendedAntinodesBefore(nodes: List<Pos>, rowIndices: IntRange, colIndices: IntRange, result: List<Pos> = emptyList()): List<Pos> {
    val rowDelta = nodes[1].row - nodes[0].row
    val colDelta = nodes[1].col - nodes[0].col
    val before =  Pos(nodes[0].row - rowDelta, nodes[0].col - colDelta)
    return if (!(before.row in rowIndices && before.col in colIndices)) return result
    else findExtendedAntinodesBefore(listOf(before, nodes[0]), rowIndices, colIndices, result + before)
}

private tailrec fun findExtendedAntinodesAfter(nodes: List<Pos>, rowIndices: IntRange, colIndices: IntRange, result: List<Pos> = emptyList()): List<Pos> {
    val rowDelta = nodes[1].row - nodes[0].row
    val colDelta = nodes[1].col - nodes[0].col
    val after =  Pos(nodes[1].row + rowDelta, nodes[1].col + colDelta)
    return if (!(after.row in rowIndices && after.col in colIndices)) result
    else findExtendedAntinodesAfter(listOf(nodes[1], after), rowIndices, colIndices, result + after)
}