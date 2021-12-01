package aoc2021

object Day01 {
    fun numberOfIncreases(depths: List<Int>, window: Int = 1): Int =
        depths.windowed(window).map { it.sum() }.zipWithNext().count { it.second > it.first }
}