fun main() {
    fun part1(input: List<String>): Int {
        fun List<String>.groupByBlankLine() = buildMap<Int, List<String>> {
            var i = 0
            this@groupByBlankLine.forEach {
                if (it.isBlank()) i++
                else this.compute(i) { _, e ->
                    if (e == null) listOf(it)
                    else (e + it)
                }
            }
        }
        return input.groupByBlankLine()
            .mapValues { entry -> entry.value.map { it.toInt() } }
            .maxOf { entry -> entry.value.sum() }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
