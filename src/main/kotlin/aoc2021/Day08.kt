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

fun decodeSegments(data: List<String>): Map<String,String> {
    val counts = data.flatMap { it.split("") }.filter { it.isNotEmpty() }.groupingBy { it }.eachCount()
    val byLength = data.groupBy { it.length }
    return counts.map { letter ->
        val segment = when (letter.value) {
            4 -> "E"
            6 -> "B"
            7 -> if (byLength.getOrDefault(4, emptyList()).any { it.contains(letter.key) }) "D" else "G"
            8 -> if (byLength.getOrDefault(2, emptyList()).any { it.contains(letter.key) }) "C" else "A"
            9 -> "F"
            else -> "*"
        }
        Pair(letter.key, segment)
    }.toMap()
}

fun decodeDigits(segmentMap: Map<String,String>, digits: List<String>): Int {
    val displayMap = mapOf(
        "ABCEFG" to "0",
        "CF" to "1",
        "ACDEG" to "2",
        "ACDFG" to "3",
        "BCDF" to "4",
        "ABDFG" to "5",
        "ABDEFG" to "6",
        "ACF" to "7",
        "ABCDEFG" to "8",
        "ABCDFG" to "9"
    )

    return digits.map { digit ->
        digit.map { segmentMap["" + it] ?: "" }.sorted().joinToString("")
    }.joinToString("") { displayMap[it] ?: "" }.toInt()
}

fun sumOfAllDecoded(data: List<String>) =
    data.sumOf { line -> splitToListPair(line).let { decodeDigits(decodeSegments(it.first), it.second) } }