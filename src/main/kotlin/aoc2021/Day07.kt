package aoc2021

import java.lang.Math.abs
import java.util.Collections.max
import java.util.Collections.min

fun alignmentCost(positions: List<Int>, target: Int): Int = positions.map { abs(it - target) }.sum()

fun alignmentCost2(positions: List<Int>, target: Int): Int = positions.map { pos -> abs(pos - target).let { (it * it + it) / 2 } }.sum()

fun minimumAlignmentCost(positions: List<Int>, cost: (List<Int>, Int) -> Int): Int =
    (min(positions) .. max(positions)).map { cost(positions, it) }.minOf { it }