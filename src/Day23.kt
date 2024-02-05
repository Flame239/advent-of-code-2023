fun main() {
    val map = readInput("Day23").map(String::toCharArray)

    val n = map.size
    val m = map[0].size
    val start = map[0].indexOfFirst { it == '.' }

    data class CC(val i: Int, val j: Int)

    fun add(cur: CC, visited: Array<BooleanArray>, next: MutableList<CC>) {
        if (map[cur.i][cur.j] != '#' && !visited[cur.i][cur.j]) next.add(CC(cur.i, cur.j))
    }

    fun longestPath(start: CC, visited: Array<BooleanArray>, prevSteps: Int): Int {
        if (start.j == m - 1) return prevSteps

        visited[start.i][start.j] = true

        var cur = start
        var steps = prevSteps

        while (cur.i != n - 1) {
            val next = mutableListOf<CC>()
            when (map[cur.i][cur.j]) {
                '>' -> {
                    add(CC(cur.i, cur.j + 1), visited, next)
                }

                '<' -> {
                    add(CC(cur.i, cur.j - 1), visited, next)
                }

                'v' -> {
                    add(CC(cur.i + 1, cur.j), visited, next)
                }

                '^' -> {
                    add(CC(cur.i - 1, cur.j), visited, next)
                }

                '.' -> {
                    add(CC(cur.i, cur.j + 1), visited, next)
                    add(CC(cur.i, cur.j - 1), visited, next)
                    add(CC(cur.i + 1, cur.j), visited, next)
                    add(CC(cur.i - 1, cur.j), visited, next)
                }
            }

            if (next.isEmpty()) return -1

            if (next.size == 1) {
                cur = next[0]
                visited[cur.i][cur.j] = true
                steps++
            } else {
                return next.maxOf { c -> longestPath(c, visited.copy(), steps + 1) }
            }
        }
        return steps
    }

    fun longestPathWithClimbing(start: CC, visited: Array<BooleanArray>, prevSteps: Int): Int {
        if (start.j == m - 1) return prevSteps

        visited[start.i][start.j] = true

        var cur = start
        var steps = prevSteps

        while (cur.i != n - 1) {
            val next = mutableListOf<CC>()
            add(CC(cur.i, cur.j + 1), visited, next)
            add(CC(cur.i, cur.j - 1), visited, next)
            add(CC(cur.i + 1, cur.j), visited, next)
            add(CC(cur.i - 1, cur.j), visited, next)

            if (next.isEmpty()) return -1

            if (next.size == 1) {
                cur = next[0]
                visited[cur.i][cur.j] = true
                steps++
            } else {
                println("Split at $cur")
                return next.maxOf { c -> longestPathWithClimbing(c, visited.copy(), steps + 1) }
            }
        }
//        println("End : $steps")
        return steps
    }

    fun part1(): Int {
        val visited = Array(n) { BooleanArray(m) }
        visited[0][start] = true
        return longestPath(CC(1, start), visited, 1)
    }

    fun part2(): Int {
        val visited = Array(n) { BooleanArray(m) }
        visited[0][start] = true
        return longestPathWithClimbing(CC(1, start), visited, 1)
    }

    measure { part1() }
    measure { part2() }
}
