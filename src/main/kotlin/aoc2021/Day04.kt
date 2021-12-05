package aoc2021

object Day04 {
    fun parseCallList(input: List<String>): List<Int> =
        input.first().split(",").map { it.toInt() }

    fun parseBoards(data: List<String>): List<Board> =
        data.drop(1)
            .filter { it.trim().isNotBlank() }
            .chunked(5)
            .map { Board(it) }

    fun findWinningScore(data: List<String>): Int {
        val calls = parseCallList(data)
        val boards = parseBoards(data)
        calls.asSequence()
            .takeWhile { boards.none { it.hasWon() } }
            .forEach { value -> boards.map { it.call(value) } }
        return boards.find { it.hasWon() }!!.score()
    }

    fun findLastWinningScore(data: List<String>): Int = lastWinning(parseCallList(data), parseBoards(data))

    private tailrec fun lastWinning(calls: List<Int>, boards: List<Board>): Int =
        if (boards.size == 1 && boards.first().hasWon()) boards.first().score()
        else {
            val remaining = boards.filter { !it.hasWon() }
            remaining.forEach { it.call(calls.first()) }
            lastWinning(calls.drop(1), remaining)
        }
}

class Board(private val lines: List<String>) {
    private val cells = lines.map {
        it.trim().split(Regex("""\s+""")).map {
            Cell(it.toInt())
        }.toTypedArray()
    }.toTypedArray()

    private var lastCall = -1

    operator fun get(row: Int, col: Int) = cells[row][col]

    fun call(number: Int) {
        cells.forEachIndexed { r, row ->
            row.forEachIndexed { c, cell ->
                if (cell.value == number) cells[r][c] = Cell(number, true)
            }
        }
        lastCall = number
    }

    fun hasWon(): Boolean {
        return winningRow() || winningColumn()
    }

    private fun winningRow() = cells.any { row -> row.all { it.called } }

    private fun winningColumn(): Boolean =
        (0..4).map { col -> cells.map { it[col]} }.any { col -> col.all { it.called } }

    fun score(): Int =
        if (hasWon()) cells.map { row -> row.sumOf { if (!it.called) it.value else 0 } }.sum() * lastCall
        else 0
}

data class Cell(val value: Int, val called: Boolean = false)

