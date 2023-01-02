/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package aoc2022

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
            https://adventofcode.com/2022/
            
            Usage: App day-number
            
            Inputs are assumed to be in 'inputs/2022/NN.txt' where 'NN' is the 2-digit day number.
            If the file isn't present, it reads from stdin instead.
            """.trimIndent())
    }
}

fun runDay01() {
    println("Part 1: " + maximumCalories(inputAsText(1)))
    println("Part 2: " + topThreeCaloriesTotal(inputAsText(1)))
}

fun runDay02() {
    println("Part 1: " + totalScoreForRPSLines(inputLines(2)))
    println("Part 2: " + totalScoreForRPSLinesNewStrategy(inputLines(2)))
}

fun runDay03() {
    println("Part 1: " + rucksackPriorityTotal(inputLines(3)))
    println("Part 2: " + rucksackBadgeTotal(inputLines(3)))
}

fun runDay04() {
    println("Part 1: " + countOfFullyContainedRanges(inputLines(4)))
    println("Part 2: " + countOfOverlappingRanges(inputLines(4)))
}

fun runDay05() {
    println("Part 1: " + applyMovesToCrates(inputLines(5)))
    println("Part 2: " + applyMovesToCratesMultiMove(inputLines(5)))
}

fun runDay06() {
    println("Part 1: " + findStartOfPacketMarker(inputAsText(6)))
    println("Part 2: " + findStartOfMessageMarker(inputAsText(6)))
}

fun runDay07() {
    println("Part 1: " + sumOfDirsUnder100k(inputLines(7)))
    println("Part 1: " + findDeletionCandidate(inputLines(7)))
}

fun runDay08() {
    println("Part 1: " + visibilityCount(inputLines(8)))
    println("Part 2: " + maxScenicScore(inputLines(8)))
}

fun runDay09() {
    println("Part 1: " + ropeTailStepsCount(inputLines(9)))
}

fun runDay10() {
    println("Part 1: " + signalStrengthSums(inputLines(10)))
    drawPixels(inputLines(10))
}

fun runDay11() {
    println("Part 1: " + monkeyBusiness(inputAsText(11)))
    println("Part 2: " + monkeyBusiness(inputAsText(11), 10000, 1))

}

fun runDay12() {
    println("Part 1: " + fewestStepsToEndpoint(inputLines(12)))
    println("Part 2: " + fewestFromAnyA(inputLines(12)))
}

fun runDay13() {
    println("Part 1: " + sumOfOrderedPairIndices(inputLines(13)))
    println("Part 2: " + decoderKey(inputLines(13)))
}

fun runDay14() {
}

fun runDay15() {
}

fun runDay16() {
}

fun runDay17() {
}

fun runDay18() {
}

fun runDay19() {
}

fun runDay20() {
}

fun runDay21() {
}

fun inputForDay(dayNum: Int): Reader = File(String.format("inputs/2022/%02d.txt", dayNum))
    .let { if (it.exists()) it.bufferedReader() else System.`in`.bufferedReader() }

fun inputLines(dayNum: Int): List<String> = inputForDay(dayNum).readLines()

//fun inputAsIntList(dayNum: Int) = inputLines(dayNum).map { it.toInt() }
//
//fun inputLineAsIntList(line: String) = line.split(",").map { it.toInt() }
//
//fun inputAsLongList(dayNum: Int) = inputLines(dayNum).map { it.toLong() }

fun inputAsText(dayNum: Int) : String = inputForDay(dayNum).readText()