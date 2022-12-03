package aoc2022

private val priorities = ('a' .. 'z').joinToString("") + ('A' .. 'Z').joinToString("")

fun rucksackPriority(rucksack: String): Int {
    val (first, second) = rucksack.chunked(rucksack.length / 2)
    val common = first.toList().intersect(second.toList()).first()
    return priorities.indexOf(common) + 1
}

fun rucksackPriorityTotal(rucksacks: List<String>) = rucksacks.sumOf { rucksackPriority(it) }

fun rucksackBadgeTotal(rucksacks: List<String>) =
    rucksacks.chunked(3).sumOf {
        val (first, second, third) = it
        priorities.indexOf(first.toList().intersect(second.toList()).intersect(third.toList()).first()) + 1
    }
