fun main() {
    data class CC(val i: Int, val j: Int) {
        override fun toString() = "[$i $j]"
    }

    val map = readInput("Day23").map(String::toCharArray)
    val n = map.size
    val m = map[0].size
    val startJ = map[0].indexOfFirst { it == '.' }
    val start = CC(1, startJ)

    fun longestPath(start: CC, visited: Array<BooleanArray>, prevSteps: Int): Int {
        visited[start.i][start.j] = true

        var cur = start
        var steps = prevSteps

        while (cur.i != n - 1) {
            val adj = mutableListOf<CC>()
            when (map[cur.i][cur.j]) {
                '>' -> adj.add(CC(cur.i, cur.j + 1))
                '<' -> adj.add(CC(cur.i, cur.j - 1))
                'v' -> adj.add(CC(cur.i + 1, cur.j))
                '^' -> adj.add(CC(cur.i - 1, cur.j))
                '.' -> adj.addAll(listOf(CC(cur.i, cur.j + 1), CC(cur.i, cur.j - 1), CC(cur.i + 1, cur.j), CC(cur.i - 1, cur.j)))
            }
            val next = adj.filter { map[it.i][it.j] != '#' && !visited[it.i][it.j] }

            if (next.isEmpty()) {
                return -1
            } else if (next.size == 1) {
                cur = next[0]
                visited[cur.i][cur.j] = true
                steps++
            } else {
                return next.maxOf { c -> longestPath(c, visited.copy(), steps + 1) }
            }
        }
        return steps
    }


    fun buildGraph(graph: CoolGraph, visited: Array<BooleanArray>, start: CC, prev: CC) {
        visited[start.i][start.j] = true
        var cur = start
        var steps = 0

        while (cur.i != n - 1) {
            val adj = mutableListOf(CC(cur.i, cur.j + 1), CC(cur.i, cur.j - 1), CC(cur.i + 1, cur.j), CC(cur.i - 1, cur.j)).filter { map[it.i][it.j] != '#' }
            val next = adj.filter { !visited[it.i][it.j] }

            if (next.isEmpty()) {
                val node = adj.first { it != prev && graph.containsNode(it) }
                graph.addEdge(prev, node, steps)
                return
            } else if (next.size == 1) {
                cur = next[0]
                visited[cur.i][cur.j] = true
                steps++
            } else {
                graph.addNode(cur)
                graph.addEdge(cur, prev, steps)
                next.forEach { c -> if (!visited[c.i][c.j]) buildGraph(graph, visited, c, cur) }
                return
            }
        }
        graph.addNode(cur)
        graph.addEdge(cur, prev, steps)
    }

    fun part1(): Int {
        val visited = Array(n) { BooleanArray(m) }
        visited[0][startJ] = true
        return longestPath(start, visited, 1)
    }

    fun part2(): Int {
        val graph = CoolGraph()
        graph.addNode(start)
        val visited = Array(n) { BooleanArray(m) }
        visited[0][startJ] = true

        buildGraph(graph, visited, start, start)
        graph.display()
        return 0
    }

    measure { part1() }
    measure { part2() }
}
