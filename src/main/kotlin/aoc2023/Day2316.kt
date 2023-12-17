package aoc2023

import java.lang.Integer.max

data class IOState(var input: Boolean = false, var output: Boolean = false)

data class Trigger(val left: Boolean, val right: Boolean, val up: Boolean, val down: Boolean)

sealed class BeamCell(val left: IOState, val right: IOState, val up: IOState, val down: IOState) {

    fun energized() = left.input || right.input || up.input || down.input

    fun activateLeft() { left.input = true }

    fun activateRight() { right.input = true }

    fun activateUp() { up.input = true }

    fun activateDown() { down.input = true }

    fun activeCount() =
        (if (left.input) 1 else 0) +
                (if (right.input) 1 else 0) +
                (if (up.input) 1 else 0) +
                (if (down.input) 1 else 0)

    abstract fun trigger(): Trigger

    abstract fun copy(): BeamCell

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeamCell

        if (left != other.left) return false
        if (right != other.right) return false
        if (up != other.up) return false
        if (down != other.down) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + right.hashCode()
        result = 31 * result + up.hashCode()
        result = 31 * result + down.hashCode()
        return result
    }
}

class OpenCell(up: IOState, down: IOState, left: IOState, right: IOState) : BeamCell(up, down, left, right) {
    override fun trigger(): Trigger {
        if (left.input) right.output = true
        if (right.input) left.output = true
        if (up.input) down.output = true
        if (down.input) up.output = true
        return Trigger(left.output, right.output, up.output, down.output)
    }

    override fun copy() =
        OpenCell(left.copy(), right.copy(), up.copy(), down.copy())
}

class VSplitter(up: IOState, down: IOState, left: IOState, right: IOState) : BeamCell(up, down, left, right) {
    override fun trigger(): Trigger {
        if (left.input || right.input) {
            up.output = true
            down.output = true
        }
        if (up.input) down.output = true
        if (down.input) up.output = true
        return Trigger(left.output, right.output, up.output, down.output)
    }

    override fun copy() =
        VSplitter(left.copy(), right.copy(), up.copy(), down.copy())
}

class HSplitter(up: IOState, down: IOState, left: IOState, right: IOState) : BeamCell(up, down, left, right) {
    override fun trigger(): Trigger {
        if (left.input) right.output = true
        if (right.input) left.output = true
        if (up.input || down.input) {
            left.output = true
            right.output = true
        }
        return Trigger(left.output, right.output, up.output, down.output)
    }

    override fun copy() =
        HSplitter(left.copy(), right.copy(), up.copy(), down.copy())
}

class LMirror(up: IOState, down: IOState, left: IOState, right: IOState) : BeamCell(up, down, left, right) {
    override fun trigger(): Trigger {
        if (left.input) down.output = true
        if (right.input) up.output = true
        if (up.input) right.output = true
        if (down.input) left.output = true
        return Trigger(left.output, right.output, up.output, down.output)
    }

    override fun copy() =
        LMirror(left.copy(), right.copy(), up.copy(), down.copy())
}

class RMirror(up: IOState, down: IOState, left: IOState, right: IOState) : BeamCell(up, down, left, right) {
    override fun trigger(): Trigger {
        if (left.input) up.output = true
        if (right.input) down.output = true
        if (up.input) left.output = true
        if (down.input) right.output = true
        return Trigger(left.output, right.output, up.output, down.output)
    }

    override fun copy() =
        RMirror(left.copy(), right.copy(), up.copy(), down.copy())
}

object Wall : BeamCell(IOState(), IOState(), IOState(), IOState()) {
    override fun trigger(): Trigger {
        return Trigger(left = false, right = false, up = false, down = false)
    }

    override fun copy() = Wall
}

fun buildBeamCells(lines: List<String>) =
    lines.map { line ->
        line.map {
            when (it) {
                '.' -> OpenCell(IOState(), IOState(), IOState(), IOState())
                '|' -> VSplitter(IOState(), IOState(), IOState(), IOState())
                '-' -> HSplitter(IOState(), IOState(), IOState(), IOState())
                '/' -> RMirror(IOState(), IOState(), IOState(), IOState())
                '\\' -> LMirror(IOState(), IOState(), IOState(), IOState())
                else -> Wall
            }
        }
    }

operator fun List<List<BeamCell>>.get(row: Int, col: Int): BeamCell =
    when {
        row < 0 || row >= this.size -> Wall
        col < 0 || col >= this[row].size -> Wall
        else -> this[row][col]
    }

fun stepBeamCells(cells: List<List<BeamCell>>): List<List<BeamCell>> {
    val nextSet = cells.map { row -> row.map { it.copy() } }
    cells.indices.forEach { row ->
        cells[row].indices.forEach { col ->
            val triggers = cells[row, col].trigger()
            if (triggers.up) nextSet[row - 1, col].activateDown()
            if (triggers.down) nextSet[row + 1, col].activateUp()
            if (triggers.left) nextSet[row, col - 1].activateRight()
            if (triggers.right) nextSet[row, col + 1].activateLeft()
        }
    }
    return nextSet
}

fun countEnergized(cells: List<List<BeamCell>>) = cells.sumOf { row -> row.count { it.energized() } }

fun activeInputs(cells: List<List<BeamCell>>) = cells.sumOf { row -> row.sumOf { it.activeCount() } }

tailrec fun iterateBeams(cells: List<List<BeamCell>>): List<List<BeamCell>> {
    //printBeamCells(cells)
    val next = stepBeamCells(cells)
    return if (activeInputs(next) == activeInputs(cells)) cells else iterateBeams(next)
}

fun printBeamCells(cells: List<List<BeamCell>>) {
    cells.forEach { row ->
        row.forEach { print(if (it.energized()) "#" else ".") }
        println("")
    }
    println("-----")
}

fun runBeamSimulation(lines: List<String>): Int {
    val cells = buildBeamCells(lines)
    cells[0][0].activateLeft()
    return countEnergized(iterateBeams(cells))
}

fun maximumEnergized(lines: List<String>): Int {
    val maxLeft = maxFromLeft(lines)
    val maxRight = maxFromRight(lines)
    val maxTop = maxFromTop(lines)
    val maxBottom = maxFromBottom(lines)
    return max(max(max(maxLeft, maxRight), maxTop), maxBottom)
}

fun maxFromLeft(lines: List<String>): Int =
    lines.indices.maxOf { index ->
        val cells = buildBeamCells(lines)
        cells[index][0].activateLeft()
        countEnergized(iterateBeams(cells))
    }

fun maxFromRight(lines: List<String>): Int =
    lines.indices.maxOf { index ->
        val cells = buildBeamCells(lines)
        cells[index][lines[index].length - 1].activateRight()
        countEnergized(iterateBeams(cells))
    }

fun maxFromTop(lines: List<String>): Int =
    lines[0].indices.maxOf { index ->
        val cells = buildBeamCells(lines)
        cells[0][index].activateUp()
        countEnergized(iterateBeams(cells))
    }

fun maxFromBottom(lines: List<String>): Int =
    lines[0].indices.maxOf { index ->
        val cells = buildBeamCells(lines)
        cells[lines.size - 1][index].activateDown()
        countEnergized(iterateBeams(cells))
    }