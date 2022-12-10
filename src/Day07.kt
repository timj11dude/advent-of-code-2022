sealed class Node
sealed class Container : Node()
object Root : Container()
data class Folder(val name: String, val container: Container) : Container()
data class File(val size: Int) : Node()


fun main() {

    fun Map<Container, List<Node>>.update(current: Container, node: Node) = (this + (current to ((this[current] ?: emptyList()) + node)))


    fun readInput(input: Collection<String>): Map<Container, List<Node>> {
        return input.fold(emptyMap<Container, List<Node>>() to emptyList<Container>()) { (reg, stack), entry ->
            when {
                entry == "$ cd /" -> reg to listOf(Root)
                entry.startsWith("$ cd ..") -> reg to (stack.dropLast(1))
                entry.startsWith("$ cd") -> reg to (stack + Folder(entry.drop(5), stack.last()))
                entry.startsWith("$ ls") -> reg to stack
                entry.startsWith("dir ") -> reg.update(stack.last(), Folder(entry.drop(4), stack.last())) to stack
                else -> reg.update(stack.last(), File(entry.takeWhile { it.isDigit() }.toInt())) to stack
            }
        }.first
    }

    class FS(map: Map<Container, List<Node>>) {
        private val computedFolderSize: MutableMap<Container, Int> = mutableMapOf()

        init {
            var previousDiff = 0
            while (computedFolderSize.size != map.size) {
                val newDiff = map.size - computedFolderSize.size
                if (newDiff == previousDiff) {
                    throw IllegalStateException("Failed to reduce diff")
                }
                previousDiff = newDiff
                map.forEach { (k, v) ->
                    if (!computedFolderSize.containsKey(k) && (v.all { it is File } || computedFolderSize.keys.containsAll(v.filterIsInstance<Folder>()))) {
                        computedFolderSize[k] = v.sumOf {
                            when (it) {
                                is File -> it.size
                                is Folder -> computedFolderSize[it]!!
                                is Root -> throw IllegalArgumentException("Root is not contained by anything else")
                            }
                        }
                    }
                }
            }
        }

        fun getFolderSizes() = computedFolderSize.toMap()
    }

    fun part1(input: Collection<String>): Int {
        val parsedInput = readInput(input)
        val fs = FS(parsedInput)
        val folderSizes = fs.getFolderSizes()
        return folderSizes.values.filter { it <= 100000 }.sum()
    }

    fun part2(input: Collection<String>): Int {
        val folderSizes = FS(readInput(input)).getFolderSizes()
        val totalSpace = 70000000
        val spaceRequired = 30000000
        val remainingSpace = totalSpace - folderSizes[Root]!!
        val needAdditional = spaceRequired - remainingSpace
        return folderSizes.values.filter { it >= needAdditional }.minOf { it }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
