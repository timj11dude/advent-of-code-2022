fun main() {

    /**
     * A , X = Rock
     * B , Y = Paper
     * C , Z = Scissors
     */
    fun points(theirs: String, yours: String) = when (theirs) {
        "A" -> when (yours) {
            "X" -> 3
            "Y" -> 6
            "Z" -> 0
            else -> throw IllegalArgumentException("Unknown yours value")
        }

        "B" -> when (yours) {
            "X" -> 0
            "Y" -> 3
            "Z" -> 6
            else -> throw IllegalArgumentException("Unknown yours value")
        }

        "C" -> when (yours) {
            "X" -> 6
            "Y" -> 0
            "Z" -> 3
            else -> throw IllegalArgumentException("Unknown yours value")
        }

        else -> throw IllegalArgumentException("Unknown yours value")
    } + when (yours) {
        "X" -> 1
        "Y" -> 2
        "Z" -> 3
        else -> throw IllegalArgumentException("Unknown yours value")
    }

    fun part1(input: List<String>): Int = input
        .map { it.split(" ") }
        .sumOf { points(it.first(), it.last()) }

    fun part2(input: List<String>): Int {
        fun String.getDrawPoints() = when (this) {
            "A" -> 1 + 3
            "B" -> 2 + 3
            "C" -> 3 + 3
            else -> throw IllegalArgumentException("Unknown choice")
        }

        fun String.getWinPoints() = when (this) {
            "A" -> 2 + 6
            "B" -> 3 + 6
            "C" -> 1 + 6
            else -> throw IllegalArgumentException("Unknown choice")
        }

        fun String.getLosePoints() = when (this) {
            "A" -> 3
            "B" -> 1
            "C" -> 2
            else -> throw IllegalArgumentException("Unknown choice")
        }
        return input
            .map { it.split(" ") }
            .sumOf {
                when (it.last()) {
                    "X" -> it.first().getLosePoints()
                    "Y" -> it.first().getDrawPoints()
                    "Z" -> it.first().getWinPoints()
                    else -> throw IllegalArgumentException("Unknown outcome")
                }
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
