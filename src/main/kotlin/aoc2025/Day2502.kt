package aoc2025

val ID1_PATTERN = Regex("""^(\d+)\1$""")
val ID2_PATTERN = Regex("""^(\d+)(\1)+$""")

fun invalidIdsInRange(range: LongRange, regex: Regex = ID1_PATTERN) =
    range.asSequence()
        .filter { it.toString().matches(regex) }
        .toList()

fun sumOfInvalidIdsInRangeData(data: String, regex: Regex = ID1_PATTERN) =
    data.split(",")
        .map { it.split("-").let { parts -> LongRange(parts[0].toLong(), parts[1].toLong()) } }
        .flatMap { invalidIdsInRange(it, regex) }
        .sum()
