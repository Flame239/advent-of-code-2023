import kotlin.math.min

fun main() {
    val patterns by lazy {
        readInput("Day13").splitBy { it.isEmpty() }
    }

    fun rowEq(p: List<String>, i1: Int, i2: Int): Boolean = p[i1] == p[i2]
    fun colEq(p: List<String>, j1: Int, j2: Int): Boolean = p.indices.all { i -> p[i][j1] == p[i][j2] }

    fun findHorizontalReflection(p: List<String>): Int {
        val n = p.size
        outer@ for (i in 0..<n - 1) {
            val width = min(i + 1, n - (i + 1))
            for (w in 0 until width) {
                if (!rowEq(p, i - w, i + 1 + w)) continue@outer
            }
            return i + 1
        }
        return 0
    }

    fun findVerticalReflection(p: List<String>): Int {
        val m = p[0].length
        outer@ for (j in 0..<m - 1) {
            val width = min(j + 1, m - (j + 1))
            for (w in 0 until width) {
                if (!colEq(p, j - w, j + 1 + w)) continue@outer
            }
            return j + 1
        }
        return 0
    }

    fun colDiff(p: List<String>, j1: Int, j2: Int): Int = p.indices.count { i -> p[i][j1] != p[i][j2] }
    fun rowDiff(p: List<String>, i1: Int, i2: Int): Int = p[0].indices.count { j -> p[i1][j] != p[i2][j] }

    fun findVerticalReflectionWithSmudge(p: List<String>): Int {
        val m = p[0].length
        outer@ for (j in 0..<m - 1) {
            var diff = 0
            val width = min(j + 1, m - (j + 1))
            for (w in 0 until width) {
                diff += colDiff(p, j - w, j + 1 + w)
                if (diff > 1) continue@outer
            }
            if (diff == 1) return j + 1
        }
        return 0
    }


    fun findHorizontalReflectionWithSmudge(p: List<String>): Int {
        val n = p.size
        outer@ for (i in 0..<n - 1) {
            var diff = 0
            val width = min(i + 1, n - (i + 1))
            for (w in 0 until width) {
                diff += rowDiff(p, i - w, i + 1 + w)
                if (diff > 1) continue@outer
            }
            if (diff == 1) return i + 1
        }
        return 0
    }

    fun part1(): Int {
        return patterns.sumOf { p -> 100 * findHorizontalReflection(p) + findVerticalReflection(p) }
    }

    fun part2(): Int {
        return patterns.sumOf { p -> 100 * findHorizontalReflectionWithSmudge(p) + findVerticalReflectionWithSmudge(p) }
    }

    measure { part1() }
    measure { part2() }
}
