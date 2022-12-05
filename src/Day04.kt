fun main() {

    val extractionRegex = "^(\\d+)-(\\d+),(\\d+)-(\\d+)$".toRegex()

    fun IntRange.fullyContains(other: IntRange): Boolean = this.first <= other.first && this.last >= other.last
    fun IntRange.partiallycontains(other: IntRange): Boolean = this.first in other || this.last in other || other.first in this || other.last in this

    fun shared(input: Collection<String>) = input.flatMap { row ->
        extractionRegex.matchEntire(row)!!.groupValues
            .drop(1)
            .map(String::toInt).let {
                listOf(
                    it[0]..it[1],
                    it[2]..it[3]
                )
            }
    }.windowed(2, 2)

    fun part1(input: Collection<String>): Int {
        return shared(input)
            .count { it.first().fullyContains(it.last()) || it.last().fullyContains(it.first()) }
    }

    fun part2(input: Collection<String>): Int {
        return shared(input)
            .count { it.first().partiallycontains(it.last()) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
