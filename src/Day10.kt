import Direction.*

private enum class Direction {
    TOP, BOTTOM, LEFT, RIGHT
}

val toBoxDrawing = mapOf(
        '-' to '─',
        '|' to '│',
        'L' to '└',
        'J' to '┘',
        '7' to '┐',
        'F' to '┌',
)

fun main() {
    val map by lazy {
        readInput("Day10-ex").map { it.toCharArray() }.toTypedArray()
    }

    val n = map.size
    val m = map[0].size

    val botConn = listOf('|', 'F', '7')
    val topConn = listOf('|', 'L', 'J')
    val rightConn = listOf('-', 'L', 'F')
    val leftConn = listOf('-', 'J', '7')

    data class C(val i: Int, val j: Int)

    fun getLoop(): List<C> {
        var i = 0
        var j = 0
        outer@ for (ci in 0..<n) for (cj in 0..<m) if (map[ci][cj] == 'S') {
            i = ci; j = cj; break@outer
        }

        val loop = mutableListOf(C(i, j))

        var prev = TOP
        if ((i + 1) < n && topConn.contains(map[i + 1][j])) {
            i++
            prev = TOP
        } else if ((i - 1) >= 0 && botConn.contains(map[i - 1][j])) {
            i--
            prev = BOTTOM
        } else if ((j - 1) >= 0 && rightConn.contains(map[i][j - 1])) {
            j--
            prev = RIGHT
        } else if ((j + 1) < m && leftConn.contains(map[i][j + 1])) {
            j++
            prev = LEFT
        }

        // @formatter:off
        while (map[i][j] != 'S') {
            loop.add(C(i, j))
            when (map[i][j]) {
                '-' -> if (prev == LEFT) j++ else j--
                '|' -> if (prev == TOP) i++ else i--
                'L' -> if (prev == TOP) { j++; prev = LEFT } else { i--; prev = BOTTOM }
                'J' -> if (prev == TOP) { j--; prev = RIGHT } else { i--; prev = BOTTOM }
                '7' -> if (prev == LEFT) { i++; prev = TOP }  else { j--; prev = RIGHT }
                'F' -> if (prev == RIGHT) { i++; prev = TOP } else { j++; prev = LEFT }
            }
        }
        // @formatter:on
        return loop
    }

    fun part1(): Int {
        return getLoop().size / 2
    }

    fun part2(): Int {
        val loop = getLoop()
        val s = loop[0]

//      side to consider:
//        -  BOTTOM
//        |  RIGHT
//        L  RIGHT-TOP
//        J  RIGHT-BOTTOM
//        7 RIGHT-TOP
//        F RIGHT-BOTTOM
//        1 - inside, -1 - outside
        val inside = Array(n) { IntArray(m) }
        inside[s.i][s.j] = 1 // -1
        loop.windowed(2).forEach { (prev, c) ->
            var mult = 1
            // @formatter:off
            when (map[c.i][c.j]) {
                '-' -> if (map[prev.i][prev.j] == 'L' || map[prev.i][prev.j] == '7') mult = -1
                '|' -> mult = 1
                'L' -> if (map[prev.i][prev.j] == '-' || map[prev.i][prev.j] == 'J') mult = -1
                'J' -> if (map[prev.i][prev.j] == 'L') mult = -1
                '7' -> if (map[prev.i][prev.j] == '-' || map[prev.i][prev.j] == 'F') mult = -1
                'F' -> if (map[prev.i][prev.j] == '7') mult = -1
            }
            // @formatter:on
            inside[c.i][c.j] = mult * inside[prev.i][prev.j]
        }

        // TODO: we know which side of the border is inside and outside, we can determine same for every other cell

        return map.size
    }

    measure { part1() }
    measure { part2() }
}
