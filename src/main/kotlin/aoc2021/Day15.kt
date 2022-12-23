package aoc2021

import common.Node
import common.aStar
import kotlin.math.abs

class GridNode(val row: Int, val col: Int, val weight: Int) : Node {
    override var cameFrom: Node = this
    override var fScore: Int = Int.MAX_VALUE
    override var gScore: Int = Int.MAX_VALUE

    override fun neighbours(): List<Node> {
        return listOf(
            getNode(row - 1, col),
            getNode(row, col - 1),
            getNode(row, col + 1),
            getNode(row + 1, col)
        ).filter { it != edgeSentinel }
    }

    override fun weight(neighbour: Node): Int = (neighbour as GridNode).weight

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GridNode

        if (row != other.row) return false
        if (col != other.col) return false

        return true
    }

    override fun hashCode(): Int {
        var result = row
        result = 31 * result + col
        return result
    }

    override fun toString(): String {
        return "GridNode(row=$row, col=$col, weight=$weight, fScore=$fScore, gScore=$gScore)"
    }

    companion object {
        private var grid: Array<Array<GridNode>> = arrayOf()
        val edgeSentinel = GridNode(-1, -1, -1)

        fun loadGrid(lines: List<String>) {
            grid = lines.mapIndexed { row, line -> line.mapIndexed { col, ch -> GridNode(row, col, ch.digitToInt()) }.toTypedArray() }.toTypedArray()
        }


        fun getNode(row: Int, col: Int): GridNode =
            if (row < 0 || row >= grid.size|| col < 0 || col >= grid[0].size) edgeSentinel else grid[row][col]

    }
}

fun manhattan(start: Node, end: Node): Int {
    val startGridNode = start as GridNode
    val endGridNode = end as GridNode
    return abs(endGridNode.col - startGridNode.col) + abs(end.row - startGridNode.row)
}
fun day15part1(lines: List<String>): Int {
    GridNode.loadGrid(lines)
    val path = aStar(GridNode.getNode(0, 0), GridNode.getNode(lines.size - 1, lines[0].length - 1), ::manhattan)
    println(path)
    return path.map { it.weight(it) }.drop(1).sum()
}

fun makeExpandedMap(lines: List<String>): List<String> =
    lines.map { expandColumns(it, 4, it) }.let { expandRows(it, 4, it) }

fun incrementDigits(line: String) =
    line.map { ch -> (ch.digitToInt() + 1).let { if (it > 9) 1 else it } }.joinToString("")

fun expandColumns(line: String, repeat: Int, result: String): String =
    if (repeat == 0) result
    else expandColumns(incrementDigits(line), repeat - 1, result + incrementDigits(line))

fun expandRows(lines: List<String>, repeat: Int, result: List<String>): List<String> =
    if (repeat == 0) result
    else expandRows(lines.map { incrementDigits(it) }, repeat - 1, result + lines.map { incrementDigits(it) })
