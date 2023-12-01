package y2020

import measure
import mult
import readInput
import splitBy

fun main() {
    val adapters by lazy {
        readInput("2020-10-example").map { it.toInt() }.sorted()
    }

    fun part1(): Int {
        val diffs = adapters.sorted().windowed(2).map { (i, j) -> j - i }
        return (diffs.count { it == 1 } + diffs[0]) * (diffs.count { it == 3 } + 1)
    }

    // brute-force O(2^n * n)
    fun max2SeqZeroes(n: Int): Int {
        var badCount = 0
        for (i in 0..<(1 shl n)) {
            for (shift in 0..(n - 3)) {
                if ((i and (1 shl shift) == 0) && (i and (1 shl shift + 1) == 0) && (i and (1 shl shift + 2) == 0)) {
                    badCount++
                    break
                }
            }
        }
        return (1 shl n) - badCount
    }

    // https://www.youtube.com/watch?v=37Nk0JMaVSc
    // we need to count binary vectors of size n (where 0 means no adapter and 1 - there is one)
    // where distance between '1's is at most 2 '0' (otherwise we break the rule of <=3 jolts lower)
    // O(n)
    fun max2SeqZeroesRecurrent(n: Int): List<Int> {
        val arr = IntArray(n + 1)
        arr[1] = 0
        arr[2] = 0
        arr[3] = 1
        for (i in 4..n) {
            arr[i] = (1 shl (i - 3)) + arr[i - 1] + arr[i - 2] + arr[i - 3]
        }
        return arr.mapIndexed { index, i -> (1 shl index) - i }
    }

//    max2SeqZeroesRecurrent(20).drop(1).withIndex().all { (index, value) -> max2SeqZeroes(index + 1) == value }.println()

    fun max2SeqZeroesDp(n: Int): IntArray {
        val arr = IntArray(n + 1)
        arr[0] = 1
        arr[1] = 2
        arr[2] = 4
        for (i in 3..n) {
            //   (..n-1..)0               - includes endings (110, 100, 000, 010)
            //  (..n-2..)01               -                  (101, 001)
            // (..n-3..)011               -                  (001)
            //                            -                  total: all 7 possible endings
            // NB: we can't count ex. (..n-3..)101 as (..n-3..) could end with '11', but we already count them in (..n-2..)01
            arr[i] = arr[i - 1] + arr[i - 2] + arr[i - 3]
        }
        return arr
    }

    println(max2SeqZeroesRecurrent(10))
    println(max2SeqZeroesDp(10).contentToString())

    fun part2(): Long {
        val max2SeqZeroesCount = max2SeqZeroesRecurrent(10)
        val diffs = (listOf(0) + adapters).sorted().windowed(2).map { (i, j) -> j - i }
        return diffs.splitBy { it == 3 }
            .map { it.size - 1 } // because adapters used when diff=3 couldn't be removed
            .map { max2SeqZeroesCount[it].toLong() }
            .mult()
    }

    fun part2dp(): Long {
        // dp[i] = how many ways to connect adapters with joltage i
        // dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3]
        val dp = HashMap<Int, Long>()
        dp[0] = 1

        adapters.sorted().forEach { a ->
            dp[a] = dp.getOrDefault(a - 1, 0) + dp.getOrDefault(a - 2, 0) + dp.getOrDefault(a - 3, 0)
        }

        return dp[adapters.last()]!!
    }

    measure { part1() }
    measure { part2() }
    measure { part2dp() }
}
