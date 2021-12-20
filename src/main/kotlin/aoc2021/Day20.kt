package aoc2021

class TrenchMap(val instructions: String, val topLeft: Pair<Int, Int>, val bottomRight: Pair<Int, Int>, val points: Set<Point>, val infiniteState: Boolean = false) {
    data class Point(val row: Int, val col: Int)

    operator fun get(row: Int, col: Int): Boolean =
        if (row >= topLeft.first &&
            row <= bottomRight.first &&
            col >= topLeft.second &&
            col <= bottomRight.second ) points.contains(Point(row, col)) else infiniteState


    fun instructionIndex(row: Int, col: Int): Int =
        listOf(
            this[row - 1, col - 1], this[row - 1, col], this[row - 1, col + 1],
            this[row, col - 1], this[row, col], this[row, col + 1],
            this[row + 1, col - 1], this[row + 1, col], this[row + 1, col + 1],
        ).joinToString("") { if (it) "1" else "0" }.toInt(2)

    fun evolve(generations: Int = 1): TrenchMap {
        val newPoints = (topLeft.first - 1 .. bottomRight.first + 1).flatMap { row ->
            (topLeft.second - 1 .. bottomRight.second + 1).map { col ->
                if (stateFor(row, col)) Point(row, col) else Point(Int.MAX_VALUE, Int.MAX_VALUE)
            }.filter { it != Point(Int.MAX_VALUE, Int.MAX_VALUE) }
        }.toSet()

        val next = TrenchMap(
            instructions,
            Pair(topLeft.first - 1, topLeft.second - 1),
            Pair(bottomRight.first + 1, bottomRight.second + 1),
            newPoints, stateFor(Int.MAX_VALUE - 2, Int.MAX_VALUE - 2)
        )

        return if (generations == 1) return next else next.evolve(generations - 1)
    }

    private fun stateFor(row: Int, col: Int): Boolean = instructions[instructionIndex(row, col)] == '#'

    override fun toString(): String {
        return "$instructions\n\n" + (topLeft.first..bottomRight.first).joinToString("\n") { row ->
            (topLeft.second..bottomRight.second).joinToString("") { col -> if (this[row, col]) "#" else "." }
        }
    }

    companion object {
        fun parse(lines: List<String>): TrenchMap {
            val points = lines.drop(2)
                .flatMapIndexed { row, line ->
                    line.mapIndexed { col, ch ->
                        if (ch == '#') Point(row, col) else Point(-1, -1)
                    }.filter { it != Point(-1, -1) }
                }.toSet()
            return TrenchMap(lines[0], Pair(0, 0), Pair(lines.size - 3, lines[2].length - 1), points)
        }
    }
}