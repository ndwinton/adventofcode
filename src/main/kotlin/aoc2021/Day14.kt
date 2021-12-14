package aoc2021

fun polymerRules(lines: List<String>): Map<String,String> =
    lines.filter { it.contains("->") }
        .map { line -> line.split(" -> ").let { Pair(it[0], it[1]) } }
        .toMap()

fun polymerise(template: String, rules: Map<String,String>, steps: Int = 1): String =
    if (steps == 1) template
        .zipWithNext()
        .joinToString("") { it.first + (rules["${it.first}${it.second}"] ?: "") } + template[template.length - 1]
    else polymerise(polymerise(template, rules, steps - 1), rules)

fun polymerPart1(lines: List<String>): Int =
    polymerise(lines[0], polymerRules(lines), 10).let { polymer ->
        val counts = polymer.groupingBy { it }.eachCount()
        counts.maxOf { it.value } - counts.minOf { it.value }
    }

fun polymerPart2(lines: List<String>, steps: Int): Long {
    val charCounts = initialCharCounts(lines[0]) // NOTE: Mutable!
    val rules: Map<String,String> = polymerRules(lines)

    fun updateCharCounts(coupleCounts: Map<String, Long>) {
        coupleCounts.forEach { (couple, total) ->
            charCounts[rules[couple]!!] = (charCounts[rules[couple]] ?: 0L) + total
        }
    }

    fun poly(coupleCounts: Map<String, Long>): Map<String, Long> {
        updateCharCounts(coupleCounts)

        return coupleCounts
            .flatMap { (couple, total) ->
                listOf(Pair("${couple[0]}${rules[couple]!!}", total), Pair("${rules[couple]!!}${couple[1]}", total))
            }
            .groupingBy { it.first }
            .aggregate { key, accumulator: Long?, element, first ->
                if (first) element.second
                else accumulator!! + element.second
            }
    }

    var coupleCounts = initialCoupleCounts(lines[0])

    (1 .. steps).forEach { coupleCounts = poly(coupleCounts) }

    return charCounts.maxOf { it.value } - charCounts.minOf { it.value }
}

private fun initialCharCounts(start: String) =
    start.groupingBy { it }
        .eachCount()
        .map { (k, v) -> Pair(k.toString(), v.toLong()) }
        .toMap()
        .toMutableMap()

private fun initialCoupleCounts(start: String) = start
    .zipWithNext { a, b -> "$a$b" }
    .groupingBy { it }
    .eachCount()
    .map { (key, value) -> Pair(key, value.toLong()) }
    .toMap()



