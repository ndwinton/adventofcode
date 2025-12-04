package aoc2025

import common.get

fun countAccessibleRolls(grid: List<String>): Int {
    fun rollAt(rowNum: Int, colNum: Int) = if (grid[rowNum, colNum] == '@') 1 else 0

    fun countNeighbours(rowNum: Int, colNum: Int) =
        rollAt(rowNum - 1, colNum - 1) +
                rollAt(rowNum - 1, colNum) +
                rollAt(rowNum - 1, colNum + 1) +
                rollAt(rowNum, colNum - 1) +
                rollAt(rowNum, colNum + 1) +
                rollAt(rowNum + 1, colNum - 1) +
                rollAt(rowNum + 1, colNum) +
                rollAt(rowNum + 1, colNum + 1)

    return grid.mapIndexed { rowNum, row ->
        row.mapIndexed { colNum, cell ->
            if (cell == '@') countNeighbours(rowNum, colNum) else -1
        }.count { it in 0..3 }
    }.sum()
}