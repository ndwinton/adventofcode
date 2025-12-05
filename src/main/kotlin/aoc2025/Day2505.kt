package aoc2025

fun countFreshIngredients(inventory: List<String>): Int {
    val freshRanges = inventory.takeWhile { it != "" }
        .map { line ->
            line.split("-")
                .map { it.toLong() }
                .let { LongRange(it[0], it[1]) }
        }
    return inventory.dropWhile { it != "" }
        .drop(1)
        .map { it.toLong() }
        .count { id -> freshRanges.any { it.contains(id) } }
}