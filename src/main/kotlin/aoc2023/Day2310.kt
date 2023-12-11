package aoc2023

import common.get

const val UP_CHARS = "S7|F"
const val DOWN_CHARS = "SJ|L"
const val LEFT_CHARS = "SL-F"
const val RIGHT_CHARS = "S7-J"

typealias PipeMap = List<String>

data class Coord(val row: Int, val col: Int)

val Nowhere = Coord(-1, -1)

data class AllowedMove(val up: String, val down: String, val left: String, val right: String)

val AllowedMap = mapOf(
    'S' to AllowedMove(UP_CHARS, DOWN_CHARS, LEFT_CHARS, RIGHT_CHARS),
    '|' to AllowedMove(UP_CHARS, DOWN_CHARS, "", ""),
    '-' to AllowedMove("", "", LEFT_CHARS, RIGHT_CHARS),
    '7' to AllowedMove("", DOWN_CHARS, LEFT_CHARS, ""),
    'F' to AllowedMove("", DOWN_CHARS, "", RIGHT_CHARS),
    'J' to AllowedMove(UP_CHARS, "", LEFT_CHARS, ""),
    'L' to AllowedMove(UP_CHARS, "", "", RIGHT_CHARS),
    '.' to AllowedMove("", "", "", ""),
)

fun findStart(pipes: PipeMap) =
    pipes.mapIndexed { row, line -> Pair(row, line) }
        .filter { it.second.contains("S") }
        .map { Coord(it.first, it.second.indexOf('S')) }
        .first()

tailrec fun findPath(pipes: PipeMap, current: Coord, path: List<Coord>): List<Coord> {
    if (pipes[current.row, current.col] == 'S' && path.size > 1) return path
    val candidates = nextAllowedMoves(pipes, current, path)
    if (candidates.isEmpty()) return emptyList()
    return findPath(pipes, candidates.first(), path + candidates.first())
}

fun nextAllowedMoves(pipes: PipeMap, current: Coord, path: List<Coord>): List<Coord> {
    val allowed = AllowedMap[pipes[current.row, current.col]]!!
    return listOf(
        if (allowed.up.contains(pipes[current.row - 1, current.col])) Coord(current.row - 1, current.col) else Nowhere,
        if (allowed.down.contains(pipes[current.row + 1, current.col])) Coord(current.row + 1, current.col) else Nowhere,
        if (allowed.left.contains(pipes[current.row, current.col - 1])) Coord(current.row, current.col - 1) else Nowhere,
        if (allowed.right.contains(pipes[current.row, current.col + 1])) Coord(current.row, current.col + 1) else Nowhere,
    ).filter { it != Nowhere && (path.size <= 1 || it != path[path.size - 2]) }
}

fun findPipeLength(pipes: PipeMap): Int {
    val start = findStart(pipes)
    val path = findPath(pipes, start, listOf(start))
    return path.size / 2
}

fun countEnclosed(pipes: PipeMap): Int {
    val start = findStart(pipes)
    val path = findPath(pipes, start, listOf(start))
    return (pipes.indices).sumOf { row -> countEnclosedForLine(row, pipes, path) }
}

fun countEnclosedForLine(row: Int, pipes: PipeMap, path: List<Coord>): Int =
    pipes[row].indices.count { col -> isEnclosed(row, col, pipes, path) }


fun isEnclosed(row: Int, col: Int, pipes: List<String>, path: List<Coord>): Boolean {
    if (path.contains(Coord(row, col))) return false
    val crossings = (col until pipes[row].length)
        .map { if (path.contains(Coord(row, it))) pipes[row, it] else '.' }
        .filter { it != '-' && it != '.' }
        .joinToString("")
        .replace(Regex("""(F7|LJ)"""),"")   // Discard point crossing at U-bends
        .replace(Regex("""(FJ|L7)"""), "+") // Consolidate elbow joins
        .length
    return (crossings % 2) == 1
}
