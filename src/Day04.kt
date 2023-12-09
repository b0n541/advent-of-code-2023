import kotlin.math.pow

fun main() {

    data class ScratchCard(val winningNumbers: Set<Int>, val myNumbers: Set<Int>)

    fun countWinningNumbers(card: ScratchCard) = card.myNumbers.count { card.winningNumbers.contains(it) }

    fun parseNumbers(numbers: String): Set<Int> {
        return numbers.trim()
            .split("\\s+".toRegex())
            .map { numberString -> numberString.trim().toInt() }
            .toSet()
    }

    fun parseCard(line: String): ScratchCard {
        var numbers = line.split(":", "|")
        return ScratchCard(parseNumbers(numbers[1]), parseNumbers(numbers[2]))
    }

    fun parseCards(input: List<String>): List<ScratchCard> {
        return input.map { line -> parseCard(line) }
    }


    fun part1(input: List<String>): Int {
        return parseCards(input).sumOf { card ->
            2.0.pow(countWinningNumbers(card) - 1).toInt()
        }
    }


    fun part2(input: List<String>): Int {

        var cardOccurrences = mutableMapOf<Int, Int>()
        parseCards(input).forEachIndexed { index, card ->
            for (nextIndex in 0..countWinningNumbers(card)) {
                cardOccurrences.merge(
                    index + nextIndex,
                    if (nextIndex == 0) 1 else cardOccurrences[index]!!
                ) { previousValue, newValue -> previousValue + newValue }
            }
        }

        return cardOccurrences.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 13)

    val testResult2 = part2(testInput)
    println(testResult2)
    check(testResult2 == 30)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
