package aoc2024

import common.get

fun countXmases(grid: List<String>): Int =
    grid.mapIndexed { row, line ->
        line.mapIndexed { col, char ->
            if (char == 'X') countXmasesAtPoint(grid, row, col) else 0
        }.sum()
    }.sum()

private fun countXmasesAtPoint(grid: List<String>, row: Int, col: Int): Int {
    val east = "" + grid[row, col] + grid[row, col + 1] + grid[row, col + 2] + grid[row, col + 3]
    val west = "" + grid[row, col] + grid[row, col - 1] + grid[row, col - 2] + grid[row, col - 3]
    val south = "" + grid[row, col] + grid[row + 1, col] + grid[row + 2, col] + grid[row + 3, col]
    val north = "" + grid[row, col] + grid[row - 1, col] + grid[row - 2, col] + grid[row - 3, col]
    val northEast = "" + grid[row, col] + grid[row - 1, col + 1] + grid[row - 2, col + 2] + grid[row - 3, col + 3]
    val southEast = "" + grid[row, col] + grid[row + 1, col + 1] + grid[row + 2, col + 2] + grid[row + 3, col + 3]
    val northWest = "" + grid[row, col] + grid[row - 1, col - 1] + grid[row - 2, col - 2] + grid[row - 3, col - 3]
    val southWest = "" + grid[row, col] + grid[row + 1, col - 1] + grid[row + 2, col - 2] + grid[row + 3, col - 3]
    return listOf(north, south, east, west, northEast, northWest, southEast, southWest).count { it == "XMAS" }
}


fun countCrossMases(grid: List<String>): Int =
    grid.mapIndexed { row, line ->
        line.mapIndexed { col, _ ->
            if (hasCrossMasAtPoint(grid, row, col)) 1 else 0
        }.sum()
    }.sum()

private fun hasCrossMasAtPoint(grid: List<String>, row: Int, col: Int): Boolean {
    val topToBottom = "" + grid[row - 1, col - 1] + grid[row, col] + grid[row + 1, col + 1]
    val bottomToTop = "" + grid[row + 1, col - 1] + grid[row, col] + grid[row - 1, col + 1]

    return (topToBottom == "MAS" || topToBottom.reversed() == "MAS") && (bottomToTop == "MAS" || bottomToTop.reversed() == "MAS")
}