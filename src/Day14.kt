fun main() {
    val rocks = readInput("Day14").map { it.toList() }
    val n = rocks.size
    val m = rocks[0].size

    fun part1(): Long {
        var totalLoad = 0L
        for (j in rocks[0].indices) {
            val loads = mutableListOf<Long>()
            var curRow = 0L
            for (i in rocks.indices) {
                if (rocks[i][j] == 'O') {
                    loads.add(curRow)
                    curRow++
                } else if (rocks[i][j] == '#') {
                    curRow = i + 1L
                }
            }
            totalLoad += loads.sumOf { n - it }
        }
        return totalLoad
    }

    fun getNorthLoad(r: List<List<Char>>): Int {
        return r.withIndex().sumOf { (i, row) -> row.count { it == 'O' } * (n - i) }
    }

    fun cycle(oldR: List<List<Char>>): List<List<Char>> {
        val r = oldR.map { it.toMutableList() }
        // North
        for (j in r[0].indices) {
            var nextInd = 0
            for (i in r.indices) {
                if (r[i][j] == 'O') {
                    r[i][j] = '.'
                    r[nextInd][j] = 'O'
                    nextInd++
                } else if (r[i][j] == '#') {
                    nextInd = i + 1
                }
            }
        }
        // West
        for (i in r.indices) {
            var nextInd = 0
            for (j in r[0].indices) {
                if (r[i][j] == 'O') {
                    r[i][j] = '.'
                    r[i][nextInd] = 'O'
                    nextInd++
                } else if (r[i][j] == '#') {
                    nextInd = j + 1
                }
            }
        }


        // South
        for (j in r[0].indices) {
            var nextInd = n - 1
            for (i in n - 1 downTo 0) {
                if (r[i][j] == 'O') {
                    r[i][j] = '.'
                    r[nextInd][j] = 'O'
                    nextInd--
                } else if (r[i][j] == '#') {
                    nextInd = i - 1
                }
            }
        }

        // East
        for (i in r.indices) {
            var nextInd = m - 1
            for (j in m - 1 downTo 0) {
                if (r[i][j] == 'O') {
                    r[i][j] = '.'
                    r[i][nextInd] = 'O'
                    nextInd--
                } else if (r[i][j] == '#') {
                    nextInd = j - 1
                }
            }
        }
        return r
    }

    fun part2(): Int {
        val rocksToStep = hashMapOf<List<List<Char>>, Int>()
        var cur = rocks
        var step = 0
        while (cur !in rocksToStep) {
            rocksToStep[cur] = step
            cur = cycle(cur)
            step++
        }
        val loopStart = rocksToStep[cur]!!
        val loopLen = step - loopStart
        val offset = (1000000000 - loopStart) % loopLen + loopStart

        return getNorthLoad(rocksToStep.entries.first { it.value == offset }.key)
    }

    measure { part1() }
    measure { part2() }
}
