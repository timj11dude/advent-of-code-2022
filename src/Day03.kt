fun main() {

    fun Char.toDigit(): Int = when {
        isUpperCase() -> code - (65 - 27)
        isLowerCase() -> code - (96)
        else -> throw IllegalArgumentException("unknown char [$this]")
    }

    fun shared(input: List<String>) = input.map { it.toList() }
        .map { rucksack ->
            listOf(rucksack.take(rucksack.size / 2), rucksack.drop(rucksack.size / 2))
                .map { compartments -> compartments.map(Char::toDigit) }
        }

    fun part1(input: List<String>): Int {
        return shared(input)
            .sumOf { (it.first() intersect it.last()).single() }
    }

    fun part2(input: List<String>): Int {
        return shared(input)
            .map { it.first() + it.last() } // merge two compartments into 1
            .windowed(3, 3)
            .sumOf { group ->  // a group has 3 lines
                group
                    .map { it.toSet() }
                    .reduce { a, b -> a intersect b }
                    .single()
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
