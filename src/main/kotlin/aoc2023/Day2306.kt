package aoc2023

fun parseRaceData(lines: List<String>): List<Pair<Int, Int>> =
    lines[0].split(Regex("""\s+""")).drop(1).map { it.toInt() }
        .zip(lines[1].split(Regex("""\s+""")).drop(1).map { it.toInt() })

fun distanceMovedForButtonPress(press: Int, raceDuration: Int): Int = (raceDuration - press) * press

fun numberOfWinningTimes(duration: Int, record: Int) =
    (0 .. duration).map { distanceMovedForButtonPress(it, duration) }.count { it > record }

fun boatRaceAnswerPart1(lines: List<String>): Int =
    parseRaceData(lines).map { numberOfWinningTimes(it.first, it.second) }.fold(1) { acc, i -> acc * i }
