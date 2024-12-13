package aoc2024

import common.combinations

fun uniqueAntinodes(grid: List<String>): Int =
    grid.flatMapIndexed  { row, line ->
            line.mapIndexed { col, char -> Pair(char, Pos(row, col)) }.filter { it.first != '.' }
        }.groupBy { it.first }
        .map { Pair(it.key, it.value.map { pair -> pair.second }.combinations(2).toList()) }
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
