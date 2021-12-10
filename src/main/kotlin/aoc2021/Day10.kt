package aoc2021

import java.util.*

fun findCorruption(line: String): String {
    val stack = Stack<Char>()
    val remainder = line.dropWhile { isValid(it, stack) }
    return if (remainder.isNotEmpty() && !isOpening(remainder.first())) remainder.first().toString() else ""
}

private fun isValid(ch: Char, stack: Stack<Char>): Boolean =
    if (isOpening(ch)) {
        stack.push(ch)
        true
    } else checkMatch(ch, stack)

private fun checkMatch(ch: Char, stack: Stack<Char>): Boolean {
    val matchFor = mapOf(')' to '(', ']' to '[', '}' to '{', '>' to '<')
    return when {
        stack.empty() -> false
        stack.pop() == matchFor[ch] -> true
        else -> false
    }
}

private fun isOpening(ch: Char): Boolean = ch in listOf('(', '[', '<', '{')

fun corruptionScore(lines: List<String>): Int =
    lines.map { findCorruption(it) }
        .groupingBy { it }
        .eachCount()
        .let { (it[")"] ?: 0) * 3 + (it["]"] ?: 0) * 57 + (it["}"] ?: 0) * 1197 + (it[">"] ?: 0) * 25137 }

