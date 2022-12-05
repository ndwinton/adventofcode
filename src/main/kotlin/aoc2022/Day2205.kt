package aoc2022

import common.transpose

fun parseCrateStackStateLines(lines: List<String>): List<String> {
    val indices = lines.dropWhile { !it.contains("1") }.first()
    val maxIndex = indices.split(" ").filter { it != "" }.last().toInt()
    return lines.takeWhile { it.contains("[") }
        .map { it.padEnd(4 * maxIndex) }
        .map { it.chunked(4).map { s -> s[1] } }
        .transpose()
        .map { it.joinToString("").trim() }
}

data class CrateMove(val count: Int, val from: Int, val to: Int)

fun parseCrateMoveLines(lines: List<String>): List<CrateMove> =
    lines.dropWhile { !it.startsWith("move") }
        .map { line ->
            line.split(" ")
                .filter { it.matches(Regex("""\d+""")) }
                .map { it.toInt() }
        }
        .map { CrateMove(it[0], it[1], it[2]) }

fun applyMovesToCrates(lines: List<String>, substackMover: (String) -> String = { it.reversed() }): String {
    val stacks = parseCrateStackStateLines(lines)
    val moves = parseCrateMoveLines(lines)
    val endState = moves.fold(stacks.toMutableList()) { currentStacks, move ->
        val fromStack = currentStacks[move.from - 1]
        val toStack = currentStacks[move.to - 1]
        currentStacks[move.to - 1] = substackMover(fromStack.take(move.count)) + toStack
        currentStacks[move.from - 1] = fromStack.drop(move.count)
        currentStacks
    }
    return endState.map { it.first() }.joinToString("")
}

fun applyMovesToCratesMultiMove(lines: List<String>): String = applyMovesToCrates(lines) { it }
