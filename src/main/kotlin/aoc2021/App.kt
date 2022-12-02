/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package aoc2021

import java.io.File
import java.io.Reader

fun main(args: Array<String>) {
    when (args[0]) {
        "1" -> runDay01()
        "2" -> runDay02()
        "3" -> runDay03()
        "4" -> runDay04()
        "5" -> runDay05()
        "6" -> runDay06()
        "7" -> runDay07()
        "8" -> runDay08()
        "9" -> runDay09()
        "10" -> runDay10()
        "11" -> runDay11()
        "12" -> runDay12()
        "13" -> runDay13()
        "14" -> runDay14()
        "15" -> runDay15()
        "16" -> runDay16()
        "17" -> runDay17()
        "18" -> runDay18()
        "19" -> runDay19()
        "20" -> runDay20()
        "21" -> runDay21()
        else -> println("""
            https://adventofcode.com/2021/
            
            Usage: App day-number
            
            Inputs are assumed to be in 'inputs/NN.txt' where 'NN' is the 2-digit day number.
            If the file isn't present, it reads from stdin instead.
            """.trimIndent())
    }
}

fun runDay01() {
    println("Part 1: " + Day01.numberOfIncreases(inputAsIntList(1)))
    println("Part 2: " + Day01.numberOfIncreases(inputAsIntList(1), window = 3))
}

fun runDay02() {
    println("Part 1: " + Day02.moveProduct(inputLines(2)))
    println("Part 2: " + Day02.moveWithAimProduct(inputLines(2)))
}

fun runDay03() {
    println("Part 1: " + Day03.gammaEpsilonProduct(inputLines(3)))
    println("Part 2: " + Day03.gasesProduct(inputLines(3)))
}

fun runDay04() {
    println("Part 1: " + Day04.findWinningScore(inputLines(4)))
    println("Part 2: " + Day04.findLastWinningScore(inputLines(4)))
}

fun runDay05() {
    println("Part 1: " + Day05.countIntersections(inputLines(5)))
    println("Part 2: " + Day05.countIntersections(inputLines(5), includeDiagonals = true))
}

fun runDay06() {
    println("Part 1: " + simulateShoal(80, Shoal(*(inputLineAsIntList(inputLines(6).first()).toIntArray()))))
    println("Part 2: " + simulateCountedShoal(256, CountedShoal(inputLineAsIntList(
        inputLines(6).first()).groupingBy { it }.eachCount().map { Pair(it.key, it.value.toLong()) }.toMap())))
}

fun runDay07() {
    println("Part 1: " + minimumAlignmentCost(inputLineAsIntList(inputLines(7).first()), ::alignmentCost))
    println("Part 2: " + minimumAlignmentCost(inputLineAsIntList(inputLines(7).first()), ::alignmentCost2))
}

fun runDay08() {
    println("Part 1: " + countSimpleOutputs(inputLines(8)))
    println("Part 2: " + sumOfAllDecoded(inputLines(8)))
}

fun runDay09() {
    println("Part 1: " + HeightMap(inputLines(9)).sumRiskLevels())
    println("Part 2: " + HeightMap(inputLines(9)).basinSizeProduct())
}

fun runDay10() {
    println("Part 1: " + corruptionScore(inputLines(10)))
    println("Part 2: " + middleCompletionScore(inputLines(10)))
}

fun runDay11() {
    println("Part 1: " + simulateOctopuses(linesToCells(inputLines(11)), 100))
    println("Part 2: " + syncOctopuses(linesToCells(inputLines(11))))
}

fun runDay12() {
    println("Part 1: " + findPaths(inputLines(12)).count())
    println("Part 2: " + findPaths(inputLines(12), ::part2Rule).count())
}

fun runDay13() {
    println("Part 1: " + countDots(runFirstFold(inputLines(13))))
    println("Part 2: \n" + printableSheet(runFolds(inputLines(13))))
}

fun runDay14() {
    println("Part 1: " + polymerPart1(inputLines(14)))
    println("Part 2: " + polymerPart2(inputLines(14), 40))
}

fun runDay15() {
    println("Part 1: " + day15part1(inputLines(15)))
    println("Part 2: " + day15part1(makeExpandedMap(inputLines(15))))
}

fun runDay16() {
    println("Part 1: " + sumVersions(parseBits(hexToBits(inputLines(16)[0])).value))
    println("Part 2: " + evaluatePackets(parseBits(hexToBits(inputLines(16)[0])).value))
    //printPackets(parseBits(hexToBits(inputLines(16)[0])).value)
}

fun runDay17() {
    // target area: x=AAA..BBB, y=CCC..DDD
    val targetLine = inputLines(17)[0]
    val cleaned = targetLine.replace(Regex("""^.*x=(-?\d+)\.\.(-?\d+).*y=(-?\d+)\.\.(-?\d+)"""), """$1 $2 $3 $4""")
    val args = cleaned.split(" ").map { it.toInt() }.toTypedArray()
    println("Part 1: " + findMaximumHeight(args[0], args[1], args[2], args[3]))
    println("Part 2: " + totalDistinctVelocities(args[0], args[1], args[2], args[3]))
}

fun runDay18() {
    println("Part 1: " + Sno.part1(inputLines(18)))
    println("Part 2: " + Sno.part2(inputLines(18)))
}

fun runDay19() {
    println("Part 1: " + uniqueBeacons(parseScanners(inputLines(19))))
    println("Part 2: " + maxScannerManhattan(parseScanners(inputLines(19))))
}

fun runDay20() {
    println("Part 1: " + TrenchMap.parse(inputLines(20)).evolve(2).points.size)
    println("Part 2: " + TrenchMap.parse(inputLines(20)).evolve(50).points.size)
}

fun runDay21() {
    val start1 = inputLines(21).first().replace(Regex(".*:\\s"), "").toInt()
    val start2 = inputLines(21).drop(1).first().replace(Regex(".*:\\s"), "").toInt()
    println("Part 1: " + playDirac(DeterministicDie(), start1, start2))
}

fun inputForDay(dayNum: Int): Reader = File(String.format("inputs/2021/%02d.txt", dayNum))
    .let { if (it.exists()) it.bufferedReader() else System.`in`.bufferedReader() }

fun inputLines(dayNum: Int): List<String> = inputForDay(dayNum).readLines()

fun inputAsIntList(dayNum: Int) = inputLines(dayNum).map { it.toInt() }

fun inputLineAsIntList(line: String) = line.split(",").map { it.toInt() }

fun inputAsLongList(dayNum: Int) = inputLines(dayNum).map { it.toLong() }

fun inputAsText(dayNum: Int) = inputForDay(dayNum).readText()