fun main() {
    fun part1(input: Collection<String>): Int {
        return input.count()
    }

    fun part2(input: Collection<String>): Int {
        return input.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
