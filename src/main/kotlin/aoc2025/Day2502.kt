package aoc2025

fun invalidIdsInRange(range: LongRange) =
    range.asSequence()
        .filter { it.toString().matches(Regex("""^(\d+)\1$""")) }
        .toList()

fun sumOfInvalidIdsInRangeData(data: String) =
    data.split(",")
        .map { it.split("-").let { parts -> LongRange(parts[0].toLong(), parts[1].toLong()) } }
        .flatMap { invalidIdsInRange(it) }
        .sum()