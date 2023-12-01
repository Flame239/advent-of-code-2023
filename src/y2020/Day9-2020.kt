package y2020

import CountingSet
import measure
import readInput
import unorderedPairs

fun main() {
    val nums by lazy {
        readInput("2020-9").map { it.toLong() }
    }

    fun part1(): Long {
        val preamble = nums.take(25)
        val sums = CountingSet(preamble.unorderedPairs().groupingBy { it.first + it.second }.eachCount())

        for (n in 25..<nums.size) {
            val next = nums[n]
            if (!sums.contains(next)) return next

            // remove old sums
            val toRemove = nums[n - 25]
            (n - 24..<n).map { i -> toRemove + nums[i] }.forEach { sumToRemove -> sums.dec(sumToRemove) }

            // add new pairs
            (n - 24..<n).map { i -> next + nums[i] }.forEach { sumToAdd -> sums.inc(sumToAdd) }
        }

        return (-1).toLong()
    }

    fun part2(): Long {
        val targetSum = part1()
        val n = nums.size

        var curSum = nums[0] + nums[1]
        var right = 1
        for (left in 0..<n - 1) {
            // ensure that window has at least 2 numbers
            if (right == left) {
                right++
                curSum += nums[right]
            }

            while (curSum < targetSum && right < n - 1) {
                right++
                curSum += nums[right]
            }
            if (curSum == targetSum) {
                val range = nums.subList(left, right + 1)
                return range.min() + range.max()
            }
            if (curSum < targetSum) return -1

            curSum -= nums[left]
        }
        return -1
    }

    measure { part1() }
    measure { part2() }
}
