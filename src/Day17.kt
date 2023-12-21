import kotlin.math.min

fun main() {
    val map: List<List<Int>> = readInput("Day17").map { line -> line.map { it.digitToInt() } }
    val n = map.size
    val m = map[0].size

    data class V(val i: Int, val j: Int, val dir: Dir, val cons: Int)

    val cache = HashMap<V, Int>()

    // ballpark
    var min = (1 until n).sumOf { map[it - 1][it] + map[it][it] }
    fun findPath(i: Int, j: Int, dir: Dir, cost: Int, cons: Int) {
//        println("$i $j")
        if (i !in map.indices || j !in map[0].indices) return

        val newCost = cost + map[i][j]
        if (i == n - 1 && j == m - 1) {
//            println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$")
            min = min(min, newCost)
        }
        if (newCost > min) return
        //
//        if (cache.containsKey(V(i, j, dir, cons)) && cache[V(i, j, dir, cons)]!! > newCost) println("Found better path $i $j $dir $cons  ${cache[V(i, j, dir, cons)]!!} -> $newCost  $min")
        //
        if (cache.getOrDefault(V(i, j, dir, cons), Int.MAX_VALUE) <= newCost) return
        cache[V(i, j, dir, cons)] = newCost

        if (cons < 3) {
            findPath(i + dir.stepI, j + dir.stepJ, dir, newCost, cons + 1)
        }

        val dir1 = Dir(dir.stepJ, dir.stepI)
        findPath(i + dir1.stepI, j + dir1.stepJ, dir1, newCost, 1)

        val dir2 = Dir(-dir.stepJ, -dir.stepI)
        findPath(i + dir2.stepI, j + dir2.stepJ, dir2, newCost, 1)
    }

    fun part1(): Int {
        findPath(0, 1, DIR_RIGHT, 0, 1)
//        findPath(1, 0, DIR_DOWN, 0, 1)
        return min
    }

    fun part2(): Int {
        return map.size
    }

    measure { part1() }
    measure { part2() }
}
