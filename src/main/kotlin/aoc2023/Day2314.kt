package aoc2023

import common.transpose

private val lineCache = mutableMapOf<String,String>()

private val gridCache = mutableMapOf<List<String>,List<String>>()

fun rollRockLine(line: String) = lineCache.getOrPut(line) {
    line.split("#")
        .joinToString("#") { it.replace(".", "") + ".".repeat(it.count { ch -> ch == '.' }) }
}

fun scoreRockLine(line: String) =
    line.reversed().mapIndexed { index, ch -> if (ch == 'O') index + 1 else 0 }.sum()

fun rollGridAndScore(lines: List<String>): Int =
    lines.transpose().sumOf { scoreRockLine(rollRockLine(it)) }

fun rollNorth(lines: List<String>) = lines.transpose().map { rollRockLine(it) }.transpose()

fun rollWest(lines: List<String>) = lines.map { rollRockLine(it) }

fun rollSouth(lines: List<String>) = rollNorth(lines.reversed()).reversed()

fun rollEast(lines: List<String>) = rollWest(lines.map { it.reversed() }).map { it.reversed() }

tailrec fun spinCycle(lines: List<String>, target: Int = 1000000000, repeat: Int = 0, cycle: List<List<String>> = emptyList()): List<String> {
    if (repeat >= target) return lines

    if (gridCache.contains(lines)) return findLinesInCache(lines, cycle, target)
    val nextLines = gridCache.getOrPut(lines) {
        //println("Miss on $repeat")
        rollEast(rollSouth(rollWest(rollNorth(lines))))
    }
    //println(scoreGrid(nextLines))
    return spinCycle(nextLines, target, repeat + 1, cycle + listOf(lines))
}

fun findLinesInCache(lines: List<String>, history: List<List<String>>, target: Int): List<String> {
    val index = history.indexOf(lines)
    val cycleSize = gridCache.size - index
    println("index $index, cycle $cycleSize")
    return gridCache.get(history[((target - 1 - index) % cycleSize) + index])!!
}

fun scoreGrid(lines: List<String>) = lines.transpose().sumOf { scoreRockLine(it) }