fun main() {
    data class Network(val moves: String, val map: Map<String, Pair<String, String>>)

    val net by lazy {
        val lines = readInput("Day8")
        val moves = lines[0]
        val map = lines.drop(2).map { l ->
            val split = l.split(" = ")
            Pair(split[0], split[1].substring(1, split[1].length - 1).split(", ").let { Pair(it[0], it[1]) })
        }.associate { it }
        Network(moves, map)
    }

    fun stepsUntil(start: String, isEnd: (String) -> Boolean): Int {
        var step = 0
        var cur = start
        while (!isEnd(cur)) {
            val curMap = net.map[cur]!!
            val dir = net.moves[step % net.moves.length]
            cur = if (dir == 'L') curMap.first else curMap.second
            step++
        }
        return step
    }

    fun part1(): Int {
        return stepsUntil("AAA") { it == "ZZZ" }
    }

    fun part2(): Long {
        val endsWithA = net.map.keys.filter { it.endsWith("A") }
        return endsWithA.map { stepsUntil(it) { c -> c.endsWith("Z") } }.map { it.toLong() }.lcm()
    }

    measure { part1() }
    measure { part2() }
}
