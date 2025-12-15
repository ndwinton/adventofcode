package aoc2025

import common.combinations

data class Coord3D(val x: Long, val y: Long, val z: Long) {
    fun squareDistance(other: Coord3D) =
        (x - other.x) * (x - other.x) + (y - other.y) * (y - other.y) + (z - other.z) * (z - other.z)
}

data class Circuit(val coords: Set<Coord3D>) {
    fun contains(coord: Coord3D) = coords.contains(coord)
}

private fun addPairToCircuits(pair: Pair<Coord3D,Coord3D>, circuits: Set<Circuit>): Set<Circuit> {
    val foundFirst = circuits.find { it.contains(pair.first) }
    val foundSecond = circuits.find { it.contains(pair.second) }

    return when {
        foundFirst == null && foundSecond == null -> { // Completely new circuit
           circuits + setOf(Circuit(setOf(pair.first, pair.second)))
        }
        foundFirst == foundSecond -> { // Both already in same circuit
            circuits
        }
        foundFirst != null && foundSecond == null -> { // Add to circuit already containing first part of pair
            (circuits - setOf(foundFirst)) + setOf(Circuit(foundFirst.coords + setOf(pair.first, pair.second)))
        }
        foundFirst == null && foundSecond != null -> { // Add to circuit already containing second part of pair
            (circuits - setOf(foundSecond)) + setOf(Circuit(foundSecond.coords + setOf(pair.first, pair.second)))
        }
        else -> { // Join two existing circuits
            (circuits - setOf(foundFirst!!, foundSecond!!) + setOf(Circuit(foundFirst.coords + foundSecond.coords)))
        }
    }
}

fun top3CircuitsResult(input: List<String>, consider: Int): Long {
    val pairs = generateCoordPairs(parseCoords(input))

    tailrec fun buildCircuits(pairs: List<Pair<Coord3D,Coord3D>>, count: Int, circuits: Set<Circuit>): Set<Circuit> =
        if (count == 0 || pairs.isEmpty()) circuits
        else buildCircuits(pairs.drop(1), count - 1, addPairToCircuits(pairs.first(), circuits))

    return buildCircuits(pairs, consider, emptySet())
        .sortedByDescending { it.coords.size }
        .take(3)
        .fold(1L) { acc, circuit -> acc * circuit.coords.size }
}

private fun parseCoords(input: List<String>): List<Coord3D> =
    input.map { it.split(",").map { it.toLong() }.let { list -> Coord3D(list[0], list[1], list[2]) } }

private fun generateCoordPairs(coords: List<Coord3D>): List<Pair<Coord3D, Coord3D>> =
    coords.combinations(2)
        .map { Pair(it[0], it[1]) }
        .sortedBy { it.first.squareDistance(it.second) }
        .toList()

fun lastRequiredConnectionResult(input: List<String>): Long {
    val coords = parseCoords(input)
    val pairs = generateCoordPairs(coords)

    tailrec fun findLastRequired(remaining: List<Pair<Coord3D,Coord3D>>, circuits: Set<Circuit>): Pair<Coord3D,Coord3D> {
        val next = remaining.first()
        val newCircuits = addPairToCircuits(next, circuits)

        return if (newCircuits.size == 1 && newCircuits.first().coords.containsAll(coords)) next
            else findLastRequired(remaining.drop(1), newCircuits)
    }

    return findLastRequired(pairs, emptySet()).let { it.first.x * it.second.x }
}