fun main() {
    val patterns by lazy {
        readInput("Day13").splitBy { it.isEmpty() }
    }

//    fun findHorizontalReflection(pattern: List<String>): Int {
//        for (i in 0..<pattern.size - 1) {
//            if (pattern[i] == pattern[i + 1]) {
//
//            }
//        }
//    }
//
//    fun findVerticalReflection(pattern: List<String>): Int {
//
//    }


    fun part1(): Int {
        return 0
    }

    fun part2(): Int {
        return patterns.size
    }

    measure { part1() }
    measure { part2() }
}
