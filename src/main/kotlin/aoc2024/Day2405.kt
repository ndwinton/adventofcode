package aoc2024

fun parsePageOrderingRules(lines: List<String>) =
    lines.filter { it.contains("|") }
        .map { line -> line.split("|").let { Pair(it[0].toInt(), it[1].toInt()) } }
        .groupBy { it.first }
        .map { group -> Pair(group.key, group.value.map { it.second }) }
        .toMap()

fun sumMidpointsOfOrderedUpdates(lines: List<String>): Int {
    val rules = parsePageOrderingRules(lines)
    return lines.filter { it.contains(",") }
        .map { line -> line.split(",").map { it.toInt() } }
        .sumOf { if (isOrderedCorrectly(it, rules)) it[it.size / 2] else 0 }
}

private tailrec fun isOrderedCorrectly(pages: List<Int>, rules: Map<Int, List<Int>>): Boolean =
    when {
        pages.isEmpty() -> true
        rules.getOrDefault(pages.last(), emptyList()).any { pages.contains(it) } -> false
        else -> isOrderedCorrectly(pages.dropLast(1), rules)
    }

fun sumMidpointsOfUnorderedUpdates(lines: List<String>): Int {
    val rules = parsePageOrderingRules(lines)
    return lines.filter { it.contains(",") }
        .map { line -> line.split(",").map { it.toInt() } }
        .filter { !isOrderedCorrectly(it, rules) }
        .map { it.sortedWith { a, b -> if (rules.getOrDefault(b, emptyList()).contains(a)) -1 else 0 } }
        .sumOf { it[it.size / 2] }
}