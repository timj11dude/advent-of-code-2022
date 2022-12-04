fun main() {

    fun Collection<String>.groupByBlankLine() = buildMap<Int, List<String>> {
        var i = 0
        this@groupByBlankLine.forEach {
            if (it.isBlank()) i++
            else this.compute(i) { _, e ->
                if (e == null) listOf(it)
                else (e + it)
            }
        }
    }

    fun shared(input: Collection<String>) = input.groupByBlankLine()
        .mapValues { entry -> entry.value.map { it.toInt() } }

    fun part1(input: Collection<String>): Int {
        return shared(input)
            .maxOf { entry -> entry.value.sum() }
    }

    fun part2(input: Collection<String>): Int {
        return shared(input)
            .values.map { it.sum() }
            .sorted()
            .takeLast(3)
            .sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
