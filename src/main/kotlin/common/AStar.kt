package common

import aoc2021.GridNode
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