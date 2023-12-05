package aoc2023

data class Transform(val range: LongRange, val offset: Long)

data class TransformGroup(val name: String, val transforms: List<Transform>)

data class Almanac(val seeds: List<Long>, val groups: List<TransformGroup>)

fun parseAlmanac(input: String): Almanac {
    val chunks = input.split(Regex("""\n\n"""))
    val seeds = chunks.first().split(Regex("""\s+""")).drop(1).map { it.toLong() }
    return Almanac(seeds, parseTransformGroups(chunks.drop(1)))
}

private fun parseTransformGroups(chunks: List<String>): List<TransformGroup> {
    return chunks.map { chunk ->
        val lines = chunk.split("\n")
        val name = lines.first()
        val transforms = lines.drop(1).map { line ->
            val triple = line.split(Regex("""\s+""")).map { it.toLong() }
            Transform(LongRange(triple[1], triple[1] + triple[2] - 1), triple[0] - triple[1])
        }
        TransformGroup(name, transforms)
    }
}

fun applySeedTransforms(seed: Long, groups: List<TransformGroup>): Long =
    groups.fold(seed) { seed, group ->
        seed + (group.transforms.find { it.range.contains(seed) }?.offset ?: 0L)
    }

fun lowestLocationForSeeds(almanac: Almanac): Long =
    almanac.seeds.minOf { applySeedTransforms(it, almanac.groups) }

// Brute force here is pretty horrible but, hey, I'm running on an M1 Mac ;-)
// In an ideal world, I'd be smarter about doing comparisons with overlapping
// ranges and fragmenting the seed range when necessary. But I haven't got time
// to spend on this now.
fun lowestLocationForSeedsUsingRanges(almanac: Almanac): Long =
    almanac.seeds
        .chunked(2)
        .minOf { (it[0] until it[0] + it[1])
            .minOf { seed -> applySeedTransforms(seed, almanac.groups) }
        }
