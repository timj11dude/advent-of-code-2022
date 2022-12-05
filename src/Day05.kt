fun main() {

    fun setup(input: Collection<String>): Pair<Collection<String>, List<ArrayDeque<Char>>> {
        val crateRegex = "\\[(\\w)\\]".toRegex()
        return (input.dropWhile { it.isNotBlank() }.drop(1)) to (
                input
                    .takeWhile { it.isNotBlank() }
                    .reversed()
                    .let { newInput ->
                        val size = newInput.first().last { it.isDigit() }.digitToInt()
                        buildList<ArrayDeque<Char>>(size) {
                            repeat(size) {
                                add(ArrayDeque())
                            }
                        }.also { output ->
                            newInput
                                .drop(1)
                                .forEach { line ->
                                    line.windowed(3, 4)
                                        .mapIndexed { i, item ->
                                            crateRegex.matchEntire(item)?.groupValues?.get(1)?.single()?.also { output[i].addFirst(it) }
                                        }
                                }
                        }
                    }

                )
    }

    fun part1(input: Collection<String>): String {
        val (rem, stacks) = setup(input)
        val moveRegex = "move (\\d+) from (\\d) to (\\d)".toRegex()
        rem.forEach { instruction ->
            moveRegex.matchEntire(instruction)!!.groupValues.let { match ->
                repeat(match[1].toInt()) {
                    stacks[match[3].toInt() - 1].addFirst(stacks[match[2].toInt() - 1].removeFirst())
                }
            }
        }
        return stacks.joinToString("") { it.first().toString() }
    }

    fun part2(input: Collection<String>): String {
        val (rem, stacks) = setup(input)
        val moveRegex = "move (\\d+) from (\\d) to (\\d)".toRegex()
        rem.forEach { instruction ->
            moveRegex.matchEntire(instruction)!!.groupValues.let { match ->
                val (moveSize, fromStack, toStack) = match.drop(1).map(String::toInt).mapIndexed { i, it -> if (i > 0) it - 1 else it }
                val tempStack = ArrayDeque<Char>()
                repeat(moveSize) {
                    tempStack.addFirst(stacks[fromStack].removeFirst())
                }
                repeat(moveSize) {
                    stacks[toStack].addFirst(tempStack.removeFirst())
                }
            }
        }
        return stacks.joinToString("") { it.first().toString() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
