package aoc2025

import common.get

private fun List<String>.countNeighbours(row: Int, col: Int): Int {
    fun rollAt(row: Int, col: Int) = if (this[row, col] == '@') 1 else 0

    return  rollAt(row - 1, col - 1) +
            rollAt(row - 1, col) +
            rollAt(row - 1, col + 1) +
            rollAt(row, col - 1) +
            rollAt(row, col + 1) +
            rollAt(row + 1, col - 1) +
            rollAt(row + 1, col) +
            rollAt(row + 1, col + 1)
}

fun countAccessibleRolls(grid: List<String>): Int =
    grid.mapIndexed { rowNum, row ->
        row.mapIndexed { colNum, cell -> if (cell == '@') grid.countNeighbours(rowNum, colNum) else -1 }
            .count { it in 0..3 }
    }.sum()

tailrec fun countRemovedRolls(grid: List<String>, removed: Int = 0): Int {
    val removable = countAccessibleRolls(grid)
    if (removable == 0) return removed

    val newGrid = grid.mapIndexed { rowNum, row ->
        row.mapIndexed { colNum, cell ->
            if (cell == '@' && grid.countNeighbours(rowNum, colNum) in 0 .. 3) '.' else cell
        }.joinToString("")
    }

    return countRemovedRolls(newGrid, removed + removable)
}