typealias Forest = List<List<Tree>>

data class Tree(val x: Int, val y: Int, val h: Int, val v: Boolean = false, val s: Int = 0)

fun List<List<Tree>>.rotate() = List(size) { rowI ->
    List(first().size) { colI ->
        this[first().size - 1 - colI][rowI]
    }
}
fun main() {

    fun parseInput(input: Collection<String>): Forest {
        return input.mapIndexed { rowI, row ->
            row.mapIndexed { colI, h ->
                Tree(colI, rowI, h.digitToInt())
            }
        }
    }

    fun checkLine(input: Collection<Tree>): List<Tree> {
        return input.fold(-1 to emptyList<Tree>()) { (h, out), tree ->
            when (tree.h > h) {
                true -> tree.h to out + tree.copy(v = true)
                false -> h to out + tree
            }
        }.second
    }

    fun part1(input: Collection<String>): Int {
        val forest = parseInput(input)
            .map(::checkLine).rotate()
            .map(::checkLine).rotate()
            .map(::checkLine).rotate()
            .map(::checkLine).rotate()
        return forest.flatten().count { it.v }
    }

    fun Forest.checkViewFrom(tree: Tree): Int {
        val row = this[tree.y]
        val col = this.rotate()[tree.x].reversed()
        fun List<Tree>.countLine(t: Int): Int {
            fun List<Tree>.takeVisibleTrees() = take(indexOfFirst { it.h >= tree.h }.takeUnless { it == -1 }?.let { it + 1 } ?: size)
            return drop(t + 1).takeVisibleTrees().count() * take(t).reversed().takeVisibleTrees().count()
        }
        return (col.countLine(tree.y) * row.countLine(tree.x))
    }

    fun part2(input: Collection<String>): Int {
        val forest = parseInput(input)
        return forest.flatten().maxOf { forest.checkViewFrom(it) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21) { "Check Failed. Returned Value: ${part1(testInput)}" }
    check(part2(testInput) == 8) { "Check Failed. Returned Value: ${part2(testInput)}" }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
    // wrong guess: 545729
}
