package aoc2021

fun octopusStep(cells: List<List<Int>>): List<List<Int>> = flash(cells.map { row -> row.map { it + 1 }}, 0)

tailrec fun flash(cells: List<List<Int>>, count: Int): List<List<Int>> {

    fun safeGet(row: Int, col: Int): Int =
        if (row >= 0 && row < cells.size && col >= 0 && col < cells[0].size) cells[row][col] else 0

    fun flashValue(row: Int, col: Int): Int =
        if (safeGet(row, col) > 9) 1 else 0

    fun flashyNeighbours(row: Int, col: Int): Int =
        flashValue(row - 1, col - 1) + flashValue(row - 1, col) + flashValue (row - 1, col + 1) +
        flashValue(row, col - 1) + flashValue(row, col + 1) +
        flashValue(row + 1, col - 1) + flashValue(row + 1, col) + flashValue(row + 1, col + 1)

    val result = cells.mapIndexed { row, columns ->
        columns.mapIndexed { col, energy -> if (energy > 9 || energy == 0) 0 else energy + flashyNeighbours(row, col) }
    }

    return if (flashCount(result) == count) result else flash(result, flashCount(result))
}

fun flashCount(cells: List<List<Int>>): Int =
    cells.fold(0) { count, row -> count + row.filter { it == 0 }.size }

fun linesToCells(lines: List<String>): List<List<Int>> = lines.map { it.map { ch -> ch.digitToInt() } }

tailrec fun simulateOctopuses(cells: List<List<Int>>, steps: Int, count: Int = 0): Int =
    if (steps == 0) count
    else {
        val newCells = octopusStep(cells)
        val newFlashers = flashCount(newCells)
        simulateOctopuses(newCells, steps - 1, count + newFlashers)
    }

tailrec fun syncOctopuses(cells: List<List<Int>>, generation: Int = 1): Int {
    val nextCells = octopusStep(cells)
    return if (flashCount(nextCells) == cells.size * cells[0].size) generation
    else syncOctopuses(nextCells, generation + 1)
}
