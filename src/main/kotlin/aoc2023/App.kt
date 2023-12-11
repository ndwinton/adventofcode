/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package aoc2023

import aoc2021.Tuple3
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

        else -> println("""
            https://adventofcode.com/2022/
            
            Usage: App day-number
            
            Inputs are assumed to be in 'inputs/2023/NN.txt' where 'NN' is the 2-digit day number.
            If the file isn't present, it reads from stdin instead.
            """.trimIndent())
    }
}

fun runDay01() {
    println("Part 1: " + inputLines(1).sumOf { calibrationValueOfLine(it) })
    println("Part 2: " + inputLines(1).sumOf { calibrationValueWithWords(it) })
}

fun runDay02() {
    println("Part 1: " + possibleLineIndexSum(inputLines(2), Tuple3(12, 13, 14)))
    println("Part 2: " + inputLines(2).sumOf { powerOfMinPossibleTuple(it) })
}

fun runDay03() {
    println("Part 1: " + sumOfPartNumbers(inputLines(3)))
    //println("Part 2: " + inputLines(2).sumOf { powerOfMinPossibleTuple(it) })
}

fun runDay04() {
    println("Part 1: " + inputLines(4).sumOf { pointsPerCard(it) })
    println("Part 2: " + playAllScratchcards(0, buildScratchcardStartState(inputLines(4))))
}

fun runDay05() {
    println("Part 1:" + lowestLocationForSeeds(parseAlmanac(inputAsText(5))))
    println("Part 2:" + lowestLocationForSeedsUsingRanges(parseAlmanac(inputAsText(5))))
}

fun runDay06() {
    println("Part 1: " + boatRaceAnswerPart1(inputLines(6)))
    println("Part 2: " + boatRaceAnswerPart2(inputLines(6)))
}

fun runDay07() {
    println("Part 1: " + camelCardsScore(inputLines(7)))
    println("Part 2: " + camelCardsScore(inputLines(7), jokers = true))
}

fun runDay08() {
    val (instructions, map) = parseNetworkMap(inputLines(8))
    println("Part 1: " + traverseNetworkMap("AAA", 0, instructions, map))
    println("Part 2: " + traverseNetworkMapMultiple(map.keys.filter { it.endsWith('A') }, 0, instructions, map))
}

fun runDay09() {
    println("Part 1: " + sumOfPredictions(inputLines(9)))
    println("Part 2: " + sumOfPredictionsBackwards(inputLines(9)))
}

fun runDay10() {
    println("Part 1: " + findPipeLength(inputLines(10)))
    println("Part 2: " + countEnclosed(inputLines(10)))
}

fun inputForDay(dayNum: Int): Reader = File(String.format("inputs/2023/%02d.txt", dayNum))
    .let { if (it.exists()) it.bufferedReader() else System.`in`.bufferedReader() }

fun inputLines(dayNum: Int): List<String> = inputForDay(dayNum).readLines()

//fun inputAsIntList(dayNum: Int) = inputLines(dayNum).map { it.toInt() }
//
//fun inputLineAsIntList(line: String) = line.split(",").map { it.toInt() }
//
//fun inputAsLongList(dayNum: Int) = inputLines(dayNum).map { it.toLong() }

fun inputAsText(dayNum: Int) : String = inputForDay(dayNum).readText()