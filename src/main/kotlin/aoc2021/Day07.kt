package aoc2021

import java.util.Collections.max
import java.util.Collections.min
import kotlin.math.abs

fun alignmentCost(positions: List<Int>, target: Int): Int = positions.sumOf { abs(it - target) }

fun alignmentCost2(positions: List<Int>, target: Int): Int =
    positions.sumOf { pos -> abs(pos - target).let { (it * it + it) / 2 } }

fun minimumAlignmentCost(positions: List<Int>, cost: (List<Int>, Int) -> Int): Int =
    (min(positions) .. max(positions)).minOf { cost(positions, it) }