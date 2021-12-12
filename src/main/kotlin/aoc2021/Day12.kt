package aoc2021

fun findPaths(lines: List<String>, ruleFn: (String, List<String>) -> Boolean = ::part1Rule): List<String> {
    val connections = buildConnections(lines)

    fun findAllPaths(
        currentNode: String,
        inProgress: List<String>,
        ruleFn: (String, List<String>) -> Boolean
    ): List<List<String>> {
        return if (currentNode == "end") listOf(inProgress + listOf("end"))
        else (connections[currentNode] ?: emptyList())
            .filter { ruleFn(it, inProgress) }
            .flatMap { findAllPaths(it, inProgress + listOf(currentNode), ruleFn) }
    }

    return findAllPaths("start", emptyList(), ruleFn).map { it.joinToString(",") }
}

fun buildConnections(lines: List<String>): Map<String,Set<String>> =
    lines.flatMap { line -> line.split("-").let { listOf(Pair(it[0], it[1]), Pair(it[1], it[0])) } }
        .groupBy { it.first }
        .map { entry -> Pair(entry.key, entry.value.map { it.second }.toSet()) }
        .toMap()

fun part1Rule(node: String, inProgress: List<String>): Boolean = node[0].isUpperCase() || !inProgress.contains(node)