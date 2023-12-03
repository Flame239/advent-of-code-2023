fun main() {
    val map by lazy {
        readInput("Day3")
    }
    val n = map.size
    val m = map[0].length

    fun forEachNum(action: (row: Int, start: Int, end: Int, num: Int) -> Unit) {
        for (i in 0..<n) {
            var j = 0
            while (j < m) {
                if (!map[i][j].isDigit()) {
                    j++
                    continue
                }
                val start = j
                while (j < m && map[i][j].isDigit()) {
                    j++
                }
                val num = map[i].substring(start, j).toInt()
                action(i, start, j - 1, num)
            }
        }
    }

    fun part1(): Int {
        var sum = 0
        forEachNum { row, start, end, num ->
            for (i in row - 1..row + 1) for (j in start - 1..end + 1) {
                if (i in 0..<n && j in 0..<m) {
                    val c = map[i][j]
                    if (c != '.' && !c.isDigit()) {
                        sum += num
                        return@forEachNum
                    }
                }
            }
        }

        return sum
    }

    fun part2(): Long {
        val gearNumCount = Array(n) { IntArray(m) }
        val gearRatio = Array(n) { LongArray(m) { 1 } }
        forEachNum { row, start, end, num ->
            for (i in row - 1..row + 1) for (j in start - 1..end + 1) {
                if (i in 0..<n && j in 0..<m) {
                    if (map[i][j] == '*') {
                        gearNumCount[i][j]++
                        gearRatio[i][j] *= num.toLong()
                    }
                }
            }
        }
        return gearNumCount.sumOfIndexed2 { i, j, count -> if (count == 2) gearRatio[i][j] else 0 }
    }

    measure { part1() }
    measure { part2() }
}
