fun main() {
    val map = readInput("Day21-ex").map { it.toCharArray() }
    val n = map.size
    val m = map[0].size

    data class CC(val i: Int, val j: Int)

    fun part1(): Int {
        val steps = 64
        val i = map.indexOfFirst { it.contains('S') }
        val j = map[i].indexOf('S')
        var reachable = mutableSetOf(CC(i, j))

        repeat(steps) {
            val nextReachable = mutableSetOf<CC>()
            reachable.forEach { c ->
                if (c.i > 0 && map[c.i - 1][c.j] != '#') nextReachable.add(CC(c.i - 1, c.j))
                if (c.j > 0 && map[c.i][c.j - 1] != '#') nextReachable.add(CC(c.i, c.j - 1))
                if (c.i < n - 1 && map[c.i + 1][c.j] != '#') nextReachable.add(CC(c.i + 1, c.j))
                if (c.j < m - 1 && map[c.i][c.j + 1] != '#') nextReachable.add(CC(c.i, c.j + 1))
            }
            reachable = nextReachable
        }

        return reachable.size
    }

    fun part2(): Int {
        val steps = 26501365
        val i = map.indexOfFirst { it.contains('S') }
        val j = map[i].indexOf('S')
        var reachable = mutableSetOf(CC(i, j))

        repeat(steps) {
            val nextReachable = mutableSetOf<CC>()
            reachable.forEach { c ->
                if (map[(c.i - 1 + n) % n][c.j % m] != '#') nextReachable.add(CC(c.i - 1, c.j))
                if (map[c.i % n][(c.j - 1 + m) % m] != '#') nextReachable.add(CC(c.i, c.j - 1))
                if (map[(c.i + 1) % n][c.j % m] != '#') nextReachable.add(CC(c.i + 1, c.j))
                if (map[c.i % n][(c.j + 1) % m] != '#') nextReachable.add(CC(c.i, c.j + 1))
            }
            reachable = nextReachable

            println(reachable.size)
        }

        return reachable.size
    }

    measure { part1() }
    measure { part2() }
}
