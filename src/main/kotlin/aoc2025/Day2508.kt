package aoc2025

import common.combinations

data class Coord3D(val x: Long, val y: Long, val z: Long) {
    fun squareDistance(other: Coord3D) =
        (x - other.x) * (x - other.x) + (y - other.y) * (y - other.y) + (z - other.z) * (z - other.z)
}

data class Circuit(val coords: Set<Coord3D>) {
    fun contains(coord: Coord3D) = coords.contains(coord)
}

fun circuitCalculation(input: List<String>, consider: Int): Long {
    val coords = input.map { it.split(",").map { it.toLong() }.let { list -> Coord3D(list[0], list[1], list[2]) } }
    val pairs = coords
        .combinations(2)
        .map { Pair(it[0], it[1]) }
        .sortedBy { it.first.squareDistance(it.second) }
        .toList()

    fun buildCircuits(pairs: List<Pair<Coord3D,Coord3D>>, count: Int, circuits: Set<Circuit>): Set<Circuit> {

        if (count == 0) return circuits

        val next = pairs.first()
        val foundFirst = circuits.find { it.contains(next.first) }
        val foundSecond = circuits.find { it.contains(next.second) }
        return when {
            foundFirst == null && foundSecond == null -> { // Completely new circuit
                buildCircuits(pairs.drop(1), count - 1, circuits + setOf(Circuit(setOf(next.first, next.second))))
            }
            foundFirst == foundSecond -> { // Both already in same circuit
                buildCircuits(pairs.drop(1), count - 1, circuits)
            }
            foundFirst != null && foundSecond == null -> { // Add to circuit already containing first part of pair
                val newCircuits = (circuits - setOf(foundFirst)) + setOf(Circuit(foundFirst.coords + setOf(next.first, next.second)))
                buildCircuits(pairs.drop(1), count - 1, newCircuits)
            }
            foundFirst == null && foundSecond != null -> { // Add to circuit already containing second part of pair
                val newCircuits = (circuits - setOf(foundSecond)) + setOf(Circuit(foundSecond.coords + setOf(next.first, next.second)))
                buildCircuits(pairs.drop(1), count - 1, newCircuits)
            }
            else -> { // Join two existing circuits
                buildCircuits(pairs.drop(1), count - 1, (circuits - setOf(foundFirst!!, foundSecond!!) + setOf(Circuit(foundFirst.coords + foundSecond.coords))))
            }
        }
    }

    return buildCircuits(pairs, consider, emptySet())
        .sortedByDescending { it.coords.size }
        .take(3)
        .fold(1L) { acc, circuit -> acc * circuit.coords.size }
}