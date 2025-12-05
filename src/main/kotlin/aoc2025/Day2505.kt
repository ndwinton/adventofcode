package aoc2025

import kotlin.math.max

fun countFreshIngredients(inventory: List<String>): Int {
    val freshRanges = extractRanges(inventory)
    return inventory.dropWhile { it != "" }
        .drop(1)
        .map { it.toLong() }
        .count { id -> freshRanges.any { it.contains(id) } }
}

private fun extractRanges(inventory: List<String>) = inventory.takeWhile { it != "" }
    .map { line ->
        line.split("-")
            .map { it.toLong() }
            .let { if (it[0] <= it[1]) LongRange(it[0], it[1]) else LongRange(it[1], it[0]) }
    }

private tailrec fun consolidateRanges(consolidated: List<LongRange>, remaining: List<LongRange>): List<LongRange> {
    if (remaining.isEmpty()) return consolidated

    val previous = consolidated.last()
    val next = remaining.first()
    val toAdd = if (previous.contains(next.first)) listOf(LongRange(previous.first, max(previous.last, next.last))) else listOf(previous, next)

    return consolidateRanges(consolidated.dropLast(1) + toAdd, remaining.drop(1))
}

fun totalPossibleFreshIngredients(inventory: List<String>): Long {
    val ranges = extractRanges(inventory).sortedWith { a, b -> when {
        a.first < b.first -> -1
        a.first > b.first -> 1
        else -> a.last.compareTo(b.last)
    }}

    val consolidated = consolidateRanges(listOf(ranges.first()), ranges.drop(1))

    crossCheckRanges(ranges, consolidated) // Debugging purposes only

    return consolidated.sumOf { it.last - it.first + 1 }
}

// Helped me with debugging – not a part of the solution
private fun crossCheckRanges(ranges: List<LongRange>, consolidated: List<LongRange>) {
    consolidated.forEachIndexed { i, me ->
        consolidated.forEachIndexed { j, range ->
            if (i != j) {
                if (range.contains(me.first) || range.contains(me.last)) {
                    println("Range $i [$me] overlaps with range $j [$range]")
                }
            }
        }
    }

    ranges.forEach { range ->
        if (!consolidated.any { it.contains(range.first) } || !consolidated.any { it.contains(range.last) }) {
            println("Range $range not found")
        }
    }
}