package aoc2022

fun parseToPairOfRanges(line: String) =
    Regex("""(\d+)-(\d+),(\d+)-(\d+)""")
        .find(line)!!
        .let {
            Pair(
                IntRange(it.groupValues[1].toInt(), it.groupValues[2].toInt()),
                IntRange(it.groupValues[3].toInt(), it.groupValues[4].toInt())
            )
        }

fun rangePairHasFullContainment(pair: Pair<IntRange,IntRange>): Boolean {
    val a = pair.first.toSet()
    val b = pair.second.toSet()
    return a.intersect(b) == b || b.intersect(a) == a
}

fun rangePairHasOverlap(pair: Pair<IntRange, IntRange>): Boolean {
    val a = pair.first.toSet()
    val b = pair.second.toSet()
    return a.intersect(b).isNotEmpty()
}

fun countOfFullyContainedRanges(lines: List<String>) = lines.count { rangePairHasFullContainment(parseToPairOfRanges(it)) }

fun countOfOverlappingRanges(lines: List<String>) = lines.count { rangePairHasOverlap(parseToPairOfRanges(it)) }