fun main() {
    fun calibrationValue(line: String): Int {
        val firstDigit = line.first { it.isDigit() }.digitToInt()
        val lastDigit = line.last { it.isDigit() }.digitToInt()
        return firstDigit * 10 + lastDigit
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { calibrationValue(it) }
    }

    val numberAsStrings = mapOf(
        "one" to '1',
        "two" to '2',
        "three" to '3',
        "four" to '4',
        "five" to '5',
        "six" to '6',
        "seven" to '7',
        "eight" to '8',
        "nine" to '9'
    )

    fun replaceWordWithDigit(line: String): String {
        var result = line.toCharArray()
        line.forEachIndexed { index, _ ->
            numberAsStrings.keys.forEach { key ->
                if (line.startsWith(key, index)) {
                    result[index] = numberAsStrings[key]!!
                }
            }
        }

        return String(result)
    }


    fun part2(input: List<String>): Int {
        return part1(input.map { line -> replaceWordWithDigit(line) })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 142)

    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
