package aoc2021

fun splitToListPair(line: String) =
    line.trim().split(Regex("""\s*\|\s*""")).let {
        Pair(it[0].split(" "), it[1].split(" "))
    }

fun countSimpleOutputs(data: List<String>): Int =
    data.flatMap { splitToListPair(it).second.map { it.length } }
        .groupingBy { it }
        .eachCount()
        .let { (it[2] ?: 0) + (it[3] ?: 0) + (it[4] ?: 0) + (it[7] ?: 0)}