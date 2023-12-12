enum class HandType {
    HIGH_CARD {
        override fun check(cards: List<Char>): Boolean {
            return cards.distinct().size == 5
        }
    },
    ONE_PAIR {
        override fun check(cards: List<Char>): Boolean {
            return cards.groupingBy { it }.eachCount().filter { it.value == 2 }.size == 1
        }
    },
    TWO_PAIR {
        override fun check(cards: List<Char>): Boolean {
            return cards.groupingBy { it }.eachCount().filter { it.value == 2 }.size == 2
        }
    },
    THREE_OF_A_KIND {
        override fun check(cards: List<Char>): Boolean {
            return cards.groupingBy { it }.eachCount().filter { it.value == 3 }.size == 1
        }
    },
    FULL_HOUSE {
        override fun check(cards: List<Char>): Boolean {
            return cards.groupingBy { it }.eachCount().filter { it.value == 3 }.size == 1 &&
                    cards.groupingBy { it }.eachCount().filter { it.value == 2 }.size == 1
        }
    },
    FOUR_OF_A_KIND {
        override fun check(cards: List<Char>): Boolean {
            return cards.groupingBy { it }.eachCount().filter { it.value == 4 }.size == 1
        }
    },
    FIVE_OF_A_KIND {
        override fun check(cards: List<Char>): Boolean {
            return cards.distinct().size == 1
        }
    };

    abstract fun check(cards: List<Char>): Boolean

    companion object {
        fun of(cards: List<Char>): HandType {
            return HandType.values().reversed().find { it.check(cards) }!!
        }
    }
}

enum class Rank(val characterRepresentation: Char) {
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    JACK('J'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    companion object {
        fun of(card: Char): Rank {
            return Rank.values().find { it.characterRepresentation == card }!!
        }
    }
}

fun main() {

    data class Hand(val cards: List<Char>, val type: HandType, val strength: Int) : Comparable<Hand> {
        override fun compareTo(other: Hand): Int {
            return compareValuesBy(
                this,
                other,
                { it.type },
                { it -> it.cards.map { Rank.of(it) }[0] },
                { it -> it.cards.map { Rank.of(it) }[1] },
                { it -> it.cards.map { Rank.of(it) }[2] },
                { it -> it.cards.map { Rank.of(it) }[3] },
                { it -> it.cards.map { Rank.of(it) }[4] })
        }
    }

    fun Hand(line: String): Hand {
        val handAndStrength = line.split(" ")

        return Hand(handAndStrength[0].toList(), HandType.of(handAndStrength[0].toList()), handAndStrength[1].toInt())
    }

    fun part1(input: List<String>): Int {

        return input.asSequence().map { Hand(it) }
            .sorted()
//            .onEach { println(it) }
            .mapIndexed { index, hand -> (index + 1) * hand.strength }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 6440)

//    val testResult2 = part2(testInput)
//    println(testResult2)
//    check(testResult2 == 30)

    val input = readInput("Day07")
    part1(input).println()
//    part2(input).println()
}
