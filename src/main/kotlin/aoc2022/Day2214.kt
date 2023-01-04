package aoc2022

data class Coord(val x: Int, val y: Int)

fun parseCoordLine(line: String): List<Coord> =
    line.split(Regex(" -> "))
        .map { it.split(",") }
        .map { Coord(it[0].toInt(), it[1].toInt()) }

class RockGrid(val rocks: Set<Coord>, val bounds: Pair<Coord, Coord>, val sand: Set<Coord> = emptySet()) {

    constructor(lines: List<List<Coord>>) : this(buildRockMap(lines), findBounds(lines),)

    companion object {

        private fun buildRockMap(lines: List<List<Coord>>): Set<Coord> = lines.flatMap { line ->
            line.zipWithNext().flatMap { pair ->
                if (pair.first.x == pair.second.x) {
                    (lowToHigh(pair.first.y, pair.second.y)).map { Coord(pair.first.x, it) }
                }
                else {
                    (lowToHigh(pair.first.x, pair.second.x)).map { Coord(it, pair.first.y) }
                }
            }
        }.toSet()

        private fun lowToHigh(a: Int, b: Int) = if (a < b) a..b else b..a

        private fun findBounds(lines: List<List<Coord>>): Pair<Coord, Coord> =
            lines.map { line ->
                Pair(Coord(line.minOf { it.x }, line.minOf { it.y }),
                    Coord(line.maxOf { it.x }, line.maxOf { it.y })
                )
            }.let { coords ->
                Pair(Coord(coords.minOf { it.first.x }, 0),
                    Coord(coords.maxOf { it.second.x }, coords.maxOf { it.second.y })
                )
            }
    }

    operator fun get(x: Int, y: Int) =
        when {
            (x < bounds.first.x || x > bounds.second.x || y < bounds.first.y || y > bounds.second.y) -> "?"
            rocks.contains(Coord(x, y)) -> "#"
            sand.contains(Coord(x, y)) -> "o"
            else -> " "
        }

    private val abyss = Coord(-1, -1)

    fun addGrain(): RockGrid =
        when (val finish = fallFrom(500, 0)) {
            abyss -> this
            else -> RockGrid(rocks, bounds, sand + finish)
        }

    private tailrec fun fallFrom(x: Int, y: Int): Coord = when {
        this[x, y + 1] == "?" -> abyss
        this[x - 1, y + 1] == "?" -> abyss
        this[x + 1, y + 1] == "?"-> abyss
        this[x, y + 1] == " " -> fallFrom(x, y + 1)
        this[x - 1, y + 1] == " " -> fallFrom(x - 1, y + 1)
        this[x + 1, y + 1] == " " -> fallFrom(x + 1, y + 1)
        else -> Coord(x, y)
    }

    tailrec fun fill(current: RockGrid = this): RockGrid {
        val next = current.addGrain()
        return if (current.sand.size == next.sand.size) current else fill(next)
    }

    override fun toString(): String {
        return (bounds.first.y..bounds.second.y).joinToString("\n") { y ->
            (bounds.first.x..bounds.second.x).joinToString("") { x -> this[x, y] }
        }
    }
}

fun loadRockGrid(input: List<String>, addFloor: Boolean = false): RockGrid {
    val grid = RockGrid(input.map { parseCoordLine(it) })
    return if (addFloor) RockGrid(grid.rocks + (0 .. 1000).map { Coord(it, grid.bounds.second.y + 2) }.toSet(),
        Pair(Coord(0, 0), Coord(1000, grid.bounds.second.y + 2))) else grid
}