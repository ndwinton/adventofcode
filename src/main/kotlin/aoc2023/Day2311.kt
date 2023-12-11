package aoc2023

import common.transpose
import common.get
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