package aoc2024

data class Block(val id: Int, val size: Int)

fun defragChecksum(state: String): Long {
    val blockList = parseBlockList(state)
    val defragged = defrag(blockList)
    return defragged.fold(Pair(0, 0L)) { acc, block ->
        val index = acc.first
        val total = acc.second
        Pair(acc.first + block.size, blockChecksum(index, block) + total)
    }.second
}

fun blockChecksum(index: Int, block: Block): Long =
    (index.toLong() * block.size + ((block.size - 1) * (block.size) / 2)) * block.id


tailrec fun defrag(blockList: List<Block>, result: List<Block> = emptyList()): List<Block> {
    if (blockList.isEmpty()) return result
    
    val first = blockList.first()
    val last = blockList.last()
    val tail = blockList.drop(1)
    return when {
        first.id != -1 -> defrag(tail, result + first)
        else -> {
            when {
                last.id == -1 -> defrag(blockList.dropLast(1), result)
                first.size == last.size -> defrag(tail.dropLast(1), result + last)
                first.size > last.size -> defrag(listOf(Block(-1, first.size - last.size)) + tail.dropLast(1), result + last)
                else -> defrag(tail.dropLast(1) + Block(last.id, last.size - first.size), result + Block(last.id, first.size))
            }
        }
    }
}

fun parseBlockList(state: String): List<Block> =
    state.trim()
        .chunked(2)
        .flatMapIndexed { index: Int, blocks: String ->
            if (blocks.length == 2) listOf(
                Block(index, blocks[0].toString().toInt()),
                Block(-1, blocks[1].toString().toInt())
            )
            else listOf(Block(index, blocks[0].toString().toInt()))
        }

