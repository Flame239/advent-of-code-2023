import kotlin.math.min

fun main() {
    val map: List<List<Int>> = readInput("Day17").map { line -> line.map { it.digitToInt() } }
    val n = map.size
    val m = map[0].size

    data class V(val i: Int, val j: Int, val dir: Dir)

    class ShortestPathFinder(val minSteps: Int, val maxSteps: Int, initMin: Int) {
        val cache = HashMap<V, Int>()
        var min = initMin

        fun shortestPath(): Int {
            findPath(0, 0, DIR_RIGHT, 0)
            findPath(0, 0, DIR_DOWN, 0)
            return min - map[0][0]
        }

        fun findPath(i: Int, j: Int, dir: Dir, cost: Int) {
            val newCost = cost + map[i][j]
            if (i == n - 1 && j == m - 1) {
                min = min(min, newCost)
                return
            }
            if (newCost > min) return
            if (cache.getOrDefault(V(i, j, dir), Int.MAX_VALUE) <= newCost) return
            cache[V(i, j, dir)] = newCost

            listOf(Dir(dir.stepJ, dir.stepI), Dir(-dir.stepJ, -dir.stepI)).forEach { d ->
                for (step in minSteps..maxSteps) {
                    val curI = i + step * d.stepI
                    val curJ = j + step * d.stepJ
                    if (curI in map.indices && curJ in map[0].indices) {
                        findPath(curI, curJ, d, newCost + (1..<step).sumOf { s -> map[i + s * d.stepI][j + s * d.stepJ] })
                    }
                }
            }
        }
    }

    fun part1(): Int = ShortestPathFinder(1, 3, (1 until n).sumOf { map[it - 1][it] + map[it][it] }).shortestPath()
    fun part2(): Int = ShortestPathFinder(4, 10, Integer.MAX_VALUE).shortestPath()

    measure { part1() }
    measure { part2() }
}
