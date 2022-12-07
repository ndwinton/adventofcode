package aoc2022

data class FileInfo(val name: String, val size: Int)
data class Dir(val name: String, val subDirs: MutableList<Dir> = mutableListOf(), val files: MutableList<FileInfo> = mutableListOf(), var size: Long = 0L) {
    var parent: Dir = this

    override fun toString(): String {
        return "$name $size ${ subDirs.map { it.toString() }}"
    }

    fun findAllDirs(): List<Dir> =
        listOf(this) + subDirs.flatMap { it.findAllDirs() }
}

fun buildDirectoryTree(commands: List<String>): Dir {
    val superRoot = Dir("", mutableListOf()).also { r -> r.subDirs.add(0, Dir("/").also { it.parent = r }) }
    val cleaned = commands.filter { !it.startsWith("\$ ls") }
    buildDirectoryTreeInner(cleaned, superRoot)
    return superRoot.subDirs[0]
}

private fun buildDirectoryTreeInner(commands: List<String>, current: Dir): Unit =
    when {
        (commands.isEmpty()) -> Unit

        (commands.first() == "\$ cd ..") -> buildDirectoryTreeInner(commands.drop(1), current.parent)

        (commands.first().startsWith("\$ cd")) -> {
            val target = commands.first().split(" ")[2]
            val targetTree = current.subDirs.find { it.name == target }!!

            val localCommands = commands.drop(1).takeWhile { !it.startsWith("\$") }
            val subDirs = localCommands.filter { it.startsWith("dir") }
                    .map { Dir(it.split(" ")[1]).also { tree -> tree.parent = targetTree } }
            targetTree.subDirs.addAll(0, subDirs)

            val files = localCommands.filter { !it.startsWith("dir") }
                .map { FileInfo(it.split(" ")[1], it.split(" ")[0].toInt()) }
            targetTree.files.addAll(0, files)

            val remainingCommands = commands.drop(1).dropWhile { !it.startsWith("\$") }
            buildDirectoryTreeInner(remainingCommands, targetTree)

            targetTree.size = subDirs.sumOf { it.size } + files.sumOf { it.size }
        }

        else -> throw IllegalArgumentException("bad command line: " + commands.first())
    }

fun sumOfDirsUnder100k(commands: List<String>) =
    buildDirectoryTree(commands).findAllDirs().filter { it.size <= 100000L }.sumOf { it.size }

fun findDeletionCandidate(commands: List<String>): Long =
    buildDirectoryTree(commands).let { root ->
        val available = 70000000L - root.size
        root.findAllDirs().map { it.size }.sorted().dropWhile { available + it < 30000000L }.first()
    }
