import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() {

    fun Char.toPriority(): Int = when {
        isUpperCase() -> code - (65 - 27)
        isLowerCase() -> code - (96)
        else -> throw IllegalArgumentException("unknown char [$this]")
    }

    fun bagList(input: Collection<String>) = input.map(String::toList)

    fun part1(input: Collection<String>): Int {
        return bagList(input)
            .map { (it.take(it.size / 2).toSet() to it.drop(it.size / 2).toSet()) } // split to groups
            .sumOf { (it.first intersect it.second).single().toPriority() } // intersect two groups, find 1, get Priority number
    }

    fun part2(input: Collection<String>): Int {
        return bagList(input)
            .windowed(3, 3) { group ->  // a group has 3 lines
                group
                    .map { it.toSet() }
                    .reduce(Set<Char>::intersect)
                    .single()
                    .toPriority()
            }.sum()
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("Day03_test")) == 157)
    check(part2(readInput("Day03_test")) == 70)

    val duration = measureTime {
        println(part1(readInput("Day03")))
        println(part2(readInput("Day03")))
    }
    println(duration)
}
