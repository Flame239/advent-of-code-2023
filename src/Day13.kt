import kotlin.math.min

fun main() {
    val patterns by lazy {
        readInput("Day13").splitBy { it.isEmpty() }
    }

    fun findHorizontalReflection(p: List<String>): Int {
        outer@ for (i in 0..<p.size - 1) {
            if (p[i] == p[i + 1]) {
                val size = min(i + 1, p.size - (i + 1)) - 1
                for (step in 1..size) {
                    if (p[i - step] != p[i + 1 + step]) continue@outer
                }
                return i + 1
            }
        }
        return 0
    }

    fun colEq(p: List<String>, j1: Int, j2: Int): Boolean = p.indices.all { i -> p[i][j1] == p[i][j2] }

    fun findVerticalReflection(p: List<String>): Int {
        val m = p[0].length
        outer@ for (j in 0..<m - 1) {
            val size = min(j + 1, m - (j + 1))
            for (step in 0 until size) {
                if (!colEq(p, j - step, j + 1 + step)) continue@outer
            }
            return j + 1
        }
        return 0
    }

    fun part1(): Int {
        return patterns.sumOf { p -> 100 * findHorizontalReflection(p) + findVerticalReflection(p) }
    }

    fun part2(): Int {
        return patterns.size
    }

    measure { part1() }
    measure { part2() }
}
