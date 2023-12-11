package common

import kotlin.math.absoluteValue

// Based on https://gist.github.com/clementgarbay/49288c006252955c2a3c6139a61ca92a
/**
 * Safe transpose a list of unequal-length lists.
 *
 * Example:
 * (List(List(1, 2, 3), List(4, 5, 6), List(7, 8))).transpose()
 * -> List(List(1, 4, 7), List(2, 5, 8), List(3, 6))
 */
fun <E> List<List<E>>.transpose(): List<List<E>> {
    // Helpers
    fun <E> List<E>.head(): E = this.first()
    fun <E> List<E>.tail(): List<E> = this.takeLast(this.size - 1)
    fun <E> E.append(xs: List<E>): List<E> = listOf(this).plus(xs)

    this.filter { it.isNotEmpty() }.let { ys ->
        return when (ys.isNotEmpty()) {
            true -> ys.map { it.head() }.append((ys.map { it.tail() }).transpose())
            else -> emptyList()
        }
    }
}

// https://stackoverflow.com/a/67595807/1773713

fun <S, T> List<S>.cartesianProduct(other: List<T>) = this.flatMap { thisIt ->
    other.map { otherIt ->
        thisIt to otherIt
    }
}

// GCD - Euclid's method
fun Long.gcd(other: Long): Long = if (other == 0L) this else other.gcd(this % other)

fun Int.gcd(other: Int): Int = if (other == 0) this else other.gcd(this % other)

fun Long.lcm(other: Long) = (this * other).absoluteValue / this.gcd(other)

fun Int.lcm(other: Int)= (this * other).absoluteValue / this.gcd(other)

operator fun List<String>.get(row: Int, col: Int): Char =
    when {
        row < 0 || row >= this.size -> '.'
        col < 0 || col >= this[row].length -> '.'
        else -> this[row][col]
    }