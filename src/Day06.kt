fun main() {

    fun shared(input: Collection<String>, dSize: Int): Int = input.single().asSequence()
        .windowed(dSize, 1) { it.distinct().size }
        .indexOfFirst { it == dSize } + dSize

    fun part1(input: Collection<String>) = shared(input, 4)
    fun part2(input: Collection<String>) = shared(input, 14)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 19)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
