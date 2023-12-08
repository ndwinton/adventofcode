package aoc2023

import common.lcm

fun parseNetworkMap(lines: List<String>): Pair<String, Map<String, Pair<String, String>>> {
    val instructions = lines.first()
    val map = lines
        .drop(2)
        .map { it.split(Regex("""[^A-Z0-9]+""")) }
        .map { Pair(it[0], Pair(it[1], it[2])) }
        .toMap()
        return Pair(instructions, map)
}

tailrec fun traverseNetworkMap(node: String, depth: Int, instructions: String, map: Map<String, Pair<String, String>>, endSuffix: String = "ZZZ"): Int =
    if (node.endsWith(endSuffix)) depth else traverseNetworkMap(nextNode(node, depth, instructions, map), depth + 1, instructions, map, endSuffix)

private fun nextNode(node: String, depth: Int, instructions: String, map: Map<String, Pair<String, String>>): String =
    if (instructions[depth % instructions.length] == 'L') map[node]!!.first else map[node]!!.second

fun traverseNetworkMapMultiple(nodes: List<String>, depth: Int, instructions: String, map: Map<String, Pair<String, String>>): Long =
    nodes.map { traverseNetworkMap(it, 0, instructions, map, endSuffix = "Z").toLong() }
        .reduce { current, next -> current.lcm(next) }
