package aoc2023

data class Hand(val cards: String, val bid: Int, val jokers: Boolean = false) : Comparable<Hand> {

    override fun compareTo(other: Hand): Int {
        return when {
            typeValue > other.typeValue -> 1
            typeValue < other.typeValue -> -1
            else -> comparableCardString.compareTo(other.comparableCardString)
        }
    }

    private val typeValue get() =
        when (cards.groupingBy { it }.eachCount().values.sorted().playJokers()) {
            listOf(5) -> 7          // Five of a kind
            listOf(1, 4) -> 6       // Four of a kind
            listOf(2, 3) -> 5       // Full house
            listOf(1, 1, 3) -> 4    // Three of a kind
            listOf(1, 2, 2) -> 3    // Two pairs
            listOf(1, 1, 1, 2) -> 2 // One pair
            else -> 1               // High card
        }

    private fun List<Int>.playJokers(): List<Int> {
        val jokerCount = cards.count { it == 'J' }
        if (!jokers || jokerCount == 0 || jokerCount == cards.length) return this

        // Could be done immutably, but this is probably clearer!
        val mutable = this.toMutableList()
        mutable.removeAt(this.lastIndexOf(jokerCount))
        mutable[mutable.size - 1] += jokerCount
        return mutable.toList()
    }

    private val comparableCardString get() =
        cards.replace('T', 'a')
            .replace('J', if (jokers) '1' else 'b')
            .replace('Q', 'c')
            .replace('K', 'd')
            .replace('A', 'e')
}

fun parseCamelCardsHands(lines: List<String>, jokers: Boolean = false): List<Hand> =
    lines.map { it.split(" ") }
        .map { Hand(it[0], it[1].toInt(), jokers) }

fun camelCardsScore(lines: List<String>, jokers: Boolean = false) =
    parseCamelCardsHands(lines, jokers).sorted().mapIndexed { index, hand -> (index + 1) * hand.bid }.sum()