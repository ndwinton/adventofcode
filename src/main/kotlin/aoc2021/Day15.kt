package aoc2021

import java.util.*
import kotlin.math.abs

interface Node {
    var cameFrom: Node
    var fScore: Int
    var gScore: Int
    fun neighbours(): List<Node>
    fun weight(neighbour: Node): Int
}

typealias Heuristic = (current: Node, goal: Node) -> Int

fun aStar(start: Node, goal: Node, h: Heuristic): List<Node> {
    val openSet: PriorityQueue<Node> = PriorityQueue(compareBy { it.fScore })
    openSet.add(start)

    fun reconstructPath(start: Node, current: Node): List<Node> {
        val totalPath = mutableListOf(current)
        var next = current
        do {
            next = next.cameFrom
            totalPath.add(next)
        }
        while (next != start)

        return totalPath.reversed()
    }

    start.gScore = 0
    start.fScore = h(start, goal)

    while (openSet.isNotEmpty()) {
        val current = openSet.remove()
        if (current == goal) return reconstructPath(start, current)

        current.neighbours().forEach { neighbour ->
            val tentativeGScore = current.gScore + current.weight(neighbour)
            if (tentativeGScore < neighbour.gScore) {
                neighbour.cameFrom = current
                neighbour.gScore = tentativeGScore
                neighbour.fScore = tentativeGScore + h(neighbour, goal)
                if (openSet.contains(neighbour)) openSet.remove(neighbour)
                openSet.add(neighbour)
            }
        }
    }

    return emptyList()
}

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
    val path = aStar(GridNode.getNode(0, 0), GridNode.getNode(99, 99), ::manhattan)
    println(path)
    return path.map { it.weight(it) }.drop(1).sum()
}