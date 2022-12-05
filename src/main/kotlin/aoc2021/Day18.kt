package aoc2021

import common.cartesianProduct
import java.lang.IllegalArgumentException

// This is a bit of a mess. But it just goes to show that it's possible
// to write Perl in any language ;-)

sealed class Sno {
    operator fun plus(other: Sno): SnoPair {
        return if (this is SnoPair && other is SnoPair) parse(add(this.toString(), other.toString())).first as SnoPair
        else throw IllegalArgumentException("addition only works on pairs")
    }

    abstract fun magnitude(): Long

    companion object {

        fun parse(input: String): Pair<Sno,String> {
            return when {
                (input[0] == '[') -> {
                    val left = parse(input.drop(1))
                    val right = parse(left.second)
                    Pair(SnoPair(left.first, right.first), right.second)
                }
                (input[0].isDigit()) -> {
                    val value = input.takeWhile { it.isDigit() }.toInt()
                    Pair(SnoNum(value), input.dropWhile { it.isDigit() })
                }
                else -> parse(input.drop(1))
            }
        }

        fun findPairAtDepth(input: String, targetDepth: Int = 5): Int {
            var depth = 0
            for (i in input.indices) {
                when(input[i]) {
                    '[' -> depth++
                    ']' -> depth--
                }
                if (depth == targetDepth) return i
            }
            return -1
        }

        fun explode(snoString: String): String {
            val target = findPairAtDepth(snoString, 5)
            if (target > 0) {
                val prefix = snoString.substring(0, target)
                val regex = Regex("""\[(\d+),(\d+)](.*)""")
                val matches = regex.matchEntire(snoString.substring(target))
                val left = matches!!.groups[1]!!.value.toInt()
                val right = matches.groups[2]!!.value.toInt()
                val suffix = matches.groups[3]!!.value

                val newPrefix = prefix.replace(Regex("""^(.*?)(\d+)(\D*)$""")) {
                    it.safeGet(1) + it.safeAdd(2, left) + it.safeGet(3)
                }
                val newSuffix = suffix.replace(Regex("""^(\D*)(\d+)(.*)""")) {
                    it.safeGet(1) + it.safeAdd(2, right) + it.safeGet(3)
                }
                return explode(newPrefix + "0" + newSuffix)
            } else {
                return snoString
            }
        }

        private fun MatchResult.safeGet(i: Int) = this.groups[i]?.value ?: ""

        private fun MatchResult.safeAdd(i: Int, add: Int) =
            if (this.groups[i] != null) (this.groups[2]!!.value.toInt() + add).toString() else ""

        fun split(snoString: String): String {
            return snoString.replace(Regex("""^(.*?)(\d{2,})(.*)""")) {
                (it.groups[1]?.value ?: "") +
                        (if (it.groups[2] != null) {
                            val x = it.groups[2]!!.value.toInt()
                            "[${x / 2},${x / 2 + x % 2}]"
                        } else "") + (it.groups[3]?.value ?: "")
            }
        }

        fun reduce(snoString: String): String {
            var prev: String
            var next = snoString
            do {
                prev = next
                next = split(explode(prev))
            } while (next != prev)
            return next
        }

        fun add(snoStringA: String, snoStringB: String): String = reduce("[$snoStringA,$snoStringB]")

        fun addMulti(lines: List<String>): String {
            return lines.drop(1).fold(lines[0]) { acc, s -> add(acc, s) }
        }

        fun part1(lines: List<String>): Long = parse(addMulti(lines)).first.magnitude()

        fun part2(lines: List<String>): Long =
            lines.cartesianProduct(lines)
                .distinct()
                .map { parse(add(it.first, it.second)).first.magnitude() }
                .maxOf { it }
    }
}

data class SnoNum(val value: Int) : Sno() {
    override fun magnitude(): Long = value.toLong()

    override fun toString(): String {
        return "$value"
    }
}

data class SnoPair(val left: Sno, val right: Sno) : Sno() {
    override fun magnitude(): Long = 3 * left.magnitude() + 2 * right.magnitude()

    override fun toString(): String {
        return "[$left,$right]"
    }
}

fun String.toSno(): Sno = Sno.parse(this).first

