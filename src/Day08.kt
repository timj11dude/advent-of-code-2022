typealias Forest = List<List<Tree>>

data class Tree(val x: Int, val y: Int, val h: Int, val v: Boolean = false, val s: Int = 0)

fun List<List<Tree>>.rotate() = List(size) { rowI ->
    List(first().size) { colI ->
        this[first().size - 1 - colI][rowI]
    }
}
/*
123  ->  741
456  ->  852
789  ->  963



*/


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

    fun part2(input: Collection<String>): Int {
        return input.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 21) { "Check Failed. Returned Value: ${part1(testInput)}" }
    check(part2(testInput) == 8) { "Check Failed. Returned Value: ${part1(testInput)}" }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
