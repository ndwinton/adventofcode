package aoc2024

import common.get

fun countXmases(rows: List<String>) : Int {
    return rows.mapIndexed { rowNum, row ->
        row.mapIndexed { colNum, char ->
            if (char == 'X') countXmasesAtPoint(rows, rowNum, colNum) else 0
        }.sum()
    }.sum()
}

private fun countXmasesAtPoint(rows: List<String>, rowNum: Int, colNum: Int): Int {
    val east = "" + rows[rowNum, colNum] + rows[rowNum, colNum + 1] + rows[rowNum, colNum + 2] + rows[rowNum, colNum + 3]
    val west = "" + rows[rowNum, colNum] + rows[rowNum, colNum - 1] + rows[rowNum, colNum - 2] + rows[rowNum, colNum - 3]
    val south = "" + rows[rowNum, colNum] + rows[rowNum + 1, colNum] + rows[rowNum + 2, colNum] + rows[rowNum + 3, colNum]
    val north = "" + rows[rowNum, colNum] + rows[rowNum - 1, colNum] + rows[rowNum - 2, colNum] + rows[rowNum - 3, colNum]
    val northEast = "" + rows[rowNum, colNum] + rows[rowNum - 1, colNum + 1] + rows[rowNum - 2, colNum + 2] + rows[rowNum - 3, colNum + 3]
    val southEast = "" + rows[rowNum, colNum] + rows[rowNum + 1, colNum + 1] + rows[rowNum + 2, colNum + 2] + rows[rowNum + 3, colNum + 3]
    val northWest = "" + rows[rowNum, colNum] + rows[rowNum - 1, colNum - 1] + rows[rowNum - 2, colNum - 2] + rows[rowNum - 3, colNum - 3]
    val southWest = "" + rows[rowNum, colNum] + rows[rowNum + 1, colNum - 1] + rows[rowNum + 2, colNum - 2] + rows[rowNum + 3, colNum - 3]
    return listOf(north, south, east, west, northEast, northWest, southEast, southWest).count { it == "XMAS" }
}
