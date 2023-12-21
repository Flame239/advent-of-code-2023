import java.lang.Integer.max

fun main() {
    val map: List<CharArray> = readInput("Day16").map { it.toCharArray() }
    val n = map.size
    val m = map[0].size

    fun move(startI: Int, startJ: Int, startDirection: Dir, visited: Array<Array<ArrayList<Dir>>>) {
        var i = startI
        var j = startJ
        var dir = startDirection

        while (i in 0 until n && j in 0 until m && !visited[i][j].contains(dir)) {
            visited[i][j].add(dir)

            if (map[i][j] == '-' && dir.vertical) {
                move(i + DIR_LEFT.stepI, j + DIR_LEFT.stepJ, DIR_LEFT, visited)
                move(i + DIR_RIGHT.stepI, j + DIR_RIGHT.stepJ, DIR_RIGHT, visited)
                return
            }

            if (map[i][j] == '|' && dir.horizontal) {
                move(i + DIR_UP.stepI, j + DIR_UP.stepJ, DIR_UP, visited)
                move(i + DIR_DOWN.stepI, j + DIR_DOWN.stepJ, DIR_DOWN, visited)
                return
            }

            if (map[i][j] == '\\') {
                dir = Dir(dir.stepJ, dir.stepI)
            }

            if (map[i][j] == '/') {
                dir = Dir(-dir.stepJ, -dir.stepI)
            }

            i += dir.stepI
            j += dir.stepJ
        }
    }

    fun countEnergised(i: Int, j: Int, dir: Dir): Int {
        val visited = Array(n) { Array(m) { ArrayList<Dir>() } }
        move(i, j, dir, visited)
        return visited.sumOf { arr -> arr.count { it.isNotEmpty() } }
    }

    fun part1(): Int = countEnergised(0, 0, DIR_RIGHT)

    fun part2(): Int {
        var max = -1
        for (i in 0 until n) max = max(max, countEnergised(i, 0, DIR_RIGHT))
        for (i in 0 until n) max = max(max, countEnergised(i, m - 1, DIR_LEFT))
        for (j in 0 until m) max = max(max, countEnergised(0, j, DIR_DOWN))
        for (j in 0 until m) max = max(max, countEnergised(n - 1, j, DIR_UP))
        return max
    }

    measure { part1() }
    measure { part2() }
}
