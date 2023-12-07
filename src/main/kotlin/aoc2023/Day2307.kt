package aoc2023

data class Hand(val cards: String, val bid: Int) : Comparable<Hand> {
    override fun compareTo(other: Hand): Int {
        return when {
            typeValue > other.typeValue -> 1
            typeValue < other.typeValue -> -1
            else -> comparableCardString.compareTo(other.comparableCardString)
        }
    }

    val typeValue get() =
        when (cards.groupingBy { it }.eachCount().values.sorted()) {
            listOf(5) -> 7          // Five of a kind
            listOf(1, 4) -> 6       // Four of a kind
            listOf(2, 3) -> 5       // Full house
            listOf(1, 1, 3) -> 4    // Three of a kind
            listOf(1, 2, 2) -> 3    // Two pairs
            listOf(1, 1, 1, 2) -> 2 // One pair
            else -> 1               // High card
        }

    val comparableCardString get() =
        cards.replace('T', 'a')
            .replace('J', 'b')
            .replace('Q', 'c')
            .replace('K', 'd')
            .replace('A', 'e')
}

fun parseCamelCardsHands(lines: List<String>): List<Hand> =
    lines.map { it.split(" ") }
        .map { Hand(it[0], it[1].toInt()) }

fun camelCardsScore(lines: List<String>) =
    parseCamelCardsHands(lines).sorted().mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()