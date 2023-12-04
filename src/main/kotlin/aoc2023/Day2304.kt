package aoc2023

import kotlin.math.pow
import kotlin.math.roundToInt

fun pointsPerCard(card: String): Int {
    val numWinning = matchesPerCard(card)
    return if (numWinning > 0) 2.0.pow((numWinning - 1).toDouble()).roundToInt() else 0
}

fun matchesPerCard(card: String): Int {
    val winning = card.split(Regex("""\s+""")).drop(2).takeWhile { it != "|" }.map { it.toInt() }.toSet()
    val have = card.split(Regex("""\s+""")).dropWhile { it != "|" }.drop(1).map { it.toInt() }.toSet()
    return have.intersect(winning).size
}

fun buildScratchcardStartState(cards: List<String>) = cards.map{ card -> Pair(1, matchesPerCard(card)) }

tailrec fun playAllScratchcards(index: Int, state: List<Pair<Int, Int>>): Int {
    if (index >= state.size) return state.sumOf { it.first }
    val score = state[index].second
    val newState = state.take(index + 1) +
            (state.drop(index + 1).take(score).map { Pair(it.first + state[index].first, it.second) }) +
            (state.drop(index + 1 + score))
    return playAllScratchcards(index + 1, newState)
}