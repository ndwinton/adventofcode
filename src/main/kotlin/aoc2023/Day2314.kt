package aoc2023

import common.transpose


fun rollRockLine(line: String) =
    line.split("#")
        .joinToString("#") { it.replace(".", "") + ".".repeat(it.count { ch -> ch == '.' }) }

fun scoreRockLine(line: String) =
    line.reversed().mapIndexed { index, ch -> if (ch == 'O') index + 1 else 0 }.sum()

fun scoreRockGrid(lines: List<String>): Int =
    lines.transpose().sumOf { scoreRockLine(rollRockLine(it)) }
