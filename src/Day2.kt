fun main() {
    data class CubeGameTurn(val blue: Int, val red: Int, val green: Int)
    data class CubeGame(val id: Int, val turns: List<CubeGameTurn>)

    val games by lazy {
        readInput("Day2").map { line ->
            val (gameId, turnsS) = line.split(": ")
            val turns = turnsS.split("; ").map { turnS ->
                val turnMap = turnS.split(", ").associate { turnColor -> turnColor.split(" ").let { Pair(it[1], it[0].toInt()) } }
                CubeGameTurn(turnMap.getOrDefault("blue", 0), turnMap.getOrDefault("red", 0), turnMap.getOrDefault("green", 0))
            }
            CubeGame(gameId.split(" ")[1].toInt(), turns)
        }
    }

    fun isGameValid(game: CubeGame): Boolean = game.turns.all { turn -> turn.red <= 12 && turn.green <= 13 && turn.blue <= 14 }

    fun getGamePower(game: CubeGame) = game.turns.maxOf { it.blue } * game.turns.maxOf { it.red } * game.turns.maxOf { it.green }

    fun part1(): Int = games.sumOf { game -> if (isGameValid(game)) game.id else 0 }

    fun part2(): Int = games.sumOf(::getGamePower)

    measure { part1() }
    measure { part2() }
}
