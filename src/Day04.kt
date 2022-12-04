fun main() {

    val extractionRegex = "^(\\d+)-(\\d+),(\\d+)-(\\d+)$".toRegex()

    fun IntRange.contains(other: IntRange): Boolean = this.first <= other.first && this.last >= other.last

    fun part1(input: Collection<String>): Int {
        return input.flatMap { row ->
            extractionRegex.matchEntire(row)!!.groupValues
                .drop(1)
                .map(String::toInt).let {
                    listOf(
                        it[0]..it[1],
                        it[2]..it[3]
                    )
                }
        }.windowed(2, 2)
            .count { it.first().contains(it.last()) || it.last().contains(it.first()) }
    }

    fun part2(input: Collection<String>): Int {
        return input.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}
