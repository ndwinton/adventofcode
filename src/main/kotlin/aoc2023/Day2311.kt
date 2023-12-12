package aoc2023

import common.transpose
import kotlin.math.abs

typealias Universe = List<String>

fun expandUniverse(universe: Universe) =
    universe
        .flatMap { if (it.all { ch -> ch == '.' }) listOf(it.toList(), it.toList()) else listOf(it.toList()) }
        .transpose()
        .flatMap { if (it.all { ch -> ch == '.' }) listOf(it, it) else listOf(it) }
        .transpose()
        .map { it.joinToString("") }

fun findGalaxies(universe: Universe): List<Coord> =
    universe.mapIndexed { row, line ->
        line.mapIndexed { col, ch -> if (ch =='#') Coord(row, col) else Nowhere } }
        .flatten()
        .filter { it != Nowhere }

fun pairings(coords: List<Coord>): List<Pair<Coord,Coord>> =
    if (coords.size == 2) listOf(Pair(coords[0], coords[1]))
    else coords.drop(1).map { Pair(coords.first(), it) } + pairings(coords.drop(1))

fun shortestDistanceSum(universe: Universe) =
    pairings(findGalaxies(expandUniverse(universe))).sumOf { abs(it.first.row - it.second.row) + abs(it.first.col - it.second.col) }

fun blankRowIndices(universe: Universe) = universe.indices.filter { line -> universe[line].all { it == '.' } }

fun shortestSumInHyperInflatedUniverse(universe: Universe, factor: Long = 1000000L): Long {
    val blankRows = blankRowIndices(universe)
    val blankCols = blankRowIndices(universe.map { it.toList() }.transpose().map { it.joinToString("") })
    return pairings(findGalaxies(universe)).sumOf { pair ->
        val rowDist = abs(pair.first.row - pair.second.row) +
                blankRows.count { orderRange(pair.first.row, pair.second.row).contains(it) } * (factor - 1)
        val colDist = abs(pair.first.col - pair.second.col) +
                blankCols.count { orderRange(pair.first.col, pair.second.col).contains(it) } * (factor - 1)
        rowDist + colDist
    }
}

private fun orderRange(a: Int, b: Int) = if (a < b) (a .. b) else (b .. a)