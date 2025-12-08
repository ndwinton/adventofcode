package aoc2025

fun tachyonSplitterCount(input: List<String>): Int {
    var beams = setOf(input.first().indexOf('S'))
    var splitters = 0

    input.drop(1).forEach { line ->
        beams = line.flatMapIndexed { index, ch ->
            when {
                ch == '.' && beams.contains(index) -> listOf(index)
                ch == '^' && beams.contains(index) -> {
                    splitters++
                    listOf(index - 1, index+1)
                }
                else -> emptyList()
            }
        }.toSet()
    }
    return splitters
}

fun tachyonPathCount(input: List<String>): Long {
    val lines = input.drop(1)
    val cache: MutableMap<Pair<Int, Int>, Long> = mutableMapOf()

    fun pathsAtIndex(lineIndex: Int, tachyonIndex: Int): Long = cache.getOrPut(Pair(lineIndex, tachyonIndex)) {
        when {
            lineIndex >= lines.size -> 1L
            lines[lineIndex][tachyonIndex] == '^' -> pathsAtIndex(lineIndex + 1, tachyonIndex - 1) + pathsAtIndex(lineIndex + 1, tachyonIndex + 1)
            else -> pathsAtIndex(lineIndex + 1, tachyonIndex)
        }
    }

    return pathsAtIndex(0, input.first().indexOf('S'))
}
