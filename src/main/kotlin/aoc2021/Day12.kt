package aoc2021

fun findPaths(lines: List<String>): List<String> {
    val connections = buildConnections(lines)

    fun findAllPaths(
        currentNode: String,
        finished: List<List<String>>,
        inProgress: List<String>
    ): List<List<String>> {
        return if (currentNode == "end") finished + listOf(inProgress + listOf("end"));
        else (connections[currentNode] ?: emptyList())
            .filter { node -> node[0].isUpperCase() || !inProgress.contains(node) }
            .flatMap { findAllPaths(it, finished, inProgress + listOf(currentNode)) }
    }

    return findAllPaths("start", emptyList(), emptyList()).map { it.joinToString(",") }
}

fun buildConnections(lines: List<String>): Map<String,Set<String>> =
    lines.flatMap { line -> line.split("-").let { listOf(Pair(it[0], it[1]), Pair(it[1], it[0])) } }
        .groupBy { it.first }
        .map { entry -> Pair(entry.key, entry.value.map { it.second }.toSet()) }
        .toMap()