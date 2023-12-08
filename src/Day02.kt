fun main() {

    data class Move(val red: Int, val green: Int, val blue: Int) {
        fun isPossibleWith(red: Int, green: Int, blue: Int): Boolean {
            return this.red <= red && this.green <= green && this.blue <= blue
        }
    }

    data class Game(val id: Int, val moves: List<Move>) {
        fun isPossibleWith(red: Int, green: Int, blue: Int): Boolean {
            return moves.none { move -> !move.isPossibleWith(red, green, blue) }
        }

        fun powerOfSetOfCubes(): Int {
            var maxRed = 0
            var maxGreen = 0
            var maxBlue = 0

            moves.forEach { move ->
                if (move.red > maxRed) maxRed = move.red
                if (move.green > maxGreen) maxGreen = move.green
                if (move.blue > maxBlue) maxBlue = move.blue
            }

            val result = maxRed * maxGreen * maxBlue
//            println("$maxRed $maxGreen $maxBlue: $result")

            return result
        }
    }

    fun parseMove(move: String): Move {
        val colors = move.trim().split(",")
//        println(colors)

        var red = 0
        var green = 0
        var blue = 0

        colors.map {
            val numberAndColor = it.trim().split(" ")
            val number = numberAndColor[0].toInt()
            when (numberAndColor[1]) {
                "red" -> red = number
                "green" -> green = number
                "blue" -> blue = number
            }
        }

        return Move(red, green, blue)
    }

    fun parseMoves(moves: String): List<Move> {
        val moveStrings = moves.split(";")
//        println(moveStrings)
        return moveStrings.map { move -> parseMove(move) }
    }

    fun parseGame(line: String): Game {
        val gameAndMoves = line.split(":")
//        println(gameAndMoves)
        val game = Game(gameAndMoves[0].removePrefix("Game ").toInt(), parseMoves(gameAndMoves[1]))
//        println(game)

        return game
    }

    fun parseGames(input: List<String>): List<Game> {
        return input.map { line -> parseGame(line) }
    }

    fun part1(input: List<String>): Int {
        val possibleGames = parseGames(input).filter { game -> game.isPossibleWith(12, 13, 14) }
//        println(possibleGames)

        return possibleGames.sumOf { game -> game.id }
    }

    fun part2(input: List<String>): Int {
        return parseGames(input).sumOf { game -> game.powerOfSetOfCubes() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    val testResult = part1(testInput)
    println(testResult)
    check(testResult == 8)

    val testResult2 = part2(testInput)
    println(testResult2)
    check(testResult2 == 2286)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
