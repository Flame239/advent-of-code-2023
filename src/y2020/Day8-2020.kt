package y2020

import measure
import readInput

fun main() {
    data class Op(val op: String, val arg: Int)

    val cmd by lazy {
        readInput("2020-8").map { Op(it.substring(0, 3), it.substring(4).toInt()) }
    }

    fun part1(): Int {
        var acc = 0
        var i = 0
        val visited = BooleanArray(cmd.size)
        while (!visited[i]) {
            visited[i] = true
            val (op, arg) = cmd[i]
            if (op == "acc") {
                acc += arg
                i++
            } else if (op == "jmp") {
                i += arg
            } else {
                i++
            }
        }
        return acc
    }

    fun part2(): Int {
        val reachableFrom = Array<ArrayList<Int>>(cmd.size + 1) { _ -> ArrayList() }
        cmd.forEachIndexed { index, op ->
            if (op.op == "acc" || op.op == "nop") {
                reachableFrom[index + 1].add(index)
            } else {
                reachableFrom[index + op.arg].add(index)
            }
        }

        var cur = cmd.size

        while (reachableFrom[cur].isNotEmpty()) {
            println("--")
            if (reachableFrom[cur].size > 1) println(cur.toString() + ": " + reachableFrom[cur])
            cur = reachableFrom[cur][0]
        }
        println(cur)

//        reachableFrom.forEachIndexed { index, from ->
//            if (from.isEmpty()) {
//                println(index)
//            }
//        }


        return cmd.size
    }


    measure { part1() }
    measure { part2() }
}
