fun main() {
    val map by lazy {
        readInput("Day11").map { it.toCharArray() }
    }
    val n = map.size
    val m = map[0].size

    fun getGalaxies(shift: Long): List<C> {
        var iShift = 0L
        val galaxiesByJ = hashMapOf<Int, ArrayList<C>>()
        for (j in 0..<m) galaxiesByJ[j] = ArrayList()

        for (i in 0..<n) {
            var empty = true
            for (j in 0..<m) {
                if (map[i][j] == '#') {
                    empty = false
                    galaxiesByJ[j]!!.add(C(i + iShift, j.toLong()))
                }
            }
            if (empty) iShift += shift
        }

        val galaxies = mutableListOf<C>()
        var jShift = 0L
        for (j in 0..<m) {
            galaxiesByJ[j]!!.forEach { c -> galaxies.add(C(c.i, c.j + jShift)) }
            var empty = (0..<n).all { map[it][j] == '.' }
            if (empty) jShift += shift
        }

        return galaxies
    }

    fun part1(): Long {
        return getGalaxies(1).unorderedPairs().sumOf { (g1, g2) -> g1.manhattan(g2) }
    }

    fun part2(): Long {
        return getGalaxies(999999).unorderedPairs().sumOf { (g1, g2) -> g1.manhattan(g2) }
    }

    measure { part1() }
    measure { part2() }
}
