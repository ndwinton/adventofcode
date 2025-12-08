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