package aoc2024

import common.get
data class Pos(val row: Int, val col: Int)

fun distinctVisited(grid: List<String>): Int {
    val start = grid.mapIndexed { row, line -> if (line.contains('^')) Pos(row, line.indexOf('^')) else Pos(-1, -1) }
        .find { it != Pos(-1, -1) }!!
    return walkTheGrid(grid, start, Pos(-1, 0), emptyList()).distinct().count()
}

private tailrec fun walkTheGrid(grid: List<String>, current: Pos, direction: Pos, result: List<Pos>): List<Pos> {
    val next = Pos(current.row + direction.row, current.col + direction.col)
    return when {
        next.row < 0 || next.row >= grid.size -> result + current
        next.col < 0 || next.col >= grid[0].length -> result + current
        grid[next.row, next.col] == '#' -> {
            val newDirection = when (direction) {
                Pos(-1, 0) -> Pos(0, 1)
                Pos(0, 1) -> Pos(1, 0)
                Pos(1, 0) -> Pos(0, -1)
                else -> Pos(-1, 0)
            }
            walkTheGrid(grid, current, newDirection, result)
        }
        else -> walkTheGrid(grid, next, direction, result + current)
    }
}

fun loopForcingPositions(grid: List<String>) : Int {
    val start = grid.mapIndexed { row, line -> if (line.contains('^')) Pos(row, line.indexOf('^')) else Pos(-1, -1) }
        .find { it != Pos(-1, -1) }!!
    return grid.flatMapIndexed { row: Int, line: String ->
        line.mapIndexed { col, char ->
            //println("$row, $col, $char")
            if (char == '.') checkLoopWithObstacle(grid, start, Pos(-1, 0), Pos(row, col), emptyList())
            else false
        }
    }.count { it }
}

private tailrec fun checkLoopWithObstacle(grid: List<String>, current: Pos, direction: Pos, obstacle: Pos, result: List<Pair<Pos,Pos>>) : Boolean {
    val next = Pos(current.row + direction.row, current.col + direction.col)
    return when {
        result.contains(Pair(current, direction)) -> true
        next.row < 0 || next.row >= grid.size -> false
        next.col < 0 || next.col >= grid[0].length -> false
        grid[next.row, next.col] == '#' || Pos(next.row, next.col) == obstacle -> {
            val newDirection = when (direction) {
                Pos(-1, 0) -> Pos(0, 1)
                Pos(0, 1) -> Pos(1, 0)
                Pos(1, 0) -> Pos(0, -1)
                else -> Pos(-1, 0)
            }
            checkLoopWithObstacle(grid, current, newDirection, obstacle, result + Pair(current, direction))
        }
        else -> checkLoopWithObstacle(grid, next, direction, obstacle, result + Pair(current, direction))
    }
}
