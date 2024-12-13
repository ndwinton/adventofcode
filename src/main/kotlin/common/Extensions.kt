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

@JvmName("transposeStringList")
fun List<String>.transpose(): List<String> =
    this.map { it.toList() }.transpose().map { it.joinToString("") }

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

// Safe get at row, col
operator fun List<String>.get(row: Int, col: Int): Char =
    when {
        row < 0 || row >= this.size -> '.'
        col < 0 || col >= this[row].length -> '.'
        else -> this[row][col]
    }

// https://www.reddit.com/r/Kotlin/comments/isg16h/what_is_the_fastest_way_combination_in_kotlin/
// Ported from Python itertools -- could certainly be improved

fun <T> Iterable<T>.combinations(length: Int): Sequence<List<T>> =
    sequence {
        val pool = this@combinations as? List<T> ?: toList()
        val n = pool.size
        if (length > n) return@sequence
        val indices = IntArray(length) { it }
        while (true) {
            yield(indices.map { pool[it] })
            var i = length
            do {
                i--
                if (i == -1) return@sequence
            } while (indices[i] == i + n - length)
            indices[i]++
            for (j in i+1 until length) indices[j] = indices[j - 1] + 1
        }
    }

fun <T> Iterable<T>.permutations(length: Int? = null): Sequence<List<T>> =
    sequence {
        val pool = this@permutations as? List<T> ?: toList()
        val n = pool.size
        val r = length ?: n
        if (r > n) return@sequence
        val indices = IntArray(n) { it }
        val cycles = IntArray(r) { n - it }
        yield(List(r) { pool[indices[it]] })
        if (n == 0) return@sequence
        cyc@ while(true) {
            for(i in r-1 downTo 0) {
                cycles[i]--
                if(cycles[i] == 0) {
                    val temp = indices[i]
                    for(j in i until n-1) indices[j] = indices[j+1]
                    indices[n-1] = temp
                    cycles[i] = n - i
                } else {
                    val j = n - cycles[i]
                    indices[i] = indices[j].also { indices[j] = indices[i] }
                    yield(List(r) { pool[indices[it]] })
                    continue@cyc
                }
            }
            return@sequence
        }
    }