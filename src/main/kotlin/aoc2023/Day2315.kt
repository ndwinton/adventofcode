package aoc2023

fun String.lavaHash() = this.fold(0) { acc, c -> ((acc + c.code) * 17) % 256 }

fun initSequenceTotal(input: String) = input.trim().split(",").sumOf { it.lavaHash() }