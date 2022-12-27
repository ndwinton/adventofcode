package aoc2022

import common.Node
import common.aStar
import kotlin.math.abs

class HeightMapNode(val row: Int, val col: Int, val height: Char) : Node {
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

    override fun weight(neighbour: Node): Int =
        (neighbour as HeightMapNode).let {
            val myHeight = if (height == 'S') 'a' else height
            val otherHeight = if (it.height == 'E') 'z' else it.height
            when {
                otherHeight == '*' -> 1000
                otherHeight > myHeight + 1 -> 1000
                else -> myHeight - otherHeight + 1
            }
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HeightMapNode

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
        return "HeightMapNode(row=$row, col=$col, height=$height, fScore=$fScore, gScore=$gScore)"
    }

    companion object {
        private var grid: Array<Array<HeightMapNode>> = arrayOf()
        val edgeSentinel = HeightMapNode(-1, -1, '*')

        fun loadGrid(lines: List<String>) {
            grid = lines.mapIndexed { row, line -> line.mapIndexed { col, ch -> HeightMapNode(row, col, ch) }.toTypedArray() }.toTypedArray()
        }

        fun getNode(row: Int, col: Int): HeightMapNode =
            if (row < 0 || row >= grid.size|| col < 0 || col >= grid[0].size) edgeSentinel else grid[row][col]

        fun nodeWithHeight(height: Char) =
            grid.first { it.map { node -> node.height }.contains(height) }.first { it.height == height }

        fun allNodesWithHeight(height: Char) = grid.flatMap { row -> row.filter { it.height == height } }
    }
}

fun manhattan(start: Node, end: Node): Int {
    val startHeightMapNode = start as HeightMapNode
    val endHeightMapNode = end as HeightMapNode
    return abs(endHeightMapNode.col - startHeightMapNode.col) + abs(endHeightMapNode.row - startHeightMapNode.row)
}

fun fewestStepsToEndpoint(lines: List<String>): Int {
    HeightMapNode.loadGrid(lines)
    val path = aStar(HeightMapNode.nodeWithHeight('S'), HeightMapNode.nodeWithHeight('E'), ::manhattan)
    return path.size - 1
}

fun fewestFromAnyA(lines: List<String>): Int {
    HeightMapNode.loadGrid(lines)
    val end = HeightMapNode.nodeWithHeight('E')
    return HeightMapNode.allNodesWithHeight('a').map { aStar(it, end, ::manhattan).size }.filter { it > 0 }.min() - 1
}